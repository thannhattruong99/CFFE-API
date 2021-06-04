package com.screenname_example.service;

import com.common.service.BaseService;
import com.screenname_example.dao.mapper.AccountMapper;
import com.screenname_example.dto.AccountDTO;
import com.screenname_example.form.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.ArrayList;

@Service
public class AccountService extends BaseService {
    @Autowired
    private AccountMapper accountMapper;

    public UserDetails getAccountDTOInformation(JwtRequest authenticationRequest){
        AccountDTO result = accountMapper.getAccountInformation(authenticationRequest);
        if(result == null){
            return null;
        }
        return new org.springframework.security.core.userdetails.User(result.getUserName(),
                result.getPassword(), new ArrayList<>());
    }

    public AccountDTO checkLogin(JwtRequest request){
        try{
            return accountMapper.login(request);
        }catch (PersistenceException e){
            System.out.println("VUI VE KHONG QUAO: " + e.getMessage());
        }
        return null;
    }
}
