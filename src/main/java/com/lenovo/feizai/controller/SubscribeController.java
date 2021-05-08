package com.lenovo.feizai.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.feizai.entity.BaseModel;
import com.lenovo.feizai.entity.ParkingNumber;
import com.lenovo.feizai.entity.ParkingSpace;
import com.lenovo.feizai.entity.Order;
import com.lenovo.feizai.myjob.SubsrcibeJob;
import com.lenovo.feizai.service.ParkingNumberServerDao;
import com.lenovo.feizai.service.ParkingSpaceServiceDao;
import com.lenovo.feizai.service.SubscribeServiceDao;
import com.lenovo.feizai.util.FileUtil;
import com.lenovo.feizai.util.GsonUtil;
import com.lenovo.feizai.myjob.QuartzManager;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author feizai
 * @date 2021/3/26 0026 下午 6:51:18
 * @annotation
 */
@Controller
@RequestMapping("/api/subscribe")
public class SubscribeController {

    @Autowired
    SubscribeServiceDao subscribeServiceDao;

    @Autowired
    ParkingSpaceServiceDao parkingSpaceServiceDao;

    @Autowired
    ParkingNumberServerDao parkingNumberServerDao;

    @Autowired
    QuartzManager myQuartzManager;

    @ResponseBody
    @RequestMapping(value = "/addSubscribeOrder", method = RequestMethod.POST)//新增订单
    public String addSubscribeOrder(@Param("order") String order, @RequestParam("uploadFile") MultipartFile file) {
        BaseModel model = new BaseModel();
        ParkingSpace parkingSpace = new ParkingSpace();
        ParkingNumber parkingNumber = new ParkingNumber();
        Order subscribeOrder = GsonUtil.GsonToBean(order, Order.class);
        parkingSpace.setMerchantname(subscribeOrder.getMerchantName());
        parkingSpace.setSerialnumber(subscribeOrder.getSpace());
        parkingSpace.setRemark(subscribeOrder.getOrderNumber());
        parkingSpace.setParkingstate("已预约");
        parkingNumber.setMerchantname(subscribeOrder.getMerchantName());
        parkingNumber.setAllnumber(0);
        parkingNumber.setUsednumber(0);
        parkingNumber.setSubscribenumber(1);
        String filePath = null;
        try {
            filePath = FileUtil.getOrderFilePath(file, subscribeOrder.getOrderNumber());
            subscribeOrder.setQrCode(filePath);
            int subscribeResult = subscribeServiceDao.addSubscribeOrder(subscribeOrder);
            int spaceResult = parkingSpaceServiceDao.updateParkingSpace(parkingSpace);
            int numberResult = parkingNumberServerDao.updateNumber(parkingNumber);
            if (subscribeResult > 0 && spaceResult > 0 && numberResult > 0) {
                model.setCode(200);
                model.setMessage("更新订单成功");
                myQuartzManager.addSubsrcibeJob(subscribeOrder.getOrderNumber(), "Subscribe", subscribeOrder.getOrderNumber(), "Subscribe", SubsrcibeJob.class, subscribeOrder);
                subscribeServiceDao.updateOrderState("进行中", subscribeOrder.getOrderNumber());
            } else {
                model.setCode(201);
                model.setMessage("更新订单失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.setCode(201);
            model.setMessage("更新订单失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateSubsrcibeOrder")//更新订单
    public String updateSubsrcibeOrder(@Param("order") String order, @RequestParam("uploadFile") MultipartFile file) {
        BaseModel model = new BaseModel();
        ParkingSpace parkingSpace = new ParkingSpace();
        ParkingNumber parkingNumber = new ParkingNumber();
        Order subscribeOrder = GsonUtil.GsonToBean(order, Order.class);
        parkingSpace.setMerchantname(subscribeOrder.getMerchantName());
        parkingSpace.setSerialnumber(subscribeOrder.getSpace());
        parkingSpace.setRemark(subscribeOrder.getOrderNumber());
        parkingSpace.setParkingstate("已预约");
        parkingNumber.setMerchantname(subscribeOrder.getMerchantName());
        parkingNumber.setAllnumber(0);
        parkingNumber.setUsednumber(0);
        parkingNumber.setSubscribenumber(1);
        String filePath = null;
        try {
            filePath = FileUtil.getOrderFilePath(file, subscribeOrder.getOrderNumber());
            subscribeOrder.setQrCode(filePath);
            int subscribeResult = subscribeServiceDao.updateSubsrcibeOrder(subscribeOrder);
            int spaceResult = parkingSpaceServiceDao.updateParkingSpace(parkingSpace);
            if (subscribeResult > 0 && spaceResult > 0) {
                model.setCode(200);
                model.setMessage("插入订单成功");
                myQuartzManager.modifySubsrcibeJobTime(subscribeOrder.getOrderNumber(), "Subscribe", subscribeOrder.getOrderNumber(), "Subscribe", subscribeOrder);
            } else {
                model.setCode(201);
                model.setMessage("插入订单失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.setCode(201);
            model.setMessage("插入订单失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/customerFindOrder")//用户查询订单
    public String customerFindOrder(@Param("String customer") String customer, @Param("year") int year, @Param("month") int month, @Param("type") String type, @Param("index") int index) {
        PageHelper.startPage(index, 10);
        List<Order> orders = null;
        List<String> list = new ArrayList<>();
        BaseModel<String> model = new BaseModel<>();
        String statrDate = year + "-" + month + "-1";
        String endDate = year + "-" + (month + 1) + "-1";
        if (month == 12) {
            endDate = (year + 1) + "-" + 1 + "-1";
        }
        try {
            orders = subscribeServiceDao.customerFindOrder(customer, statrDate, endDate, type);
            for (Order order : orders) {
                String json = GsonUtil.GsonString(order);
                list.add(json);
            }
            PageInfo<Order> pageInfo = new PageInfo<>(orders);
//            if (pageInfo.getTotal() == 0) {
//                model.setCode(201);
//                model.setMessage("查询失败");
//                return GsonUtil.GsonString(model);
//            }
            if (index <= pageInfo.getNavigateLastPage() && pageInfo.getTotal() > 0) {
                model.setCode(200);
                model.setMessage("yes");
                model.setDatas(list);
            } else {
                model.setCode(200);
                model.setMessage("no");
            }
        } catch (Exception e) {
            e.printStackTrace();
            orders = null;
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/merchantFindOrder")//商家查询订单
    public String merchantFindOrder(@Param("String customer") String customer, @Param("year") int year, @Param("month") int month, @Param("type") String type, @Param("index") int index) {
        PageHelper.startPage(index, 10);
        List<Order> orders = null;
        List<String> list = new ArrayList<>();
        BaseModel<String> model = new BaseModel<>();
        String statrDate = year + "-" + month + "-1";
        String endDate = year + "-" + (month + 1) + "-1";
        if (month == 12) {
            endDate = (year + 1) + "-" + 1 + "-1";
        }
        try {
            orders = subscribeServiceDao.merchantFindOrder(customer, statrDate, endDate, type);
            for (Order order : orders) {
                String json = GsonUtil.GsonString(order);
                list.add(json);
            }
            PageInfo<Order> pageInfo = new PageInfo<>(orders);
            if (index <= pageInfo.getNavigateLastPage() && pageInfo.getTotal() > 0) {
                model.setCode(200);
                model.setMessage("yes");
                model.setDatas(list);
            } else {
                model.setCode(200);
                model.setMessage("no");
            }
        } catch (Exception e) {
            e.printStackTrace();
            orders = null;
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/selectIngOrderByUser")//查询用户正在进行中的订单
    public String selectIngOrderByUser(@Param("username") String username) {
        List<Order> orders = subscribeServiceDao.selectIngOrderByUser(username);
        BaseModel<String> model = new BaseModel<>();
        List<String> result = new ArrayList<>();
        for (Order order : orders) {
            result.add(GsonUtil.GsonString(order));
        }
        if (result.size() > 0) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(result);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/findSubscribeOrderByLicense")//通过车牌查询订单
    public String findSubscribeOrderByLicense(@Param("license") String license, @Param("merchant") String merchant) {
        List<Order> orders = subscribeServiceDao.findSubscribeOrderByLicense(license, merchant);
        BaseModel model = new BaseModel();
        if (orders.size() > 0) {
            model.setCode(200);
            model.setMessage("查询成功");
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/findOrderByNumber")//通过订单号查询订单
    public String findOrderByNumber(@Param("ordernumber") String ordernumber) {
        Order order = subscribeServiceDao.findOrderByNumber(ordernumber);
        BaseModel<String> model = new BaseModel<>();
        if (order != null) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setData(GsonUtil.GsonString(order));
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/cancelOrderByNumber")
    public String cancelOrderByNumber(@RequestParam("orderjson") String orderjson) {
        Order order = GsonUtil.GsonToBean(orderjson, Order.class);
        BaseModel model = new BaseModel();
        int result = subscribeServiceDao.cancelOrder(order);

        ParkingSpace space = new ParkingSpace();
        space.setMerchantname(order.getMerchantName());
        space.setSerialnumber(order.getSpace());
        space.setParkingstate("未使用");
        space.setRemark(null);
        parkingSpaceServiceDao.updateParkingSpace(space);

        ParkingNumber number = new ParkingNumber();
        number.setMerchantname(order.getMerchantName());
        number.setAllnumber(0);
        number.setSubscribenumber(-1);
        number.setUnusednumber(0);
        number.setUsednumber(0);
        parkingNumberServerDao.updateNumber(number);

        if (result > 0) {
            model.setCode(200);
            model.setMessage("取消成功");
            myQuartzManager.removeJob(order.getOrderNumber(), "Subscribe", order.getOrderNumber(), "Subscribe");
        } else {
            model.setCode(201);
            model.setMessage("取消失败");
        }
        return GsonUtil.GsonString(model);
    }

}
