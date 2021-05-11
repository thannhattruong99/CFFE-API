package com.screenname.dao.mapper;

import com.common.config.BaseDAO;
import com.screenname.dto.AccountDTO;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Repository
public class AccountMapper extends BaseDAO {
    public AccountMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public List<AccountDTO> getAll(){
        List<AccountDTO> listAccountDTOS = sqlSession.selectList("com.screenname.dao.sql.AccountDAO.selectAll");
        return listAccountDTOS;
    }

    public boolean createAnAccount(AccountDTO accountDTO){
        try{
            if(sqlSession.insert("com.screenname.dao.sql.AccountDAO.insert", accountDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
        } finally {

        }

        return true;
    }
}