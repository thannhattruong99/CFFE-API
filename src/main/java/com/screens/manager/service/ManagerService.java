package com.screens.manager.service;

import com.screens.manager.dao.mapper.ManagerMapper;
import com.screens.manager.dto.ManagerDTO;
import com.screens.manager.form.RequestManagerListForm;
import com.screens.manager.form.ResponseManagerListForm;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);
    private static final int DEFAULT_FETCH_NEXT = 15;
    @Autowired
    private ManagerMapper managerMapper;

    public ResponseManagerListForm getManagerList(RequestManagerListForm requestForm){
        System.out.println("Toi day roi ne");
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestManagerListFormToMangerDTO(requestForm, managerDTO);
        ResponseManagerListForm response = null;
        try {
            response = managerMapper.getManagers(managerDTO);
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
        }
        return response;
    }


    private void convertRequestManagerListFormToMangerDTO(RequestManagerListForm requestForm, ManagerDTO managerDTO){
        managerDTO.setSearchValue(requestForm.getSearchValue().trim());
        managerDTO.setSearchField(requestForm.getSearchField().toLowerCase().trim());
        managerDTO.setSortField(requestForm.getSortField().toLowerCase().trim());
        managerDTO.setDesc(requestForm.isDescending());

        int offSet = 0;
        if(requestForm.getPageNum() > 0){
            offSet = (requestForm.getPageNum() - 1) * requestForm.getFetchNext();
        }
        managerDTO.setOffSet(offSet);

        managerDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        if(requestForm.getFetchNext() > 0){
            managerDTO.setFetchNext(requestForm.getFetchNext());
        }

        String status = null;
        if(requestForm.getStatus() == 1){
            status = "ACTIVE";
        }else if(requestForm.getStatus() == 2){
            status = "PENDING";
        }
        managerDTO.setStatus(status);
    }

}
