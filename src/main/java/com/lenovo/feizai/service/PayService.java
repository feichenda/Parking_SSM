package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.PayDao;
import com.lenovo.feizai.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author feizai
 * @date 2021/4/20 0020 下午 3:11:13
 * @annotation
 */
@Service
public class PayService implements PayServiceDao {

    @Autowired
    PayDao dao;

    @Override
    public int addPayOrder(Order order) {
        return dao.addPayOrder(order);
    }
}
