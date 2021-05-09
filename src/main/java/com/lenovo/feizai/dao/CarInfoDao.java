package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.CarInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/23 0023 下午 5:22:36
 * @annotation
 */
public interface CarInfoDao {
    public int addCarInfo(CarInfo carInfo);

    public List<CarInfo> selectCarByUsernam(String username);

    public List<String> selectCarLicenseByUsernam(String username);

    public CarInfo selectCarByLicense(CarInfo carInfo);

    public List<String> selectNoFreeCarByUsername(String username);

    public int updateCarInfo(CarInfo carInfo);

    public int deleteCar(CarInfo carInfo);

    public int updateUserName(@Param("oldname") String oldname, @Param("newname") String newname);
}
