package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.UserDao;
import com.lenovo.feizai.entity.Customer;
import com.lenovo.feizai.entity.Password;
import com.lenovo.feizai.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceDao {

    @Autowired
    UserDao dao;

    @Override
    public List<User> selectUserByUserName(User user) {
        return dao.selectUserByUserName(user);
    }

    @Override
    public int changePasswordByUserName(Password password) {
        return dao.changePasswordByUserName(password);
    }

    @Override
    public int addUser(User user) {
        return dao.addUser(user);
    }

    @Override
    public int updateUserName(String oldname, String newname) {
        return dao.updateUserName(oldname, newname);
    }
}
