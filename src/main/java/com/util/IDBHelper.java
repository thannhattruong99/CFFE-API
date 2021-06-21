package com.util;

import org.apache.ibatis.session.SqlSessionFactory;

public interface IDBHelper {
    SqlSessionFactory makeConnection();
}
