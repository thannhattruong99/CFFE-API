package com.screenname_example.service;

import com.screenname_example.dao.mapper.AccountMapper;
import com.screenname_example.dto.AccountDTO;
import com.screenname_example.form.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountService {
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
}
