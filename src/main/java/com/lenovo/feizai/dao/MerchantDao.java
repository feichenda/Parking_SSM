package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.Merchant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author feizai
 * @date 2021/4/10 0010 上午 12:23:40
 * @annotation
 */
public interface MerchantDao {
    public int addMerchant(Merchant merchant);

    public Merchant selectMerchantByName(String username);

    public int updateUserName(@Param("oldname") String oldname, @Param("newname") String newname, @Param("avatar") String avatar);

    public int updateAvatar( @Param("username") String username, @Param("avatar") String avatar);

    public int updatePhone(@Param("username") String username, @Param("phone") String phone);

    public int updateMerchantQQ(@Param("merchant") String merchant, @Param("QQ") String QQ);
}
