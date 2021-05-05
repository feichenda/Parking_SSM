package com.lenovo.feizai.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.feizai.entity.BaseModel;
import com.lenovo.feizai.entity.Customer;
import com.lenovo.feizai.service.CommentServiceDao;
import com.lenovo.feizai.service.CustomerServiceDao;
import com.lenovo.feizai.util.FileUtil;
import com.lenovo.feizai.util.GsonUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author feizai
 * @date 2021/3/21 0021 下午 7:48:40
 * @annotation
 */
@Controller
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerServiceDao customerServiceDao;

    @Autowired
    CommentServiceDao commentServiceDao;

    @RequestMapping("/selectAllUser")
    public String selectAllUser(@RequestParam(required = true,defaultValue = "1") int page, Model model) {
        PageHelper.startPage(page,10);
        List<Customer> result = customerServiceDao.selectAllCustomer();
        PageInfo<Customer> pageInfo = new PageInfo<>(result);
        model.addAttribute("pagemsg", pageInfo);
        model.addAttribute("userinfo", result);
        model.addAttribute("getTotal", pageInfo.getTotal());
        return "customerinfo";
    }

    @RequestMapping("/selectUserByUsername")
    public String selectUserByUsername(String name, Model model) {
        Customer result = customerServiceDao.selectCustomer(name);
        if (result!=null) {
            model.addAttribute("userdetailedinfo", result);
            return "customerdetailedinfo";
        } else {
            return null;
        }
    }

    @RequestMapping("/selectUserByKey")
    public String selectCustomerByKey(@RequestParam(required = true,defaultValue = "1") int page, String keyword, Model model) {
        keyword = keyword.trim();
        PageHelper.startPage(page,10);
        List<Customer> result = customerServiceDao.selectCustomerByKey(keyword);
        PageInfo<Customer> pageInfo = new PageInfo<>(result);
        model.addAttribute("pagemsg", pageInfo);
        model.addAttribute("userinfo", result);
        model.addAttribute("getTotal", pageInfo.getTotal());
        return "customerinfo";
    }

    @ResponseBody
    @RequestMapping("/updateHomeAddressInfo")
    public String updateHomeAddressInfo(@RequestBody Customer customer) {
        int result = customerServiceDao.updateHomeAddressInfo(customer);
        BaseModel model = new BaseModel();
        if (result > 0) {
            model.setCode(200);
            model.setMessage("更新成功");
            return GsonUtil.GsonString(model);
        } else {
            model.setCode(201);
            model.setMessage("更新失败");
            return GsonUtil.GsonString(model);
        }
    }

    @ResponseBody
    @RequestMapping("/updateCompanyAddressInfo")
    public String updateCompanyAddressInfo(@RequestBody Customer customer) {
        int result = customerServiceDao.updateCompanyAddressInfo(customer);
        BaseModel model = new BaseModel();
        if (result > 0) {
            model.setCode(200);
            model.setMessage("更新成功");
            return GsonUtil.GsonString(model);
        } else {
            model.setCode(201);
            model.setMessage("更新失败");
            return GsonUtil.GsonString(model);
        }
    }

    @ResponseBody
    @RequestMapping("/selectCustomerByUsername")
    public String selectCustomerByUsername(@Param("name") String name) {
        Customer result = customerServiceDao.selectCustomer(name);
        BaseModel<Customer> model = new BaseModel<>();
        if (result != null) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setData(result);
            return GsonUtil.GsonString(model);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
            return GsonUtil.GsonString(model);
        }
    }

    @ResponseBody
    @RequestMapping("/upadteCustomerAvatar")
    public String updateAvatar(@Param("username") String username, @RequestParam("file") MultipartFile file) {
        BaseModel<String> model = new BaseModel<>();
        try {
            String filePath = FileUtil.getAvatarFilePath(file, username);
            int result = customerServiceDao.updateAvatar(username, filePath);
            commentServiceDao.updateAvatar(username, filePath);
            if (result > 0) {
                model.setCode(200);
                model.setMessage("头像上传成功");
                model.setData(filePath);
                return GsonUtil.GsonString(model);
            } else {
                model.setCode(201);
                model.setMessage("头像上传失败");
                return GsonUtil.GsonString(model);
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.setCode(201);
            model.setMessage("头像上传失败");
            return GsonUtil.GsonString(model);
        }
    }

    @ResponseBody
    @RequestMapping("/updateCustomerPhone")
    public String updatePhone(@Param("username") String username, @Param("phone") String phone){
        int result = customerServiceDao.updatePhone(username, phone);
        BaseModel<String> model = new BaseModel<>();
        if (result>0){
            model.setCode(200);
            model.setMessage("更新成功");
            model.setData(phone);
        }else {
            model.setCode(201);
            model.setMessage("更新失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateCustomerQQ")
    public String updateCustomerQQ(@Param("customer") String customer, @Param("QQ") String QQ) {
        int result = customerServiceDao.updateCustomerQQ(customer, QQ);
        BaseModel<String> model = new BaseModel();
        if (result > 0) {
            model.setCode(200);
            model.setMessage("更新成功");
            model.setData(QQ);
        } else {
            model.setCode(201);
            model.setMessage("更新失败");
        }
        return GsonUtil.GsonString(model);
    }

}
