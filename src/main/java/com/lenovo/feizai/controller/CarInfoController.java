package com.lenovo.feizai.controller;

import com.lenovo.feizai.entity.BaseModel;
import com.lenovo.feizai.entity.CarInfo;
import com.lenovo.feizai.service.CarInfoServiceDao;
import com.lenovo.feizai.util.GsonUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author feizai
 * @date 2021/3/23 0023 下午 5:21:59
 * @annotation
 */
@Controller
@RequestMapping("/api/car")
public class CarInfoController {

    @Autowired
    CarInfoServiceDao carInfoServiceDao;

    @ResponseBody
    @RequestMapping("/addCarInfo")
    public String addCarInfo(@RequestBody CarInfo carInfo){
        CarInfo info = carInfoServiceDao.selectCarByLicense(carInfo);
        BaseModel model = new BaseModel();
        if (info != null) {
            model.setCode(202);
            model.setMessage("已存在该车辆");
            return GsonUtil.GsonString(model);
        }
        int result = carInfoServiceDao.addCarInfo(carInfo);
        if (result>0){
            model.setCode(200);
            model.setMessage("添加成功");
        }else {
            model.setCode(201);
            model.setMessage("添加失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateCarInfo")
    public String updateCarInfo(@RequestBody CarInfo carInfo) {
        int result = carInfoServiceDao.updateCarInfo(carInfo);
        BaseModel model = new BaseModel();
        if (result>0){
            model.setCode(200);
            model.setMessage("更新成功");
        }else {
            model.setCode(201);
            model.setMessage("更新失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/selectCarByUsername")
    public String selectCarByUsername(@Param("username") String username){
        List<CarInfo> carInfos = carInfoServiceDao.selectCarByUsernam(username);
        BaseModel<CarInfo> model = new BaseModel<>();
        if (carInfos.size()>0){
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(carInfos);
            return GsonUtil.GsonString(model);
        }else {
            model.setCode(201);
            model.setMessage("查询失败");
            return GsonUtil.GsonString(model);
        }
    }

    @ResponseBody
    @RequestMapping("/selectFreeCarByUsername")
    public String selectFreeCarByUsername(@Param("username") String username){
        List<String> carInfos = carInfoServiceDao.selectCarLicenseByUsernam(username);
        List<String> list = carInfoServiceDao.selectNoFreeCarByUsername(username);
        BaseModel<String> model = new BaseModel<>();
        for (String s : list) {
            carInfos.remove(s);
        }
        if (carInfos.size()>0){
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(carInfos);
        }else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/selectNoFreeCarByUsername")
    public String selectNoFreeCarByUsername(@Param("username") String username){
        List<String> list = carInfoServiceDao.selectNoFreeCarByUsername(username);
        BaseModel<String> model = new BaseModel<>();
        if (list.size()>0){
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(list);
        }else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/deleteCar")
    public String deleteCarByUsernameAndLicense(@RequestBody CarInfo carInfo) {
        int result = carInfoServiceDao.deleteCar(carInfo);
        BaseModel model = new BaseModel();
        if (result>0){
            model.setCode(200);
            model.setMessage("删除成功");
        }else {
            model.setCode(201);
            model.setMessage("删除失败");
        }
        return GsonUtil.GsonString(model);
    }
}
