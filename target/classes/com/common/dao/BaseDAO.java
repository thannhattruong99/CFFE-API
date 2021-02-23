package com.common.dao;

import com.util.IDBHelper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDAO {
    protected SqlSession sqlSession;

    @Autowired
    public BaseDAO(IDBHelper idbHelper) {
        this.sqlSession = idbHelper.openSession();
    }
}