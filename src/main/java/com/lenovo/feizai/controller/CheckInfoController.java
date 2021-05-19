package com.lenovo.feizai.controller;

import com.lenovo.feizai.entity.*;
import com.lenovo.feizai.myjob.LeaveJob;
import com.lenovo.feizai.myjob.QuartzManager;
import com.lenovo.feizai.service.*;
import com.lenovo.feizai.util.GsonUtil;
import com.lenovo.feizai.util.TimeUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author feizai
 * @date 2021/4/17 0017 下午 3:33:58
 * @annotation
 */
@Controller
@RequestMapping("/api/check")
public class CheckInfoController {
    @Autowired
    CheckInfoServiceDao checkInfoServiceDao;

    @Autowired
    ParkingNumberServerDao parkingNumberServerDao;

    @Autowired
    ParkingSpaceServiceDao parkingSpaceServiceDao;

    @Autowired
    SubscribeServiceDao subscribeServiceDao;

    @Autowired
    RatesServiceDao ratesServiceDao;

    @Autowired
    PayServiceDao payServiceDao;
    
    @Autowired
    QuartzManager myQuartzManager;

    @ResponseBody
    @RequestMapping("/addCheckInfoByQRCode")//扫码入库,
    public String addCheckInfoByQRCode(@Param("orderNumber") String orderNumber) {
        BaseModel model = new BaseModel();
        try {
            Order order = subscribeServiceDao.findOrderByNumber(orderNumber);
            switch (order.getState()) {
                case "进行中":
                    CheckInfo isParking = checkInfoServiceDao.selectUsingSpace(order.getMerchantName(),order.getCarLicense());
                    if (isParking == null) {
                        CheckInfo info = new CheckInfo();
                        info.setMerchant(order.getMerchantName());
                        info.setIntime(TimeUtil.getNowTimestamp());
                        info.setCarlicense(order.getCarLicense());
                        info.setSerialnumber(order.getSpace());
                        info.setState("未缴费");
                        ParkingNumber number = new ParkingNumber();
                        ParkingSpace space = new ParkingSpace();

                        space.setMerchantname(order.getMerchantName());
                        space.setParkingstate("已使用");
                        space.setSerialnumber(order.getSpace());
                        space.setRemark(order.getCarLicense());

                        number.setMerchantname(order.getMerchantName());
                        number.setSubscribenumber(-1);
                        number.setUsednumber(1);
                        number.setAllnumber(0);
                        number.setUnusednumber(0);

                        int result = checkInfoServiceDao.addCheckInfo(info);
                        int result1 = parkingNumberServerDao.updateNumber(number);
                        int result2 = parkingSpaceServiceDao.updateParkingSpace(space);
                        int result3 = subscribeServiceDao.updateOrderState("已完成", order.getOrderNumber());
                        if (result > 0 && result1 > 0 && result2 > 0 && result3 > 0) {
                            model.setCode(200);
                            model.setMessage("入库成功");
                            myQuartzManager.removeJob(order.getOrderNumber(), "Subscribe", order.getOrderNumber(), "Subscribe");
                        } else {
                            model.setCode(201);
                            model.setMessage("入库失败");
                        }
                    } else {
                        model.setCode(203);
                        model.setMessage("该车辆已在停车场中");
                    }
                    break;
                case "已超时":
                    model.setCode(204);
                    model.setMessage("订单已超时");
                    break;
                case "已取消":
                    model.setCode(205);
                    model.setMessage("订单已取消");
                    break;
                case "已完成":
                    model.setCode(206);
                    model.setMessage("订单已完成");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.setCode(202);
            model.setMessage("数据解析错误");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/addCheckInfo")//输车牌入库
    public String addCheckInfo(@RequestBody CheckInfo checkInfo) {
        CheckInfo isParking = checkInfoServiceDao.selectUsingSpace(checkInfo.getMerchant(), checkInfo.getCarlicense());
        BaseModel model = new BaseModel();
        if (isParking == null) {
            ParkingNumber number = new ParkingNumber();
            ParkingSpace space = new ParkingSpace();

            space.setMerchantname(checkInfo.getMerchant());
            space.setParkingstate("已使用");
            space.setSerialnumber(checkInfo.getSerialnumber());
            space.setRemark(checkInfo.getCarlicense());

            number.setMerchantname(checkInfo.getMerchant());
            number.setSubscribenumber(0);
            number.setUsednumber(1);
            number.setAllnumber(0);
            number.setUnusednumber(0);

            checkInfo.setState("未缴费");

            int result = checkInfoServiceDao.addCheckInfo(checkInfo);
            int result1 = parkingNumberServerDao.updateNumber(number);
            int result2 = parkingSpaceServiceDao.updateParkingSpace(space);
            if (result > 0 && result1 > 0 && result2 > 0) {
                model.setCode(200);
                model.setMessage("入库成功");
            } else {
                model.setCode(201);
                model.setMessage("入库失败");
            }
        } else {
            model.setCode(202);
            model.setMessage("该车辆已在停车场中");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/selectCheckInfoByCar")//出口检测车辆属于那种离场
    public String selectCheckInfoByCar(@Param("merchant") String merchant, @Param("car") String car) {
        CheckInfo info = checkInfoServiceDao.selectCheckInfoByCarAndMerchant(merchant, car);
        BaseModel<CheckInfo> model = new BaseModel<>();
        if (info != null) {
            if (info.getState().equals("已缴费") ) {//已预先缴费的
                //取消定时任务
                myQuartzManager.removeJob(info.getOrdernumber(),"Leave",info.getOrdernumber(),"Leave");
                //变更状态值，checkinfo，parknumber，parkspace
                info.setState("已出场");

                ParkingNumber number = new ParkingNumber();
                number.setMerchantname(info.getMerchant());
                number.setAllnumber(0);
                number.setUsednumber(-1);
                number.setSubscribenumber(0);
                number.setUnusednumber(0);

                ParkingSpace space = new ParkingSpace();
                space.setMerchantname(info.getMerchant());
                space.setSerialnumber(info.getSerialnumber());
                space.setParkingstate("未使用");
                space.setRemark(null);

                parkingSpaceServiceDao.updateParkingSpace(space);
                parkingNumberServerDao.updateNumber(number);
                int result = checkInfoServiceDao.goout(info);
                if (result > 0) {
                    model.setCode(200);
                    model.setMessage("已缴费");
                    model.setData(info);
                }

            } else {//未预先缴费的，返回信息给APP或已超时出场的也是在这里找到
                info.setOuttime(TimeUtil.getNowTimestamp());
                Rates rates = ratesServiceDao.findRatesByMerchant(merchant);
                float price = TimeUtil.getPrice(info.getIntime(), info.getOuttime(), rates);
                info.setPrice(price);
                model.setCode(200);
                model.setMessage("未缴费");
                model.setData(info);
            }
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/isPay")//检测是否支付
    public String isPay(@Param("merchant") String merchant, @Param("car") String car) {
        CheckInfo pay = checkInfoServiceDao.isPay(merchant, car);
        BaseModel model = new BaseModel();
        if (pay != null) {
            model.setCode(201);
            model.setMessage("用户未支付成功");
        } else {
            model.setCode(200);
            model.setMessage("用户支付成功");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updatePayByMoney")//现金支付
    public String pay(@RequestBody CheckInfo checkInfo) {
        checkInfo.setState("已出场");
        System.out.println(checkInfo.toString());

        ParkingNumber number = new ParkingNumber();
        number.setMerchantname(checkInfo.getMerchant());
        number.setAllnumber(0);
        number.setUsednumber(-1);
        number.setSubscribenumber(0);
        number.setUnusednumber(0);

        ParkingSpace space = new ParkingSpace();
        space.setMerchantname(checkInfo.getMerchant());
        space.setSerialnumber(checkInfo.getSerialnumber());
        space.setParkingstate("未使用");
        space.setRemark(null);

        parkingSpaceServiceDao.updateParkingSpace(space);
        parkingNumberServerDao.updateNumber(number);
        int result = checkInfoServiceDao.updatePayByMoney(checkInfo);

        BaseModel model = new BaseModel();
        if (result > 0) {
            model.setCode(200);
            model.setMessage("出库成功");
        } else {
            model.setCode(201);
            model.setMessage("出库失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateByNowCode")//出口扫码支付
    public String updateByNowCode(@Param("orderjson") String orderjson, @Param("checkinfojson") String checkinfojson) {
        BaseModel model = new BaseModel();
        try {
            Order order = GsonUtil.GsonToBean(orderjson, Order.class);
            CheckInfo checkInfo = GsonUtil.GsonToBean(checkinfojson, CheckInfo.class);

            ParkingNumber number = new ParkingNumber();
            number.setMerchantname(checkInfo.getMerchant());
            number.setAllnumber(0);
            number.setUsednumber(-1);
            number.setSubscribenumber(0);
            number.setUnusednumber(0);

            ParkingSpace space = new ParkingSpace();
            space.setMerchantname(checkInfo.getMerchant());
            space.setSerialnumber(checkInfo.getSerialnumber());
            space.setParkingstate("未使用");
            space.setRemark(null);

            parkingSpaceServiceDao.updateParkingSpace(space);
            parkingNumberServerDao.updateNumber(number);
            payServiceDao.addPayOrder(order);

            int result = checkInfoServiceDao.updatePayByMoney(checkInfo);

            if (result > 0) {
                model.setCode(200);
                model.setMessage("出库成功");
            } else {
                model.setCode(201);
                model.setMessage("出库失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.setCode(201);
            model.setMessage("出库失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/clearing")//提前扫码计算费用
    public String clearing(@Param("merchant") String merchant, @Param("car") String car) {
        CheckInfo checkInfo = checkInfoServiceDao.clearing(merchant, car);
        BaseModel<CheckInfo> model = new BaseModel<>();
        if (checkInfo != null) {
            checkInfo.setOuttime(TimeUtil.getNowTimestamp());
            Rates rates = ratesServiceDao.findRatesByMerchant(merchant);
            float price = TimeUtil.getPrice(checkInfo.getIntime(), checkInfo.getOuttime(), rates);
            checkInfo.setPrice(price);
            model.setCode(200);
            model.setMessage("查询成功");
            model.setData(checkInfo);
        } else {
            model.setCode(201);
            model.setMessage("未查询到该车辆");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updataByScanQRCode")//提前扫码支付更新订单
    public String updataByScanQRCode(@Param("orderjson") String orderjson, @Param("checkinfojson") String checkinfojson) {
        BaseModel model = new BaseModel();
        try {
            Order order = GsonUtil.GsonToBean(orderjson, Order.class);
            CheckInfo checkInfo = GsonUtil.GsonToBean(checkinfojson, CheckInfo.class);

            int result = payServiceDao.addPayOrder(order);
            int result1 = checkInfoServiceDao.updatePayByMoney(checkInfo);

            if (result > 0 && result1 > 0) {
                model.setCode(200);
                model.setMessage("更新成功");
                //开启定时任务,倒计时5分钟，超过5分钟重新算钱，原订单置为已超时，同时新建订单
                myQuartzManager.addLeaveJob(checkInfo.getOrdernumber(),"Leave",checkInfo.getOrdernumber(),"Leave", LeaveJob.class,checkInfo);
            } else {
                model.setCode(201);
                model.setMessage("更新失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.setCode(201);
            model.setMessage("更新失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/selectUsingSpace")
    public String selectUsingSpace(@RequestBody ParkingSpace space) {
        CheckInfo checkInfo = checkInfoServiceDao.selectUsingSpace(space.getMerchantname(), space.getRemark());
        BaseModel<CheckInfo> model = new BaseModel<>();
        if (checkInfo != null) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setData(checkInfo);
        }else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }
}
