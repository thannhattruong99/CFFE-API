package com.common.dao.mapper;

import com.common.dao.BaseDAO;
import com.common.dto.Account;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountMapper extends BaseDAO {
    public AccountMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public List<Account> getAll(){
        List<Account> listAccounts = sqlSession.selectList("com.common.dao.sql.AccountDAO.selectAll");
        return listAccounts;
    }
}