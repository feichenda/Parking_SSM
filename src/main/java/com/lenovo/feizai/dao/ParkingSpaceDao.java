package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.MerchantChange;
import com.lenovo.feizai.entity.ParkingSpace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/18 0018 上午 10:56:55
 * @annotation
 */
public interface ParkingSpaceDao {
    public int addSpace(ParkingSpace parkingSpace);

    public List<ParkingSpace> searchSpace(String merchantname);

    public List<ParkingSpace> searchSpaceByNameAndSerialnumber(String merchantname, String serialnumber);

    public int updateParkingSpace(ParkingSpace parkingSpace);

    public int updateParkingSpaceByNameAndSerialnumber(@Param("merchant") String merchant, @Param("serialnumber") String serialnumber, @Param("state") String state);

    public int updateByMerchantChange(MerchantChange merchantChange);

    public int deleteSpaceByMerchantName(String oldname);
}
