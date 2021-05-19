package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.Order;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/26 0026 下午 6:52:35
 * @annotation
 */
public interface SubscribeServiceDao {
    public int addSubscribeOrder(Order order);

    public int updateSubsrcibeOrder(Order order);

    public List<Order> customerFindOrder(String customerName, String startDate, String endDate, String orderType);

    public List<Order> merchantFindOrder(String merchantName, String startDate, String endDate, String orderType);

    public List<Order> selectIngOrderByUser(String username);

    public Order findOrderByNumber(String ordernumber);

    public int updateUserName(String oldname, String newname);

    public int updateOrderState(String state, String ordernumber);

    public int cancelOrder(Order order);

    public Order isSubscribing(String merchant,String car);
}
