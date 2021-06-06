package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/26 0026 下午 6:52:35
 * @annotation
 */
public interface SubscribeDao {
    public int addSubscribeOrder(Order order);

    public int updateSubsrcibeOrder(Order order);

    public List<Order> customerFindOrder(@Param("customerName") String customerName, @Param("statrDate") String startDate, @Param("endDate") String endDate, @Param("orderType") String orderType);

    public List<Order> merchantFindOrder(@Param("merchantName") String merchantName, @Param("statrDate") String startDate, @Param("endDate") String endDate, @Param("orderType") String orderType);

    public List<Order> selectIngOrderByUser(String username);

    public Order findOrderByNumber(String ordernumber);

    public int updateUserName(@Param("oldname") String oldname, @Param("newname") String newname);

    public int updateOrderState(@Param("state") String state, @Param("ordernumber") String ordernumber);

    public int cancelOrder(Order order);

//    public Order isSubscribing(@Param("merchantname") String merchantname,@Param("car") String car);
    public Order isSubscribing(String car);
}
