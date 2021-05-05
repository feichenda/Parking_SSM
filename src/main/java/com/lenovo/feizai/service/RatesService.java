package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.RatesDao;
import com.lenovo.feizai.entity.MerchantChange;
import com.lenovo.feizai.entity.Rates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 02/25/2021 025 9:40:49 PM
 * @annotation
 */
@Service
public class RatesService implements RatesServiceDao {

    @Autowired
    RatesDao dao;

    @Override
    public int addRates(Rates rates) {
        return dao.addRates(rates);
    }

    @Override
    public List<Rates> selectRatesByName(String merchantname) {
        return dao.selectRatesByName(merchantname);
    }

    @Override
    public List<Rates> selectRatesByMerchant(String merchantname) {
        return dao.selectRatesByMerchant(merchantname);
    }

    @Override
    public int updateByMerchantChange(MerchantChange merchantChange) {
        return dao.updateByMerchantChange(merchantChange);
    }

    @Override
    public Rates findRatesByMerchant(String merchant) {
        return dao.findRatesByMerchant(merchant);
    }
}
