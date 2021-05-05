package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.Customer;

import java.util.List;

/**
 * @author feizai
 * @date 01/27/2021 027 3:29:32 PM
 */
public interface CustomerServiceDao {
    public int addCustomer(Customer customer);

    public Customer selectCustomer(String username);

    public List<Customer> selectAllCustomer();

    public List<Customer> selectCustomerByKey(String keyword);

    public int updateHomeAddressInfo(Customer customer);

    public int updateCompanyAddressInfo(Customer customer);

    public int updateAvatar(String username, String avatar);

    public int updatePhone(String username, String phone);

    public int updateUserName(String oldname, String newname, String avatar);

    public int updateCustomerQQ(String customer, String QQ);
}
