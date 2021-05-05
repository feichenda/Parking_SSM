package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.Customer;
import com.lenovo.feizai.entity.Password;
import com.lenovo.feizai.entity.User;

import java.util.List;

public interface UserServiceDao {
    public List<User> selectUserByUserName(User user);

    public int changePasswordByUserName(Password password);

    public int addUser(User user);

    public int updateUserName(String oldname, String newname);
}
