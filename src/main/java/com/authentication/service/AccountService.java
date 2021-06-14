package com.authentication.service;

import com.authentication.dao.mapper.AccountMapper;
import com.authentication.dto.AccountDTO;
import com.authentication.form.RequestLoginForm;
import com.authentication.form.ResponseLoginForm;
import com.common.config.JwtTokenHelper;
import com.common.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private AccountMapper accountMapper;

    public AccountDTO loadUserByUsername(String userName){
        AccountDTO result = accountMapper.getAccountInformation(userName);
        if(result != null){
            return result;
        }else {
            throw new UsernameNotFoundException("User not found with username: " + userName);
        }

    }

    public ResponseLoginForm checkLogin(RequestLoginForm request){
        ResponseLoginForm resultDAO = null;
        try{
            resultDAO = accountMapper.login(request);
            if(resultDAO != null){
                Map<String, Object> claims = new HashMap<>();
                claims.put("UserId", resultDAO.getUserId());
                claims.put("UserName", resultDAO.getUserName());
                claims.put("FullName", resultDAO.getFullName());
                claims.put("StoreId", resultDAO.getStoreId());
                claims.put("RoleId", resultDAO.getRoleId());
                resultDAO.setToken(jwtTokenHelper.generateToken(claims, request.getUsername()));
            }

        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
        }
        return resultDAO;
    }
}
