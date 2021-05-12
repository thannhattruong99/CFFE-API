package com.screenname.dao.mapper;

import com.common.dao.BaseDAO;
import com.screenname.dto.CreateAccountDTO;
import com.screenname.dto.GetAccountDTO;
import com.screenname.form.ResponseGetAccountForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountMapper extends BaseDAO {
    public AccountMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseGetAccountForm getAccount(GetAccountDTO accountDTO){
        ResponseGetAccountForm accountForm = sqlSession.selectOne("com.screenname.dao.sql.AccountDAO.getAccount", accountDTO);
        return accountForm;
    }

    public boolean createAnAccount(CreateAccountDTO createAccountDTO){
        if(sqlSession.insert("com.screenname.dao.sql.AccountDAO.insert", createAccountDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }
}