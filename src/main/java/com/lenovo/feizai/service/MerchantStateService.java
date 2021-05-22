package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.MerchantStateDao;
import com.lenovo.feizai.entity.MerchantChange;
import com.lenovo.feizai.entity.MerchantLoaction;
import com.lenovo.feizai.entity.MerchantProperty;
import com.lenovo.feizai.entity.MerchantState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 2021/4/5 0005 下午 6:10:01
 * @annotation
 */
@Service
public class MerchantStateService implements MerchantStateServiceDao {
    @Autowired
    MerchantStateDao dao;

    @Override
    public int addMerchantState(MerchantState state) {
        return dao.addMerchantState(state);
    }

    @Override
    public List<MerchantState> selectAllMerchantState() {
        return dao.selectAllMerchantState();
    }

    @Override
    public MerchantState selectCheckInfoByName(String merchant) {
        return dao.selectCheckInfoByName(merchant);
    }

    @Override
    public List<MerchantState> selectMerchantState(String keyword) {
        return dao.selectMerchantState(keyword);
    }

    @Override
    public List<MerchantLoaction> selectUncheckMerchant(String keyword) {
        return dao.selectUncheckMerchant(keyword);
    }

    @Override
    public List<MerchantChange> selectUncheckChangeMerchant(String keyword) {
        return dao.selectUncheckChangeMerchant(keyword);
    }

    @Override
    public List<MerchantState> selectMerchantStateByName(String merchant) {
        return dao.selectMerchantStateByName(merchant);
    }

    @Override
    public int updateCheckInfoByName(MerchantState state) {
        return dao.updateCheckInfoByName(state);
    }

    @Override
    public MerchantState findMerchantState(String merchant) {
        return dao.findMerchantState(merchant);
    }

    @Override
    public int updateParkingState(String merchant, String state) {
        return dao.updateParkingState(merchant, state);
    }

    @Override
    public int updateByMerchantChange(MerchantChange merchantChange) {
        return dao.updateByMerchantChange(merchantChange);
    }

    @Override
    public int readdMerchantStatus(MerchantState merchantState, String oldname) {
        return dao.readdMerchantStatus(merchantState, oldname);
    }
}
