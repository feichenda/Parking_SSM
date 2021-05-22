package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.*;

import java.util.List;

/**
 * @author feizai
 * @date 02/22/2021 022 9:17:25 PM
 * @annotation
 */
public interface ParkingInfoServiceDao {
    public int addParking(ParkingInfo parkingInfo);

    public int updateParking(ParkingInfo parkingInfo);

    public List<ParkingInfo> selectAllMerchant();

    public List<MerchantLoaction> selectMerchantBasicInfo();

    public List<MerchantLoaction> selectMerchant(String keyword);

    public ParkingInfo selectMerchantByMerchantName(String merchantname);

    public List<MerchantLoaction> selectMerchantByUsername(String usernmae);

    public int updateImages(String certificate_image, String parking_image, String merchantname);

    public List<MerchantLoaction> selectMerchantByKey(String keyword);

    public int updateByMerchantChange(MerchantChange merchantChange);

    public int updateUserName(String oldname, String newname);

    public int updateParkingInfoLink(String merchant, String phone, String linkname, String QQ);

    public int readdParkingInfo(ParkingInfo parkingInfo, String oldname);
}
