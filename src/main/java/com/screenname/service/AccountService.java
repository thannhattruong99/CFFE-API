package com.screenname.service;

import com.common.config.CasptoneAPIApplication;
import com.screenname.dto.AccountDTO;
import com.screenname.dao.mapper.AccountMapper;
import com.screenname.form.AccountFormValidator;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    AccountMapper accountMapper;

    public List<AccountDTO> getStudentAll(){
        return accountMapper.getAll();
    }

    public AccountDTO createAnAccount(AccountFormValidator accountForm){
        AccountDTO accountDTO = convertAccountFormToAccountDTO(accountForm);

        try {
            if(!accountMapper.createAnAccount(accountDTO)){
                accountDTO.setPassword(null);
                return accountDTO;
            }
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }

        return null;
    }

    private AccountDTO convertAccountFormToAccountDTO(AccountFormValidator accountForm){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(accountForm.getEmail());
        accountDTO.setFullname(accountForm.getFullname());
        accountDTO.setPassword(accountForm.getPassword());
        accountDTO.setRole(1);
        accountDTO.setStatus(1);
        accountDTO.setCreatedDate("1000-01-01 00:00:00");
        return accountDTO;
    }

    public List<String> checkAccountBussiness(AccountFormValidator accountForm){
        List<String> errorCodes = new ArrayList<String>();
        if(!accountForm.getPassword().equals(accountForm.getConfirmPassword())){
            errorCodes.add("E002");
        }
        return errorCodes;
    }
}