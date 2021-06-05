package com.util;

import com.screens.manager.service.ManagerService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

@Service
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
