package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.Comment;
import com.lenovo.feizai.entity.MerchantChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author feizai
 * @date 2021/4/13 0013 下午 2:49:18
 * @annotation
 */
public interface CommentServiceDao {
    public int addComment(Comment comment);

    public List<Comment> selectCommentByMerchant(@Param("merchant") String merchant, @Param("index") int index);

    public int updateByMerchantChange(MerchantChange merchantChange);

    public int updateAvatar(String username, String avatar);

    public int updateUserName(String oldname, String newname);
}
