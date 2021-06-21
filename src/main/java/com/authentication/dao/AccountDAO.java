package com.authentication.dao;

import com.authentication.dto.AccountDTO;
import com.authentication.form.RequestLoginForm;
import com.authentication.form.ResponseLoginForm;
import com.common.dao.BaseDAO;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO extends BaseDAO {
    public AccountDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public AccountDTO getAccountInformation(String userName) {
        try {
            openConnection();
            return sqlSession.selectOne("AccountDAO.getAccountInformation", userName);
        }finally {
            closeConnection();
        }
    }



    public ResponseLoginForm login(RequestLoginForm request){
        try{
            openConnection();
            return sqlSession.selectOne("AccountDAO.login", request);
        }finally {
            closeConnection();
        }
    }

}
