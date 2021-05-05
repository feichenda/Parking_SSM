package com.lenovo.feizai.controller;

import com.lenovo.feizai.entity.BaseModel;
import com.lenovo.feizai.entity.Comment;
import com.lenovo.feizai.service.CommentServiceDao;
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
 * @date 2021/4/13 0013 下午 2:48:39
 * @annotation
 */
@Controller
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    CommentServiceDao commentServiceDao;

    @ResponseBody
    @RequestMapping("/addComment")
    public String addComment(@RequestBody Comment comment) {
        int result = commentServiceDao.addComment(comment);
        BaseModel model = new BaseModel();
        if (result>0){
            model.setCode(200);
            model.setMessage("评论成功");
        }else {
            model.setCode(201);
            model.setMessage("评论失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/selectCommentByMerchant")
    public String selectCommentByMerchant(@Param("merchant") String merchant, @Param("index") int index) {
        List<Comment> comments = commentServiceDao.selectCommentByMerchant(merchant, index);
        BaseModel<Comment> model = new BaseModel<>();
        if (comments.size()>0){
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(comments);
        }else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }
}
