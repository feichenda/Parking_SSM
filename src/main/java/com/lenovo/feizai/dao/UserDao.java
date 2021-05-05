package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.Customer;
import com.lenovo.feizai.entity.Password;
import com.lenovo.feizai.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    public List<User> selectUserByUserName(User user);

    public int changePasswordByUserName(Password password);

    public int addUser(User user);

    public int updateUserName(@Param("oldname") String oldname, @Param("newname") String newname);
}
