package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.CustomerDao;
import com.lenovo.feizai.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 01/27/2021 027 3:30:27 PM
 */
@Service
public class CustomerService implements CustomerServiceDao {

    @Autowired
    CustomerDao dao;

    @Override
    public int addCustomer(Customer customer) {
        return dao.addCustomer(customer);
    }

    @Override
    public Customer selectCustomer(String username) {
        return dao.selectCustomer(username);
    }

    @Override
    public List<Customer> selectAllCustomer() {
        return dao.selectAllCustomer();
    }

    @Override
    public List<Customer> selectCustomerByKey(String keyword) {
        return dao.selectCustomerByKey(keyword);
    }

    @Override
    public int updateHomeAddressInfo(Customer customer) {
        return dao.updateHomeAddressInfo(customer);
    }

    @Override
    public int updateCompanyAddressInfo(Customer customer) {
        return dao.updateCompanyAddressInfo(customer);
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
    public int updateUserName(String oldname, String newname, String avatar) {
        return dao.updateUserName(oldname, newname, avatar);
    }

    @Override
    public int updateCustomerQQ(String customer, String QQ) {
        return dao.updateCustomerQQ(customer, QQ);
    }
}
