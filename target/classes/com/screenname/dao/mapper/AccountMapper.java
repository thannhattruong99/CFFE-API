package com.screenname.dao.mapper;

import com.common.config.BaseDAO;
import com.screenname.dto.Account;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountMapper extends BaseDAO {
    public AccountMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public List<Account> getAll(){
        List<Account> listAccounts = sqlSession.selectList("com.screenname.dao.sql.AccountDAO.selectAll");
        return listAccounts;
    }

    public boolean createAccounts(List<Account> accounts){
        sqlSession.commit(false);
        int result = sqlSession.insert("com.screenname.dao.sql.AccountDAO.xxx", accounts);
        if(result == 2){
            sqlSession.commit();
            return true;
        }
        return false;
    }
}