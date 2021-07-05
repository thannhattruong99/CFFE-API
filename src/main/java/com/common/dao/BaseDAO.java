package com.common.dao;

import com.util.IDBHelper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

public class BaseDAO {
    protected SqlSession sqlSession;
    IDBHelper idbHelper;

    @Autowired
    public BaseDAO(IDBHelper idbHelper) {
        this.idbHelper = idbHelper;
    }

    public void openConnection() {
        this.sqlSession = idbHelper.makeConnection().openSession(TransactionIsolationLevel.READ_COMMITTED);
    }

    public void closeConnection(){
//        try {
            if(sqlSession != null){
                sqlSession.commit();
//                if(sqlSession.getConnection() != null){
//                    sqlSession.getConnection().close();
//                }
            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

    }
}