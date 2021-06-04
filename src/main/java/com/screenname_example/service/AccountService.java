package com.screenname_example.service;

import com.common.config.JwtTokenHelper;
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
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService extends BaseService {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

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

    public AccountDTO loadUserByUsername(String userName){
        AccountDTO result = accountMapper.getAccountInformation(userName);
        if(result != null){
            return result;
        }else {
            throw new UsernameNotFoundException("User not found with username: " + userName);
        }

    }

    public String checkLogin(JwtRequest request){
        try{
            AccountDTO resultDAO = accountMapper.login(request);
            if(resultDAO != null){
                Map<String, Object> claims = new HashMap<>();
                claims.put("UserId", resultDAO.getUserId());
                claims.put("UserName", resultDAO.getUserName());
                claims.put("FullName", resultDAO.getFullName());
                claims.put("StoreId", resultDAO.getStoreId());
                claims.put("RoleId", resultDAO.getRoleId());
                return jwtTokenHelper.generateToken(claims, request.getUsername());
            }

        }catch (PersistenceException e){
            System.out.println("VUI VE KHONG QUAO: " + e.getMessage());
        }
        return null;
    }
}
