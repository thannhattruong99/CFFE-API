package com.screens.manager.service;

import com.common.form.ResponseCommonForm;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);

    @Autowired
    private ManagerMapper managerMapper;

    public ResponseManagerListForm getManagerList(RequestManagerListForm requestForm){
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestManagerListFormToMangerDTO(requestForm, managerDTO);
        ResponseManagerListForm responseForm = new ResponseManagerListForm();
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

    public ResponseCommonForm createManger(RequestCreateManagerForm requestForm){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestCreateManagerFormToManagerDTO(requestForm, managerDTO);
        try {
            if(managerMapper.createManager(managerDTO)){

                String msgContent = "Username: " + managerDTO.getUserName() +
                                    "\nPassword: " + managerDTO.getPassword();
                if(!EmailHelper.sendEmail(managerDTO.getEmail(), msgContent)){
//                    return false;
                }
            }
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
            responseForm.setErrorCodes(catchSqlException(e.getMessage()));
        }catch (MessagingException e){
            logger.error("Send email at ManagerService: " + e.getMessage());
        }
        return responseForm;
    }

    public ResponseCommonForm updateManagerInformation(RequestUpdateManagerForm requestForm){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestUpdateManagerFormToManagerDTO(requestForm, managerDTO);
        try{
            if(!managerMapper.updateManagerInformation(managerDTO)){
                List<String> errorCodes = new ArrayList<>();
                errorCodes.add(MSG_063);
                responseForm.setErrorCodes(errorCodes);
            }
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
            responseForm.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return responseForm;
    }

    public ResponseCommonForm resetManagerPassword(RequestResetPasswordForm requestForm){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestResetPasswordToManagerDTO(requestForm, managerDTO);
        try {

            if(!managerMapper.resetPassword(managerDTO)){
                List<String> errorCodes = new ArrayList<>();
                errorCodes.add(MSG_063);
                responseForm.setErrorCodes(errorCodes);
            }else{
                String email = "";
                String msgContent = "Username: " + managerDTO.getUserName() +
                        "\nPassword: " + managerDTO.getPassword();
                if(!StringHelper.isNullOrEmpty(requestForm.getEmail())){
                    email = requestForm.getEmail();
                }else{
                    email = managerMapper.getEmailByUserName(managerDTO);
                }

                if(!EmailHelper.sendEmail(email, msgContent)){
//                    return false;
                }

            }
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
            responseForm.setErrorCodes(catchSqlException(e.getMessage()));
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return responseForm;
    }

    public ResponseCommonForm updateManagerStatus(RequestUpdateStatusForm requestForm){
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestUpdateStatusFormToManagerDTO(requestForm, managerDTO);
        ResponseCommonForm responseForm = checkUpdateStatusBusiness(managerDTO);

        if(responseForm.getErrorCodes() == null){
            try {
                managerMapper.updateManagerStatus(managerDTO);
            }catch (PersistenceException e){
                logger.error("Error at ManagerService: " + e.getMessage());
            }
        }
        return responseForm;
    }

    private ManagerDTO getStoreIdAndStatusIdByUserName(ManagerDTO managerDTO){
        return managerMapper.getStatusIdAndStoreIdByUserName(managerDTO);
    }

    private ResponseCommonForm checkUpdateStatusBusiness(ManagerDTO managerDTO){
        ResponseCommonForm responseCommonForm = new ResponseCommonForm();
        ManagerDTO resultDAO = managerMapper.getStatusIdAndStoreIdByUserName(managerDTO);
        if(managerDTO.getStatusId() == INACTIVE_STATUS && resultDAO.getStatusId() == PENDING_STATUS){
            if(StringHelper.isNullOrEmpty(managerDTO.getReasonInactive())
                    || !StringHelper.isNullOrEmpty(resultDAO.getStoreId())){
                ArrayList<String> errorCodes = new ArrayList<>();
                errorCodes.add(MSG_066);
                responseCommonForm.setErrorCodes(errorCodes);
            }
        }
        return responseCommonForm;
    }


//    private ResponseCommonForm checkUpdateStatusBusiness(RequestUpdateStatusForm requestForm, ManagerDTO managerDTO){
//        ResponseCommonForm responseCommon = new ResponseCommonForm();
//        ManagerDTO resultDAO = getStoreIdAndStatusIdByUserName(managerDTO);
//        checkInactiveStatusBusiness(requestForm, resultDAO);
//
//        if()
//
//
//        return responseCommon;
//    }

    private void convertRequestCreateManagerFormToManagerDTO(RequestCreateManagerForm requestForm, ManagerDTO managerDTO){
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
            userName = userName.concat(String.valueOf(responseResult).trim());
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
        if(requestForm.getStatusId() == ACTIVE_STATUS){
            status = ACTIVE_STATUS_STR;
        }else if(requestForm.getStatusId() == INACTIVE_STATUS){
            status = INACTIVE_STATUS_STR;
        }else if(requestForm.getStatusId() == PENDING_STATUS){
            status = PENDING_STATUS_STR;
        }
        managerDTO.setStatus(status);
    }

    private void convertRequestUpdateManagerFormToManagerDTO(RequestUpdateManagerForm requestForm, ManagerDTO managerDTO){
        managerDTO.setUserName(requestForm.getUserName());
        managerDTO.setFullName(requestForm.getFullName());
        managerDTO.setImageURL("xx/image");
        managerDTO.setGender(requestForm.getGender());
        managerDTO.setBirthDate(requestForm.getBirthDate());
        managerDTO.setIdentifyCard(requestForm.getIdentifyCard());
        managerDTO.setPhone(requestForm.getPhone());
        managerDTO.setEmail(requestForm.getEmail());
        managerDTO.setAddress(requestForm.getAddress());
        managerDTO.setDistrictId(requestForm.getDistrictId());
        managerDTO.setStatusId(PENDING_STATUS);
        managerDTO.setUpdatedTime(TIME_ZONE_VIETNAMESE);
    }

    private void convertRequestResetPasswordToManagerDTO(RequestResetPasswordForm requestForm, ManagerDTO managerDTO){
        managerDTO.setUserName(requestForm.getUserName());
        managerDTO.setPassword(StringHelper.generatePassword(PASSWORD_LENGTH));
    }

    private void convertRequestUpdateStatusFormToManagerDTO(RequestUpdateStatusForm requestForm, ManagerDTO managerDTO){
        managerDTO.setUserName(requestForm.getUserName());
        managerDTO.setStatusId(requestForm.getStatusId());
        if(!StringHelper.isNullOrEmpty(requestForm.getReasonInactive())){
            managerDTO.setReasonInactive(requestForm.getReasonInactive());
        }
    }

}
