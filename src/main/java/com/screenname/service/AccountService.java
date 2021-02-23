package com.screenname.service;

import com.screenname.dto.Account;
import com.screenname.dao.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountMapper accountMapper;

    public List<Account> getStudentAll(){
        List<Account> listAccounts2 = accountMapper.getAll();
        return listAccounts2;
    }
}