package com.screens.manager.service;

import com.authentication.dto.AuthorDTO;
import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.file.listener.events.EventPublisher;
import com.screens.manager.dao.ManagerDAO;
import com.screens.manager.dto.ManagerDTO;
import com.screens.manager.form.*;
import com.util.EmailHelper;
import com.util.MessageConstant;
import com.util.StringHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

import static com.util.MessageConstant.MSG_124;

@Service
public class ManagerService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(ManagerService.class);

    @Autowired
    EventPublisher eventPublisher;

    @Autowired
    private ManagerDAO managerDAO;

    public ResponseManagerListForm getManagerList(RequestManagerListForm requestForm){
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestManagerListFormToMangerDTO(requestForm, managerDTO);
        ResponseManagerListForm responseForm = null;
        try {
            responseForm = managerDAO.getManagers(managerDTO);
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
        }
        return responseForm;
    }

    public ResponseManagerDetailForm getManagerDetail(RequestManagerDetailForm requestForm, AuthorDTO authorDTO){
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestManagerDetailFormToManagerDTO(requestForm, managerDTO, authorDTO);
        ResponseManagerDetailForm responseForm = null;
        try {
            responseForm = managerDAO.getManagerDetail(managerDTO);
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
        }
//        eventPublisher.publishEvent("Upload file");
        return responseForm;
    }

    public ResponseCommonForm createManger(RequestCreateManagerForm requestForm){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestCreateManagerFormToManagerDTO(requestForm, managerDTO);
        try {
            if(managerDAO.createManager(managerDTO)){
                String msgContent = "Username: " + managerDTO.getUserName() +
                                    "\nPassword: " + managerDTO.getPassword();
                if(!EmailHelper.sendEmail(managerDTO.getEmail(), msgContent)){
                    ArrayList<String> errorCodes = new ArrayList<>();
                    errorCodes.add(MSG_124);
                    responseForm.setErrorCodes(errorCodes);
                }
            }
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
            responseForm.setErrorCodes(catchSqlException(e.getMessage()));
        }catch (IOException e) {
            logger.error("Error at ManagerService: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error at ManagerService: " + e.getMessage());
        }
        return responseForm;
    }

    public ResponseCommonForm updateManagerInformation(RequestUpdateManagerForm requestForm, AuthorDTO authorDTO){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestUpdateManagerFormToManagerDTO(requestForm, managerDTO, authorDTO);
        try{
            if(!managerDAO.updateManagerInformation(managerDTO)){
                addErrorMessage(responseForm,MessageConstant.MSG_063);
            }
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
            responseForm.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return responseForm;
    }

    public ResponseCommonForm resetManagerPassword(RequestResetPasswordForm requestForm, AuthorDTO authorDTO){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestResetPasswordToManagerDTO(requestForm, managerDTO, authorDTO);
        try {
            if(!managerDAO.resetPassword(managerDTO)){
                addErrorMessage(responseForm,MessageConstant.MSG_063);
            }else{
                String email = "";
                String msgContent = "Username: " + managerDTO.getUserName() +
                        "\nPassword: " + managerDTO.getPassword();
                if(!StringHelper.isNullOrEmpty(requestForm.getEmail())){
                    email = requestForm.getEmail();
                }else{
                    email = managerDAO.getEmailByUserName(managerDTO);
                }
                if(!EmailHelper.sendEmail(email, msgContent)){
                    ArrayList<String> errorCodes = new ArrayList<>();
                    errorCodes.add(MSG_124);
                    responseForm.setErrorCodes(errorCodes);
                }
            }
        }catch (PersistenceException e){
            logger.error("Error at ManagerService: " + e.getMessage());
        } catch (IOException e) {
            logger.error("Error at ManagerService: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error at ManagerService: " + e.getMessage());
        }
        return responseForm;
    }

    public ResponseCommonForm updateManagerStatus(RequestUpdateManagerStatusForm requestForm){
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestUpdateStatusFormToManagerDTO(requestForm, managerDTO);
        ResponseCommonForm responseForm = checkUpdateStatusBusiness(managerDTO);
        if(responseForm.getErrorCodes() == null){
            try {
                managerDAO.updateManagerStatus(managerDTO);
            }catch (PersistenceException e){
                logger.error("Error at ManagerService: " + e.getMessage());
            }
        }
        return responseForm;
    }


    /*
     * //ManagerDTO managerDTO is converted from requestForm
     * */
    private ResponseCommonForm checkUpdateStatusBusiness(ManagerDTO managerDTO){
        ResponseCommonForm responseCommonForm = new ResponseCommonForm();
        ManagerDTO resultDAO = managerDAO.getStatusIdAndStoreIdByUserName(managerDTO);

        //Not found manager
        if (resultDAO == null){
            addErrorMessage(responseCommonForm,MessageConstant.MSG_063);
        }
        //manager is activating, can not change status
        else if(resultDAO.getStatusId() == ACTIVE_STATUS){
            addErrorMessage(responseCommonForm,MessageConstant.MSG_076);
        }
        //request inactive, status is pending, check reason inactive is not empty
        else if(managerDTO.getStatusId() == INACTIVE_STATUS
//                && resultDAO.getStatusId() == PENDING_STATUS
                && StringHelper.isNullOrEmpty(managerDTO.getReasonInactive())){
            addErrorMessage(responseCommonForm,MessageConstant.MSG_066);
        }
        //other wise is true
        return responseCommonForm;
    }

    public ResponseCommonForm changePassword(RequestChangePasswordForm requestForm, AuthorDTO authorDTO){
        ManagerDTO managerDTO = new ManagerDTO();
        convertRequestChangePasswordFormToManagerDTO(requestForm, managerDTO, authorDTO);
        ResponseCommonForm responseForm = checkChangePasswordBusiness(requestForm, managerDTO);
        if(responseForm.getErrorCodes() == null){
            try {
                    managerDAO.updatePassword(managerDTO);
            }catch (PersistenceException e){
                logger.error("Error at ManagerService: " + e.getMessage());
            }
        }
        return responseForm;
    }

    private void convertRequestCreateManagerFormToManagerDTO(RequestCreateManagerForm requestForm, ManagerDTO managerDTO){
        managerDTO.setFullName(requestForm.getFullName());
        managerDTO.setUserName(generateUserNameFromFullName(requestForm.getFullName()));
        managerDTO.setPassword(StringHelper.generatePassword(PASSWORD_LENGTH));
        managerDTO.setRoleId(MANAGER_ROLE);
        if (StringUtils.isNotEmpty(requestForm.getImageURL())) {
            managerDTO.setImageURL(requestForm.getImageURL());
        } else {
            managerDTO.setImageURL(DEFAULT_IMAGE);
        }
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

        int responseResult = managerDAO.countRecordLikeUserName(requestDAO);
        if(responseResult > 0){
            userName = userName.concat(String.valueOf(responseResult).trim());
        }

        return userName;
    }

    private void convertUserNameToMangerDTO(String userName, ManagerDTO managerDTO){
        managerDTO.setUserName(userName);
        managerDTO.setUserNameRegexp(userName + NUMBER_REGEXP);
    }

    private void convertRequestManagerDetailFormToManagerDTO(RequestManagerDetailForm requestForm, ManagerDTO managerDTO, AuthorDTO authorDTO){
        managerDTO.setUserName(requestForm.getUserName());
        if(authorDTO != null){
            managerDTO.setUserName(authorDTO.getUserName());
        }
    }

    private void convertRequestManagerListFormToMangerDTO(RequestManagerListForm requestForm, ManagerDTO managerDTO){
        managerDTO.setSearchValue(requestForm.getSearchValue());
        managerDTO.setSearchField(requestForm.getSearchField().toLowerCase().trim());
        managerDTO.setDesc(IS_DESCENDING);

        if(requestForm.getPageNum() > 0){
            managerDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }

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

    private void convertRequestUpdateManagerFormToManagerDTO(RequestUpdateManagerForm requestForm, ManagerDTO managerDTO, AuthorDTO authorDTO){
        managerDTO.setUserName(requestForm.getUserName());
        if(authorDTO != null){
            managerDTO.setUserName(authorDTO.getUserName());
        }

        managerDTO.setFullName(requestForm.getFullName());
        if (StringUtils.isNotEmpty(requestForm.getImageURL())) {
            managerDTO.setImageURL(requestForm.getImageURL());
        }
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

    private void convertRequestResetPasswordToManagerDTO(RequestResetPasswordForm requestForm, ManagerDTO managerDTO, AuthorDTO authorDTO){
        managerDTO.setUserName(requestForm.getUserName());
        if(authorDTO != null){
            managerDTO.setUserName(authorDTO.getUserName());
        }
        managerDTO.setPassword(StringHelper.generatePassword(PASSWORD_LENGTH));
    }

    private void convertRequestUpdateStatusFormToManagerDTO(RequestUpdateManagerStatusForm requestForm, ManagerDTO managerDTO){
        managerDTO.setUserName(requestForm.getUserName());
        managerDTO.setStatusId(requestForm.getStatusId());
        if(!StringHelper.isNullOrEmpty(requestForm.getReasonInactive())){
            managerDTO.setReasonInactive(requestForm.getReasonInactive());
        }
        managerDTO.setUpdatedTime(TIME_ZONE_VIETNAMESE);
    }

    private void convertRequestChangePasswordFormToManagerDTO(RequestChangePasswordForm requestForm, ManagerDTO managerDTO, AuthorDTO authorDTO){
        managerDTO.setUserName(requestForm.getUserName());
        if(authorDTO != null){
            managerDTO.setUserName(authorDTO.getUserName());
        }
        managerDTO.setPassword(requestForm.getOldPassword());
        managerDTO.setNewPassword(requestForm.getNewPassword());
    }

    private ResponseCommonForm checkChangePasswordBusiness(RequestChangePasswordForm requestForm, ManagerDTO managerDTO){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        if(requestForm.getNewPassword().equals(requestForm.getRetypePassword())){
            try{
                if(!managerDAO.checkUserNameAndPassword(managerDTO)){
                    addErrorMessage(responseForm, MessageConstant.MSG_068);
                }
            }catch (PersistenceException e){
                logger.error("Error at ManagerService: " + e.getMessage());
            }
        }else{
            addErrorMessage(responseForm,MessageConstant.MSG_076);
        }

        return responseForm;
    }
}