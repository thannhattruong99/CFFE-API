package com.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;

@Component
public class DBHelper implements IDBHelper{
    private static final Logger logger = LoggerFactory.getLogger(DBHelper.class);
    @Override
    public SqlSessionFactory makeConnection() {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        } catch (IOException e) {
            logger.error("Error at DBHelper: " + e.getMessage());
        }
        return new SqlSessionFactoryBuilder().build(reader);
    }

}
