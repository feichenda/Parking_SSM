package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.Order;

/**
 * @author feizai
 * @date 2021/4/20 0020 下午 3:11:13
 * @annotation
 */
public interface PayDao {
    public int addPayOrder(Order order);
}
