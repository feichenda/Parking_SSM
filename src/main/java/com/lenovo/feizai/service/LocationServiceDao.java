package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.Location;
import com.lenovo.feizai.entity.MerchantChange;

import java.util.List;

/**
 * @author feizai
 * @date 02/22/2021 022 9:18:41 PM
 * @annotation
 */
public interface LocationServiceDao {
    public int addLocation(Location location);

    public List<Location> selectParking(Location location);

    public List<Location> selectLocation(String keyword);

    public List<Location> selectParkingByName(String name);

    public int updateByMerchantChange(MerchantChange merchantChange);
}
