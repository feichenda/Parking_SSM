package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.SubscribeDao;
import com.lenovo.feizai.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/26 0026 下午 6:52:35
 * @annotation
 */
@Service
public class SubscribeService implements SubscribeServiceDao {

    @Autowired
    SubscribeDao dao;

    @Override
    public int addSubscribeOrder(Order order) {
        return dao.addSubscribeOrder(order);
    }

    @Override
    public int updateSubsrcibeOrder(Order order) {
        return dao.updateSubsrcibeOrder(order);
    }

    @Override
    public List<Order> customerFindOrder(String customerName, String startDate, String endDate, String orderType) {
        return dao.customerFindOrder(customerName, startDate, endDate, orderType);
    }

    @Override
    public List<Order> merchantFindOrder(String merchantName, String startDate, String endDate, String orderType) {
        return dao.merchantFindOrder(merchantName, startDate, endDate, orderType);
    }

    @Override
    public List<Order> selectIngOrderByUser(String username) {
        return dao.selectIngOrderByUser(username);
    }

    @Override
    public Order findOrderByNumber(String ordernumber) {
        return dao.findOrderByNumber(ordernumber);
    }

    @Override
    public int updateUserName(String oldname, String newname) {
        return dao.updateUserName(oldname, newname);
    }

    @Override
    public int updateOrderState(String state, String ordernumber) {
        return dao.updateOrderState(state, ordernumber);
    }

    @Override
    public int cancelOrder(Order order) {
        return dao.cancelOrder(order);
    }

//    @Override
//    public Order isSubscribing(String merchant, String car) {
//        return dao.isSubscribing(merchant, car);
//    }

    @Override
    public Order isSubscribing(String car) {
        return dao.isSubscribing(car);
    }
}
