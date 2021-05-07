package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.ParkingNumberDao;
import com.lenovo.feizai.entity.MerchantChange;
import com.lenovo.feizai.entity.ParkingNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/17 0017 下午 2:42:56
 * @annotation
 */
@Service
public class ParkingNumberServer implements ParkingNumberServerDao {

    @Autowired
    ParkingNumberDao dao;

    @Override
    public int addParkingNumber(ParkingNumber parkingNumber) {
        return dao.addParkingNumber(parkingNumber);
    }

    @Override
    public List<ParkingNumber> selectNumber(String keyword) {
        return dao.selectNumber(keyword);
    }

    @Override
    public List<ParkingNumber> selectNumberByname(String name) {
        return dao.selectNumberByname(name);
    }

    @Override
    public int updateNumber(ParkingNumber parkingNumber) {
        return dao.updateNumber(parkingNumber);
    }

    @Override
    public int updateByMerchantChange(MerchantChange merchantChange) {
        return dao.updateByMerchantChange(merchantChange);
    }

    @Override
    public ParkingNumber selectNumberByMerchantnumber(String merchant) {
        return dao.selectNumberByMerchantnumber(merchant);
    }
}
