package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.CommentDao;
import com.lenovo.feizai.entity.Comment;
import com.lenovo.feizai.entity.MerchantChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 2021/4/13 0013 下午 2:54:28
 * @annotation
 */
@Service
public class CommentService implements CommentServiceDao {
    @Autowired
    CommentDao dao;

    @Override
    public int addComment(Comment comment) {
        return dao.addComment(comment);
    }

    @Override
    public List<Comment> selectCommentByMerchant(String merchant, int index) {
        return dao.selectCommentByMerchant(merchant, index);
    }

    @Override
    public int updateByMerchantChange(MerchantChange merchantChange) {
        return dao.updateByMerchantChange(merchantChange);
    }

    @Override
    public int updateAvatar(String username, String avatar) {
        return dao.updateAvatar(username, avatar);
    }

    @Override
    public int updateUserName(String oldname, String newname) {
        return dao.updateUserName(oldname, newname);
    }
}
