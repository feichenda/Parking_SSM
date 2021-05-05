package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.Merchant;
import com.lenovo.feizai.entity.MerchantChange;

import java.util.List;

/**
 * @author feizai
 * @date 2021/4/10 0010 上午 12:23:40
 * @annotation
 */
public interface MerchantServiceDao {
    public int addMerchant(Merchant merchant);

    public Merchant selectMerchantByName(String username);

    public int updateUserName(String oldname, String newname, String avatar);

    public int updateAvatar(String username, String avatar);

    public int updatePhone(String username, String phone);

    public int updateMerchantQQ(String merchant, String QQ);
}
