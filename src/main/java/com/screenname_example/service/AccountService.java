package com.screenname_example.service;

import com.screenname_example.dto.CreateAccountDTO;
import com.screenname_example.dao.mapper.AccountMapper;
import com.screenname_example.dto.GetAccountDTO;
import com.screenname_example.form.RequestCreateAccountForm;
import com.screenname_example.form.RequestGetAccountForm;
import com.screenname_example.form.ResponseCreateAccountForm;
import com.screenname_example.form.ResponseGetAccountForm;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    private static final int DEFAULT_FETCH_NEXT = 5;

    @Autowired
    AccountMapper accountMapper;

    public ResponseGetAccountForm getStudent(RequestGetAccountForm accountForm){
        ResponseGetAccountForm responseGetAccountForm = null;
        try {
            GetAccountDTO getAccountDTO = convertGetAccountFormToAccountDTO(accountForm);
            responseGetAccountForm = accountMapper.getAccount(getAccountDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseGetAccountForm;
    }

    private GetAccountDTO convertGetAccountFormToAccountDTO(RequestGetAccountForm accountForm){
        GetAccountDTO accountDTO = new GetAccountDTO();
        accountDTO.setSearchValue(accountForm.getSearchValue());
        accountDTO.setSearchField(accountForm.getSearchField());
        accountDTO.setDesc(true);

        if(accountForm.getPageNum() > 0){
            accountDTO.setOffSet((accountForm.getPageNum() - 1) * accountForm.getFetchNext());
        }

        accountDTO.setFetchNext(accountForm.getFetchNext());
        if(accountForm.getFetchNext() <= 0){
            accountDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        }
        System.out.println("accountDTO: " + accountDTO.getSearchValue());
        return accountDTO;
    }

    public ResponseCreateAccountForm createAnAccount(RequestCreateAccountForm accountForm){
        ResponseCreateAccountForm responseCreateAccountForm = null;
        CreateAccountDTO createAccountDTO = convertCreateAccountFormToAccountDTO(accountForm);

        try {
            if(accountMapper.createAnAccount(createAccountDTO)){
                responseCreateAccountForm = new ResponseCreateAccountForm();
                responseCreateAccountForm.setEmail(createAccountDTO.getEmail());
                responseCreateAccountForm.setFullname(createAccountDTO.getFullname());
                responseCreateAccountForm.setRole(createAccountDTO.getRole());
                responseCreateAccountForm.setStatus(createAccountDTO.getStatus());
            }
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }

        return responseCreateAccountForm;
    }

    private CreateAccountDTO convertCreateAccountFormToAccountDTO(RequestCreateAccountForm accountForm){
        CreateAccountDTO createAccountDTO = new CreateAccountDTO();
        createAccountDTO.setEmail(accountForm.getEmail());
        createAccountDTO.setFullname(accountForm.getFullname());
        createAccountDTO.setPassword(accountForm.getPassword());
        createAccountDTO.setRole(1);
        createAccountDTO.setStatus(1);
        createAccountDTO.setCreatedDate("1000-01-01 00:00:00");
        return createAccountDTO;
    }

    public List<String> checkAccountBussiness(RequestCreateAccountForm accountForm){
        List<String> errorCodes = new ArrayList<String>();
        if(!accountForm.getPassword().equals(accountForm.getConfirmPassword())){
            errorCodes.add("E002");
        }
        return errorCodes;
    }
}