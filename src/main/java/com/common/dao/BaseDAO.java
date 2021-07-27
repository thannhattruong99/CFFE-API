package com.common.dao;

import com.util.IDBHelper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDAO {
    protected SqlSession sqlSession;
    IDBHelper idbHelper;

    @Autowired
    public BaseDAO(IDBHelper idbHelper) {
        this.idbHelper = idbHelper;
    }

    public void openSession() {
        this.sqlSession = idbHelper.makeConnection().openSession(TransactionIsolationLevel.READ_COMMITTED);
    }

    public void closeSession(){
        if(sqlSession != null){
            sqlSession.commit();
        }
    }
}