package com.screenname_example.dao.mapper;

import com.common.dao.BaseDAO;
import com.screenname_example.dto.AccountDTO;
import com.screenname_example.form.JwtRequest;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountMapper extends BaseDAO {
    public AccountMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public AccountDTO getAccountInformation(JwtRequest authenticationRequest){
        AccountDTO result = sqlSession.selectOne("AccountDAO.getAccountInformation", authenticationRequest);
        return result;
    }

    public AccountDTO login(JwtRequest request){
        AccountDTO result = sqlSession.selectOne("AccountDAO.login", request);
        return result;
    }

}
