package com.screens.manager.service;

import com.screens.manager.dao.mapper.ManagerMapper;
import com.screens.manager.dto.ManagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    private ManagerDTO convertRequestManagerListFormToMangerDTO(){
        ManagerDTO managerDTO = new ManagerDTO();
        return managerDTO;
    }

}
