package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.CheckInfo;

/**
 * @author feizai
 * @date 2021/4/17 0017 下午 3:37:27
 * @annotation
 */
public interface CheckInfoServiceDao {
    public int addCheckInfo(CheckInfo info);

    public CheckInfo selectCheckInfoByCarAndMerchant(String merchant, String car);

    public CheckInfo isPay(String merchant, String car);

    public CheckInfo clearing(String merchant, String car);

    public int updatePayByMoney(CheckInfo checkInfo);

    public int goout(CheckInfo checkInfo);

    public CheckInfo selectUsingSpace(String merchant, String car);

    public CheckInfo isParking(String car);
}
