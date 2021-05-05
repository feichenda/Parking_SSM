package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author feizai
 * @date 01/27/2021 027 3:29:32 PM
 */
public interface CustomerDao {
    public int addCustomer(Customer customer);

    public Customer selectCustomer(String username);

    public List<Customer> selectAllCustomer();

    public List<Customer> selectCustomerByKey(String keyword);

    public int updateHomeAddressInfo(Customer customer);

    public int updateCompanyAddressInfo(Customer customer);

    public int updateAvatar(@Param("username") String username, @Param("avatar") String avatar);

    public int updatePhone(@Param("username") String username, @Param("phone") String phone);

    public int updateUserName(@Param("oldname") String oldname, @Param("newname") String newname, @Param("avatar") String avatar);

    public int updateCustomerQQ(@Param("customer") String customer, @Param("QQ") String QQ);
}
