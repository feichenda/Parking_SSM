package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.MerchantChangeDao;
import com.lenovo.feizai.entity.MerchantChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author feizai
 * @date 2021/4/10 0010 下午 3:53:21
 * @annotation
 */
@Service
public class MerchantChangeService implements MerchantChangeServiceDao {
    @Autowired
    MerchantChangeDao dao;

    @Override
    public int addMerchantChange(MerchantChange change) {
        return dao.addMerchantChange(change);
    }

    @Override
    public int updateMerchantChange(MerchantChange change) {
        return dao.updateMerchantChange(change);
    }

    @Override
    public MerchantChange selectMerchatChangeByOldName(String oldemerchantname) {
        return dao.selectMerchatChangeByOldName(oldemerchantname);
    }

    @Override
    public int updateMerchantChangeState(String auditstate, String remark, String oldname) {
        return dao.updateMerchantChangeState(auditstate, remark, oldname);
    }

    @Override
    public int updateUserName(String oldname, String newname) {
        return dao.updateUserName(oldname, newname);
    }
}
