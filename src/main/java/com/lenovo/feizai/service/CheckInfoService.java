package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.CheckInfoDao;
import com.lenovo.feizai.entity.CheckInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author feizai
 * @date 2021/4/17 0017 下午 3:37:37
 * @annotation
 */
@Service
public class CheckInfoService implements CheckInfoServiceDao {
    @Autowired
    CheckInfoDao dao;

    @Override
    public int addCheckInfo(CheckInfo info) {
        return dao.addCheckInfo(info);
    }

    @Override
    public CheckInfo selectCheckInfoByCarAndMerchant(String merchant, String car) {
        return dao.selectCheckInfoByCarAndMerchant(merchant, car);
    }

    @Override
    public CheckInfo isPay(String merchant, String car) {
        return dao.isPay(merchant, car);
    }

    @Override
    public CheckInfo clearing(String merchant, String car) {
        return dao.clearing(merchant, car);
    }

    @Override
    public int updatePayByMoney(CheckInfo checkInfo) {
        return dao.updatePayByMoney(checkInfo);
    }

    @Override
    public int goout(CheckInfo checkInfo) {
        return dao.goout(checkInfo);
    }

    @Override
    public CheckInfo selectUsingSpace(String merchant, String car) {
        return dao.selectUsingSpace(merchant, car);
    }

    @Override
    public CheckInfo isParking(String car) {
        return dao.isParking(car);
    }
}
