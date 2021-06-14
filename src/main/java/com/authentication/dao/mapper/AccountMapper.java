package com.authentication.dao.mapper;

import com.authentication.dto.AccountDTO;
import com.authentication.form.RequestLoginForm;
import com.authentication.form.ResponseLoginForm;
import com.common.dao.BaseDAO;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountMapper extends BaseDAO {
    public AccountMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public AccountDTO getAccountInformation(String userName){
        AccountDTO result = sqlSession.selectOne("AccountDAO.getAccountInformation", userName);
        return result;
    }

    public ResponseLoginForm login(RequestLoginForm request){
        ResponseLoginForm result = sqlSession.selectOne("AccountDAO.login", request);
        return result;
    }

}
