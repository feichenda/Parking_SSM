package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.MerchantChange;
import com.lenovo.feizai.entity.Rates;

import java.util.List;

/**
 * @author feizai
 * @date 02/25/2021 025 9:40:49 PM
 * @annotation
 */
public interface RatesServiceDao {
    public int addRates(Rates rates);

    public List<Rates> selectRatesByName(String merchantname);

    public List<Rates> selectRatesByMerchant(String merchantname);

    public int updateByMerchantChange(MerchantChange merchantChange);

    public Rates findRatesByMerchant(String merchant);
}
