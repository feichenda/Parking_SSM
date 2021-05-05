package com.lenovo.feizai.service;

import com.lenovo.feizai.entity.CollectionInfo;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/29 0029 下午 4:24:11
 * @annotation
 */
public interface CollectionServiceDao {
    public List<CollectionInfo> selectCollectionByCustomer(String username);

    public int addCollection(CollectionInfo collectionInfo);

    public int deleteCollection(CollectionInfo collectionInfo);

    public CollectionInfo isCollection(String username, String merchant);

    public int updateCollection(CollectionInfo collectionInfo);

    public int updateUserName(String oldname, String newname);
}
