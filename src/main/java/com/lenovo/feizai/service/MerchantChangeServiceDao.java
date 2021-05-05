package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.MerchantChange;

/**
 * @author feizai
 * @date 2021/4/10 0010 下午 3:52:26
 * @annotation
 */
public interface MerchantChangeServiceDao {
    public int addMerchantChange(MerchantChange change);

    public int updateMerchantChange(MerchantChange change);

    public MerchantChange selectMerchatChangeByOldName(String oldemerchantname);

    public int updateMerchantChangeState(String auditstate, String remark, String oldname);

    public int updateUserName(String oldname, String newname);
}
