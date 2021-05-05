package com.lenovo.feizai.controller;

import com.lenovo.feizai.entity.BaseModel;
import com.lenovo.feizai.entity.Location;
import com.lenovo.feizai.service.LocationServiceDao;
import com.lenovo.feizai.service.MerchantStateServiceDao;
import com.lenovo.feizai.util.GsonUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/16 0016 上午 10:35:39
 * @annotation
 */
@Controller
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    LocationServiceDao locationServiceDao;

    @ResponseBody
    @RequestMapping(value = "/selectparking", method = RequestMethod.POST)
    public String selectParking(@RequestBody Location location) {
        List<Location> parkings = locationServiceDao.selectParking(location);
        BaseModel<Location> model = new BaseModel<>();
        if (parkings.size()>0) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(parkings);
            return GsonUtil.GsonString(model);
        }else {
            model.setCode(201);
            model.setMessage("查询失败");
            return GsonUtil.GsonString(model);
        }
    }

    @ResponseBody
    @RequestMapping("/selectParkingByName")
    public String selectParkingByName(@Param("merchant") String merchant){
        List<Location> locations = locationServiceDao.selectParkingByName(merchant);
        BaseModel<Location> model = new BaseModel<>();
        if (locations.size()>0){
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(locations);
        }else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

}
