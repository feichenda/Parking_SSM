package com.lenovo.feizai.controller;

import com.lenovo.feizai.entity.*;
import com.lenovo.feizai.service.*;
import com.lenovo.feizai.util.FileUtil;
import com.lenovo.feizai.util.GsonUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserServiceDao userserviceDao;

    @Autowired
    CustomerServiceDao customerServiceDao;

    @Autowired
    ParkingInfoServiceDao parkingInfoServiceDao;

    @Autowired
    MerchantServiceDao merchantServiceDao;

    @Autowired
    CarInfoServiceDao carInfoServiceDao;

    @Autowired
    CollectionServiceDao collectionServiceDao;

    @Autowired
    CommentServiceDao commentServiceDao;

    @Autowired
    MerchantChangeServiceDao merchantChangeServiceDao;

    @Autowired
    SubscribeServiceDao subscribeServiceDao;

    @ResponseBody
    @RequestMapping("/login")//用户登录
    public String login(@RequestBody User user) {
        BaseModel model = new BaseModel();
        List<User> result = userserviceDao.selectUserByUserName(user);
        if (result.size() > 0 && user.getRole().equals(result.get(0).getRole())) {
            //存在该用户
            if (user.getPassword().equals(result.get(0).getPassword())) {
                model.setCode(200);
                model.setMessage("登录成功");
                return GsonUtil.GsonString(model);
            } else {
                model.setCode(201);
                model.setMessage("密码错误");
                return GsonUtil.GsonString(model);
            }
        } else {
            //不存在该用户
            model.setCode(202);
            model.setMessage("用户不存在");
            return GsonUtil.GsonString(model);
        }
    }

    @ResponseBody
    @RequestMapping("/changepassword")//用户修改密码
    public String changepassword(@RequestBody Password password) {
        User user = new User();
        BaseModel model = new BaseModel();
        user.setUsername(password.getUsername());
        user.setRole(password.getRole());
        List<User> users = userserviceDao.selectUserByUserName(user);
        if (users.size() > 0) {
            String old = users.get(0).getPassword();
            if (old.equals(password.getOldpassword())) {
                int result = userserviceDao.changePasswordByUserName(password);
                if (result > 0) {
                    model.setCode(200);
                    model.setMessage("修改成功");
                    return GsonUtil.GsonString(model);
                } else {
                    model.setCode(201);
                    model.setMessage("修改失败");
                    return GsonUtil.GsonString(model);
                }
            } else {
                model.setCode(202);
                model.setMessage("修改失败,原密码输入错误");
                return GsonUtil.GsonString(model);
            }
        } else {
            model.setCode(203);
            model.setMessage("修改失败,不存在该用户");
            return GsonUtil.GsonString(model);
        }
    }

    @RequestMapping(value = "/jsplogout")
    public String jsplogout(String name) {
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)//用户注册
    public String register(@RequestBody User user, String phone) {
        BaseModel model = new BaseModel();
        List<User> result = userserviceDao.selectUserByUserName(user);
        if (result.size() == 0) {
            userserviceDao.addUser(user);
            switch (user.getRole()) {
                case "用户":
                    Customer customer = new Customer();
                    customer.setUsername(user.getUsername());
                    customer.setPhone(phone);
                    customer.setAvatar("image/avatar.jpg");
                    System.out.println(customer.toString());
                    customerServiceDao.addCustomer(customer);
                    model.setCode(200);
                    model.setMessage("注册成功");
                    return GsonUtil.GsonString(model);
                case "商家":
                    Merchant merchant = new Merchant();
                    merchant.setUsername(user.getUsername());
                    merchant.setPhone(phone);
                    merchant.setAvatar("image/avatar.jpg");
                    merchantServiceDao.addMerchant(merchant);
                    model.setCode(200);
                    model.setMessage("注册成功");
                    return GsonUtil.GsonString(model);
            }
            model.setCode(201);
            model.setMessage("注册失败");
            return GsonUtil.GsonString(model);
        } else {
            model.setCode(202);
            model.setMessage("该用户已存在");
            return GsonUtil.GsonString(model);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/forget", method = RequestMethod.GET)//用户忘记密码找回
    public String forget(String user, String phone, String role) {
        BaseModel model = new BaseModel();
        if (role.equals("用户")) {
            Customer result = customerServiceDao.selectCustomer(user);
            if (result == null) {
                model.setCode(201);
                model.setMessage("该用户不存在");
                return GsonUtil.GsonString(model);
            } else {
                if (result.getPhone().equals(phone)) {
                    model.setCode(200);
                    model.setMessage("信息正确");
                    return GsonUtil.GsonString(model);
                } else {
                    model.setCode(202);
                    model.setMessage("您输入的手机号码有误");
                    return GsonUtil.GsonString(model);
                }
            }
        } else {
            Merchant merchants = merchantServiceDao.selectMerchantByName(user);
            if (merchants == null) {
                model.setCode(201);
                model.setMessage("该用户不存在");
                return GsonUtil.GsonString(model);
            } else {
                if (merchants.getPhone().equals(phone)) {
                    model.setCode(200);
                    model.setMessage("信息正确");
                    return GsonUtil.GsonString(model);
                } else {
                    model.setCode(202);
                    model.setMessage("您输入的手机号码有误");
                    return GsonUtil.GsonString(model);
                }
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/find", method = RequestMethod.GET)//查找用户
    public String find(String user, String password) {
        BaseModel model = new BaseModel();
        Password pass = new Password();
        pass.setUsername(user);
        pass.setNewpassword(password);
        pass.setRole("用户");
        int result = userserviceDao.changePasswordByUserName(pass);
        if (result > 0) {
            model.setCode(200);
            model.setMessage("修改成功");
            return GsonUtil.GsonString(model);
        } else {
            model.setCode(201);
            model.setMessage("修改失败");
            return GsonUtil.GsonString(model);
        }
    }

    @ResponseBody
    @RequestMapping("/updateUsername")//变更用户名
    public String updateUsername(@Param("oldname") String oldname, @Param("newname") String newname, @Param("role") String role) {
        User user = new User();
        user.setUsername(newname);
        List<User> users = userserviceDao.selectUserByUserName(user);
        BaseModel<String> model = new BaseModel<>();
        if (users.size() > 0) {
            model.setCode(201);
            model.setMessage("该用户名已存在");
        } else {
            switch (role) {
                case "用户":
                    Customer customer = customerServiceDao.selectCustomer(oldname);
                    try {
                        FileUtil.renameAvatar(oldname, newname);
                    } catch (IOException e) {
                        e.printStackTrace();
                        model.setCode(202);
                        model.setMessage("重命名失败");
                        return GsonUtil.GsonString(model);
                    }
                    String useravatar = customer.getAvatar();
                    String replace = useravatar.replace(oldname, newname);
                    int result = customerServiceDao.updateUserName(oldname, newname, replace);
                    int result1 = carInfoServiceDao.updateUserName(oldname, newname);
                    int result2 = collectionServiceDao.updateUserName(oldname, newname);
                    int result3 = commentServiceDao.updateUserName(oldname, newname);
                    int result4 = customerServiceDao.updateAvatar(newname, replace);
                    int result5 = subscribeServiceDao.updateUserName(oldname, newname);
                    int result6 = userserviceDao.updateUserName(oldname, newname);
                    if (result > 0 && result1 > 0 && result2 > 0 && result3 > 0 && result4 > 0 && result5 > 0 && result6 > 0) {
                        model.setCode(200);
                        model.setMessage("更新成功");
                        model.setData(replace);
                    } else {
                        model.setCode(201);
                        model.setMessage("更新失败");
                    }
                    break;
                case "商家":
                    Merchant merchant = merchantServiceDao.selectMerchantByName(oldname);
                    try {
                        FileUtil.renameAvatar(oldname, newname);
                    } catch (IOException e) {
                        e.printStackTrace();
                        model.setCode(202);
                        model.setMessage("重命名失败");
                        return GsonUtil.GsonString(model);
                    }
                    String merchantavatar = merchant.getAvatar();
                    String replace1 = merchantavatar.replace(oldname, newname);
                    int result7 = merchantServiceDao.updateUserName(oldname, newname, replace1);
                    int result8 = merchantChangeServiceDao.updateUserName(oldname, newname);
                    int result9 = parkingInfoServiceDao.updateUserName(oldname, newname);
                    int result10 = userserviceDao.updateUserName(oldname, newname);
                    if (result7 > 0 && result8 > 0 && result9 > 0 && result10 > 0) {
                        model.setCode(200);
                        model.setMessage("更新成功");
                        model.setData(replace1);
                    } else {
                        model.setCode(201);
                        model.setMessage("更新失败");
                    }
                    break;
            }
        }
        return GsonUtil.GsonString(model);
    }
}
