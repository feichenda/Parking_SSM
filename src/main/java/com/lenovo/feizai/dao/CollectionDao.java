package com.lenovo.feizai.dao;

import com.lenovo.feizai.entity.CollectionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/29 0029 下午 4:24:11
 * @annotation
 */
public interface CollectionDao {
    public List<CollectionInfo> selectCollectionByCustomer(String username);

    public int addCollection(CollectionInfo collectionInfo);

    public int deleteCollection(CollectionInfo collectionInfo);

    public CollectionInfo isCollection(@Param("username") String username, @Param("merchant") String merchant);

    public int updateCollection(CollectionInfo collectionInfo);

    public int updateUserName(@Param("oldname") String oldname, @Param("newname") String newname);
}
