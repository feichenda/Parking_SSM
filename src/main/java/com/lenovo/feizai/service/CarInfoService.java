package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.CarInfoDao;
import com.lenovo.feizai.entity.CarInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/23 0023 下午 5:24:09
 * @annotation
 */
@Service
public class CarInfoService implements CarInfoServiceDao {

    @Autowired
    CarInfoDao dao;

    @Override
    public int addCarInfo(CarInfo carInfo) {
        return dao.addCarInfo(carInfo);
    }

    @Override
    public List<CarInfo> selectCarByUsernam(String name) {
        return dao.selectCarByUsernam(name);
    }

    @Override
    public List<String> selectCarLicenseByUsernam(String username) {
        return dao.selectCarLicenseByUsernam(username);
    }

    @Override
    public List<String> selectNoFreeCarByUsername(String username) {
        return dao.selectNoFreeCarByUsername(username);
    }

    @Override
    public int updateCarInfo(CarInfo carInfo) {
        return dao.updateCarInfo(carInfo);
    }

    @Override
    public int deleteCar(CarInfo carInfo) {
        return dao.deleteCar(carInfo);
    }

    @Override
    public int updateUserName(String oldname, String newname) {
        return dao.updateUserName(oldname, newname);
    }
}
