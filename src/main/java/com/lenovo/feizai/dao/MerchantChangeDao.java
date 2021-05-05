package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.MerchantChange;
import org.apache.ibatis.annotations.Param;

/**
 * @author feizai
 * @date 2021/4/10 0010 下午 3:52:26
 * @annotation
 */
public interface MerchantChangeDao {
    public int addMerchantChange(MerchantChange change);

    public int updateMerchantChange(MerchantChange change);

    public MerchantChange selectMerchatChangeByOldName(String oldemerchantname);

    public int updateMerchantChangeState(@Param("oldname") String oldname, @Param("auditstate") String auditstate, @Param("remark") String remark);

    public int updateUserName(@Param("oldname") String oldname, @Param("newname") String newname);


}
