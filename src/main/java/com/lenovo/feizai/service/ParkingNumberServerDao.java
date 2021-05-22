package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.MerchantChange;
import com.lenovo.feizai.entity.ParkingNumber;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/17 0017 下午 2:42:56
 * @annotation
 */
public interface ParkingNumberServerDao {
    public int addParkingNumber(ParkingNumber parkingNumber);

    public List<ParkingNumber> selectNumber(String keyword);

    public List<ParkingNumber> selectNumberByname(String name);

    public int updateNumber(ParkingNumber parkingNumber);

    public int updateByMerchantChange(MerchantChange merchantChange);

    public ParkingNumber selectNumberByMerchantnumber(String merchant);

    public int readdParkingNumber(ParkingNumber parkingNumber, String oldname);
}
