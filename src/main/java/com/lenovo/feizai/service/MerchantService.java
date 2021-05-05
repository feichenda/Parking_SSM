package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.MerchantDao;
import com.lenovo.feizai.entity.Merchant;
import com.lenovo.feizai.entity.MerchantChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 2021/4/10 0010 上午 12:25:06
 * @annotation
 */
@Service
public class MerchantService implements MerchantServiceDao {

    @Autowired
    MerchantDao dao;
    @Override
    public int addMerchant(Merchant merchant) {
        return dao.addMerchant(merchant);
    }

    @Override
    public Merchant selectMerchantByName(String username) {
        return dao.selectMerchantByName(username);
    }

    @Override
    public int updateUserName(String oldname, String newname,String avatar) {
        return dao.updateUserName(oldname, newname, avatar);
    }

    @Override
    public int updateAvatar(String username, String avatar) {
        return dao.updateAvatar(username, avatar);
    }

    @Override
    public int updatePhone(String username, String phone) {
        return dao.updatePhone(username, phone);
    }

    @Override
    public int updateMerchantQQ(String merchant, String QQ) {
        return dao.updateMerchantQQ(merchant, QQ);
    }
}
