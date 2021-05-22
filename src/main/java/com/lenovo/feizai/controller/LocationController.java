package com.lenovo.feizai.controller;

import com.lenovo.feizai.entity.*;
import com.lenovo.feizai.service.*;
import com.lenovo.feizai.util.GsonUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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

    @Autowired
    ParkingInfoServiceDao parkingInfoServiceDao;

    @Autowired
    ParkingNumberServerDao parkingNumberServerDao;

    @Autowired
    RatesServiceDao ratesServiceDao;

    @Autowired
    MerchantStateServiceDao merchantStateServiceDao;

    @ResponseBody
    @RequestMapping(value = "/selectparking", method = RequestMethod.POST)
    public String selectParking(@RequestBody Location location) {
        List<Location> parkings = locationServiceDao.selectParking(location);
        List<MerchantProperty> properties = new ArrayList<>();
        for (Location parking : parkings) {
            MerchantProperty property = new MerchantProperty();
            ParkingInfo parkinginfo = parkingInfoServiceDao.selectMerchantByMerchantName(parking.getMerchantname());
            ParkingNumber parkingNumber = parkingNumberServerDao.selectNumberByMerchantnumber(parking.getMerchantname());
            Rates rates = ratesServiceDao.findRatesByMerchant(parking.getMerchantname());
            MerchantState merchantState = merchantStateServiceDao.findMerchantState(parking.getMerchantname());
            property.setLocation(parking);
            property.setParkingInfo(parkinginfo);
            property.setParkingNumber(parkingNumber);
            property.setRates(rates);
            property.setMerchantState(merchantState);
            properties.add(property);
        }
        BaseModel<MerchantProperty> model = new BaseModel<>();
        if (parkings.size() > 0) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(properties);
            return GsonUtil.GsonString(model);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
            return GsonUtil.GsonString(model);
        }
    }

    @ResponseBody
    @RequestMapping("/selectParkingByName")
    public String selectParkingByName(@Param("merchant") String merchant) {
        Location location = locationServiceDao.selectParkingByName(merchant);
        BaseModel<Location> model = new BaseModel<>();
        if (location != null) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setData(location);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

}
