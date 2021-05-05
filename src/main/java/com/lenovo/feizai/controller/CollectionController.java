package com.lenovo.feizai.controller;

import com.lenovo.feizai.entity.BaseModel;
import com.lenovo.feizai.entity.CollectionInfo;
import com.lenovo.feizai.service.CollectionServiceDao;
import com.lenovo.feizai.util.GsonUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/29 0029 下午 4:20:39
 * @annotation
 */
@Controller
@RequestMapping("/api/collection")
public class CollectionController {

    @Autowired
    CollectionServiceDao collectionServiceDao;

    @ResponseBody
    @RequestMapping("/selectCollectionByCustomer")
    public String selectCollectionByCustomer(@Param("username") String username) {
        List<CollectionInfo> collectionInfos = collectionServiceDao.selectCollectionByCustomer(username);
        BaseModel<CollectionInfo> model = new BaseModel<>();
        if (collectionInfos.size() > 0) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(collectionInfos);
        }else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/isCollection")
    public String isCollection(@Param("username") String username, @Param("merchant") String merchant) {
        CollectionInfo collection = collectionServiceDao.isCollection(username, merchant);
        BaseModel model = new BaseModel();
        if (collection != null){
            model.setCode(200);
            model.setMessage("当前用户已收藏该停车场");
        }else {
            model.setCode(201);
            model.setMessage("当前用户未收藏该停车场");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/addCollection")
    public String addCollection(@RequestBody CollectionInfo collectionInfo) {
        CollectionInfo collection = collectionServiceDao.isCollection(collectionInfo.getUsername(), collectionInfo.getRemark());
        BaseModel model = new BaseModel();
        if (collection==null) {
            int result = collectionServiceDao.addCollection(collectionInfo);
            if (result > 0) {
                model.setCode(200);
                model.setMessage("收藏成功");
            } else {
                model.setCode(201);
                model.setMessage("收藏失败");
            }
        }else {
            model.setCode(202);
            model.setMessage("您已收藏该停车场");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateCollection")
    public String updateCollection(@RequestBody CollectionInfo collectionInfo){
        CollectionInfo collection = collectionServiceDao.isCollection(collectionInfo.getUsername(), collectionInfo.getRemark());
        BaseModel model = new BaseModel();
        if (collection==null) {
            int result = collectionServiceDao.updateCollection(collectionInfo);
            if (result > 0) {
                model.setCode(200);
                model.setMessage("修改成功");
            } else {
                model.setCode(201);
                model.setMessage("修改失败");
            }
        }else {
            if (collection.getRemark().equals(collectionInfo.getRemark())&&collection.getId()==collectionInfo.getId()){
                int result = collectionServiceDao.updateCollection(collectionInfo);
                if (result > 0) {
                    model.setCode(200);
                    model.setMessage("修改成功");
                } else {
                    model.setCode(201);
                    model.setMessage("修改失败");
                }
            }else {
                model.setCode(202);
                model.setMessage("您已收藏该停车场");
            }
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/deleteCollection")
    public String deleteCollection(@RequestBody CollectionInfo collectionInfo) {
        int result = collectionServiceDao.deleteCollection(collectionInfo);
        BaseModel model = new BaseModel();
        if (result>0){
            model.setCode(200);
            model.setMessage("取消收藏成功");
        }else {
            model.setCode(201);
            model.setMessage("取消收藏失败");
        }
        return GsonUtil.GsonString(model);
    }
}
