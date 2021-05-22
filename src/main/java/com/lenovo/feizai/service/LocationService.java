package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.LocationDao;
import com.lenovo.feizai.entity.Location;
import com.lenovo.feizai.entity.MerchantChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 02/22/2021 022 9:18:41 PM
 * @annotation
 */
@Service
public class LocationService implements LocationServiceDao {

    @Autowired
    LocationDao dao;

    @Override
    public int addLocation(Location location) {
        return dao.addLocation(location);
    }

    @Override
    public List<Location> selectParking(Location location) {
        return dao.selectParking(location);
    }

    @Override
    public List<Location> selectLocation(String keyword) {
        return dao.selectLocation(keyword);
    }

    @Override
    public Location selectParkingByName(String name) {
        return dao.selectParkingByName(name);
    }

    @Override
    public int updateByMerchantChange(MerchantChange merchantChange) {
        return dao.updateByMerchantChange(merchantChange);
    }

    @Override
    public int readdLocation(Location location, String oldname) {
        return dao.readdLocation(location, oldname);
    }
}
