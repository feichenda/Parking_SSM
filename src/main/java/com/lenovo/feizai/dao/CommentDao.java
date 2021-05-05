package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.Comment;
import com.lenovo.feizai.entity.MerchantChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author feizai
 * @date 2021/4/13 0013 下午 2:49:18
 * @annotation
 */
public interface CommentDao {
    public int addComment(Comment comment);

    public List<Comment> selectCommentByMerchant(@Param("merchant") String merchant, @Param("index") int index);

    public int updateByMerchantChange(MerchantChange merchantChange);

    public int updateAvatar(@Param("username") String username, @Param("avatar") String avatar);

    public int updateUserName(@Param("oldname") String oldname, @Param("newname") String newname);
}
