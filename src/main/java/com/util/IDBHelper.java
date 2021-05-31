package com.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;

public interface IDBHelper {
    SqlSessionFactory makeConnection();
//    SqlSession openSession();
}
