package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.ParkingSpaceDao;
import com.lenovo.feizai.entity.MerchantChange;
import com.lenovo.feizai.entity.ParkingSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/18 0018 上午 10:56:55
 * @annotation
 */
@Service
public class ParkingSpaceService implements ParkingSpaceServiceDao {

    @Autowired
    ParkingSpaceDao dao;

    @Override
    public int addSpace(ParkingSpace parkingSpace) {
        return dao.addSpace(parkingSpace);
    }

    @Override
    public List<ParkingSpace> searchSpace(String merchantname) {
        return dao.searchSpace(merchantname);
    }

    @Override
    public List<ParkingSpace> searchSpaceByNameAndSerialnumber(String merchantname, String serialnumber) {
        return dao.searchSpaceByNameAndSerialnumber(merchantname, serialnumber);
    }

    @Override
    public int updateParkingSpace(ParkingSpace parkingSpace) {
        return dao.updateParkingSpace(parkingSpace);
    }

    @Override
    public int updateParkingSpaceByNameAndSerialnumber(String merchant, String serialnumber, String state) {
        return dao.updateParkingSpaceByNameAndSerialnumber(merchant, serialnumber, state);
    }

    @Override
    public int updateByMerchantChange(MerchantChange merchantChange) {
        return dao.updateByMerchantChange(merchantChange);
    }

    @Override
    public int deleteSpaceByMerchantName(String oldname) {
        return dao.deleteSpaceByMerchantName(oldname);
    }
}
