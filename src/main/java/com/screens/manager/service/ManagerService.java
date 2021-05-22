package com.screens.manager.service;

import com.common.service.BaseService;
import com.screens.manager.dao.mapper.ManagerMapper;
import com.screens.manager.dto.ManagerDTO;
import com.screens.manager.form.*;
import com.util.EmailHelper;
import com.util.StringHelper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class ManagerService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);
    private static final int DEFAULT_FETCH_NEXT = 15;
    private static final boolean IS_DESCENDING = false;
    private static final String NUMBER_REGEXP = "[0-9]*";
    private static final String TIME_ZONE_VIETNAMESE = "+07:00";
    private static final int PASSWORD_LENGTH = 6;
    private static final int MANAGER_ROLE = 2;
    private static final int PENDING_STATUS = 3;

    @Autowired
    private ManagerMapper managerMapper;

    public ResponseManagerListForm getManagerList(RequestManagerListForm requestForm){
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestManagerListFormToMangerDTO(requestForm, managerDTO);
        ResponseManagerListForm responseForm = null;
        try {
            responseForm = managerMapper.getManagers(managerDTO);
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
        }
        return responseForm;
    }

    public ResponseManagerDetailForm getManagerDetail(RequestManagerDetailForm requestForm){
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestManagerDetailFormToManagerDTO(requestForm, managerDTO);
        ResponseManagerDetailForm responseForm = null;
        try {
            responseForm = managerMapper.getManagerDetail(managerDTO);
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
        }
        return responseForm;
    }

    public ResponseCreateManagerForm createManger(RequestCreateManagerForm requestForm){
        ResponseCreateManagerForm responseSupporter = new ResponseCreateManagerForm();
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestCreateManagerFormToManagerDTO(requestForm, managerDTO);
        try {
            if(managerMapper.createManager(managerDTO)){
                responseSupporter.setUserName(managerDTO.getUserName());

                String msgContent = "Username: " + managerDTO.getUserName() +
                                    "\nPassword: " + managerDTO.getPassword();
                if(!EmailHelper.sendEmail(managerDTO.getEmail(), msgContent)){
//                    return false;
                }
            }
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
            responseSupporter.setError(true);
            responseSupporter.setErrorCodes(sqlException(e.getMessage()));
        }catch (MessagingException e){
            logger.error("Send email at ManagerService: " + e.getMessage());
        }
        return responseSupporter;
    }

    public void convertRequestCreateManagerFormToManagerDTO(RequestCreateManagerForm requestForm, ManagerDTO managerDTO){
        managerDTO.setFullName(requestForm.getFullName());
        managerDTO.setUserName(generateUserNameFromFullName(requestForm.getFullName()));
        managerDTO.setPassword(StringHelper.generatePassword(PASSWORD_LENGTH));
        managerDTO.setRoleId(MANAGER_ROLE);
        managerDTO.setImageURL("xx/image");
        managerDTO.setGender(requestForm.getGender());
        managerDTO.setBirthDate(requestForm.getBirthDate());
        managerDTO.setIdentifyCard(requestForm.getIdentifyCard());
        managerDTO.setPhone(requestForm.getPhone());
        managerDTO.setEmail(requestForm.getEmail());
        managerDTO.setAddress(requestForm.getAddress());
        managerDTO.setDistrictId(requestForm.getDistrictId());
        managerDTO.setStatusId(PENDING_STATUS);
        managerDTO.setCreatedTime(TIME_ZONE_VIETNAMESE);
    }

    private String generateUserNameFromFullName(String fullName){
        String userName = StringHelper.generateUserNameFromFullName(fullName);
        ManagerDTO requestDAO = new ManagerDTO();
        convertUserNameToMangerDTO(userName, requestDAO);

        int responseResult = managerMapper.countRecordLikeUserName(requestDAO);
        if(responseResult > 0){
            userName = userName.concat(String.valueOf(responseResult + 1).trim());
        }

        return userName;
    }

    private void convertUserNameToMangerDTO(String userName, ManagerDTO managerDTO){
        managerDTO.setUserName(userName);
        managerDTO.setUserNameRegexp(userName + NUMBER_REGEXP);
    }


    private void convertRequestManagerDetailFormToManagerDTO(RequestManagerDetailForm requestForm, ManagerDTO managerDTO){
        managerDTO.setUserName(requestForm.getUserName());
    }

    private void convertRequestManagerListFormToMangerDTO(RequestManagerListForm requestForm, ManagerDTO managerDTO){
        managerDTO.setSearchValue(requestForm.getSearchValue());
        managerDTO.setSearchField(requestForm.getSearchField().toLowerCase().trim());
        managerDTO.setDesc(IS_DESCENDING);

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
            status = "INACTIVE";
        }else if(requestForm.getStatus() == 3){
            status = "PENDING";
        }
        managerDTO.setStatus(status);
    }
}
