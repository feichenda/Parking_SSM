package com.lenovo.feizai.service;

import com.lenovo.feizai.dao.CollectionDao;
import com.lenovo.feizai.entity.CollectionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author feizai
 * @date 2021/3/29 0029 下午 4:24:11
 * @annotation
 */
@Service
public class CollectionService implements CollectionServiceDao {

    @Autowired
    CollectionDao dao;

    @Override
    public List<CollectionInfo> selectCollectionByCustomer(String username) {
        return dao.selectCollectionByCustomer(username);
    }

    @Override
    public int addCollection(CollectionInfo collectionInfo) {
        return dao.addCollection(collectionInfo);
    }

    @Override
    public int deleteCollection(CollectionInfo collectionInfo) {
        return dao.deleteCollection(collectionInfo);
    }

    @Override
    public CollectionInfo isCollection(String username, String merchant) {
        return dao.isCollection(username, merchant);
    }

    @Override
    public int updateCollection(CollectionInfo collectionInfo) {
        return dao.updateCollection(collectionInfo);
    }

    @Override
    public int updateUserName(String oldname, String newname) {
        return dao.updateUserName(oldname, newname);
    }
}
