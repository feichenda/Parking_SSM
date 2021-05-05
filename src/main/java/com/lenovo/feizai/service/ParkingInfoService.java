package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.ParkingInfoDao;
import com.lenovo.feizai.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 02/22/2021 022 9:17:25 PM
 * @annotation
 */
@Service
public class ParkingInfoService implements ParkingInfoServiceDao {

    @Autowired
    ParkingInfoDao dao;

    @Override
    public int addParking(ParkingInfo parkingInfo) {
        return dao.addParking(parkingInfo);
    }

    @Override
    public int updateParking(ParkingInfo parkingInfo) {
        return dao.updateParking(parkingInfo);
    }

    @Override
    public List<ParkingInfo> selectAllMerchant() {
        return dao.selectAllMerchant();
    }

    @Override
    public List<MerchantLoaction> selectMerchantBasicInfo() {
        return dao.selectMerchantBasicInfo();
    }

    @Override
    public List<MerchantLoaction> selectMerchant(String keyword) {
        return dao.selectMerchant(keyword);
    }

    @Override
    public ParkingInfo selectMerchantByMerchantName(String merchantname) {
        return dao.selectMerchantByMerchantName(merchantname);
    }

    @Override
    public List<MerchantLoaction> selectMerchantByUsername(String usernmae) {
        return dao.selectMerchantByUsername(usernmae);
    }

    @Override
    public int updateImages(String certificate_image, String parking_image, String merchantname) {
        return dao.updateImages(certificate_image, parking_image, merchantname);
    }

    @Override
    public List<MerchantLoaction> selectMerchantByKey(String keyword) {
        return dao.selectMerchantByKey(keyword);
    }

    @Override
    public int updateByMerchantChange(MerchantChange merchantChange) {
        return dao.updateByMerchantChange(merchantChange);
    }

    @Override
    public int updateUserName(String oldname, String newname) {
        return dao.updateUserName(oldname, newname);
    }

    @Override
    public int updateParkingInfoLink(String merchant, String phone, String linkname, String QQ) {
        return dao.updateParkingInfoLink(merchant, phone, linkname, QQ);
    }

}
