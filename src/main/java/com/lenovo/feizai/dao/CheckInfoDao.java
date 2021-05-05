package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.CheckInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author feizai
 * @date 2021/4/17 0017 下午 3:37:11
 * @annotation
 */
public interface CheckInfoDao {
    public int addCheckInfo(CheckInfo info);

    public CheckInfo selectCheckInfoByCarAndMerchant(@Param("merchant") String merchant, @Param("car") String car);

    public CheckInfo isPay(@Param("merchant") String merchant, @Param("car") String car);

    public CheckInfo clearing(@Param("merchant") String merchant, @Param("car") String car);

    public int updatePayByMoney(CheckInfo checkInfo);

    public int goout(CheckInfo checkInfo);

    public CheckInfo selectUsingSpace(@Param("merchant") String merchant, @Param("car") String car);

    public CheckInfo isParking(String car);
}
