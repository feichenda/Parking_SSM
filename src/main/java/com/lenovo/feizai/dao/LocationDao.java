package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.Location;
import com.lenovo.feizai.entity.MerchantChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author feizai
 * @date 02/22/2021 022 9:18:41 PM
 * @annotation
 */
public interface LocationDao {
    public int addLocation(Location location);

    public List<Location> selectParking(Location location);

    public List<Location> selectLocation(String keyword);

    public Location selectParkingByName(String name);

    public int updateByMerchantChange(MerchantChange merchantChange);

    public int readdLocation(@Param("location") Location location, @Param("oldname") String oldname);
}
