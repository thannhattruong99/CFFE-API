package com.screens.shelf.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.shelf.dao.mapper.ShelfMapper;
import com.screens.shelf.dto.ShelfDTO;
import com.screens.shelf.dto.StackDTO;
import com.screens.shelf.form.*;
import com.util.StringHelper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShelfService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(ShelfService.class);
    private static final String MSG_012 = "MSG-012";
    @Autowired
    private ShelfMapper shelfMapper;

    public ResponseShelfListForm getShelfList(RequestShelfListForm requestForm){
        ResponseShelfListForm responseForm = null;
        ShelfDTO shelfDTO = new ShelfDTO();
        convertRequestShelfListToShelfDTO(requestForm, shelfDTO);
        try{
            responseForm = shelfMapper.getShelfList(shelfDTO);
        }catch (PersistenceException e){
            logger.error("Error at ShelfService: " + e.getMessage());
        }

        return responseForm;
    }

    public ResponseShelfDetailForm getShelfDetail(RequestShelfDetailForm requestForm){
        ResponseShelfDetailForm responseForm = null;
        ShelfDTO shelfDTO = new ShelfDTO();
        convertRequestShelfDetailFormToShelfDTO(requestForm, shelfDTO);

        try {
            responseForm = shelfMapper.getShelfDetail(shelfDTO);
        }catch (PersistenceException e){
            logger.error("Error at ShelfService: " + e.getMessage());
        }

        return responseForm;
    }

    public ResponseCommonForm createShelf(RequestCreateShelfForm requestForm){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        ShelfDTO shelfDTO = new ShelfDTO();
        convertRequestCreateShelfFormToShelfDTO(requestForm, shelfDTO);
        try {
            if(!shelfMapper.createShelf(shelfDTO)){
                List<String> errorCodes = new ArrayList<>();
                errorCodes.add(MSG_012);
                responseForm.setErrorCodes(errorCodes);
            }
        }catch (PersistenceException e){
            logger.error("Error at ShelfService: " + e.getMessage());
            responseForm.setErrorCodes(catchSqlException(e.getMessage()));
        }

        return responseForm;
    }

    public ResponseCommonForm updateShelf(RequestUpdateShelfForm requestForm){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        ShelfDTO shelfDTO = new ShelfDTO();
        convertRequestUpdateShelfFormToShelfDTO(requestForm, shelfDTO);

        try{
            if(!shelfMapper.updateShelf(shelfDTO)){
                ArrayList<String> errorCodes = new ArrayList<>();
                errorCodes.add(MSG_012);
                responseForm.setErrorCodes(errorCodes);
            }
        }catch (PersistenceException e){
            logger.error("Error at ShelfService: " + e.getMessage());
            responseForm.setErrorCodes(catchSqlException(e.getMessage()));
        }

        return responseForm;
    }

    public ResponseCommonForm updateShelfStatus(RequestUpdateShelfStatusForm requestForm){
        ShelfDTO shelfDTO = new ShelfDTO();
        convertRequestUpdateStatusFormToShelfDTO(requestForm, shelfDTO);
        ResponseCommonForm responseForm = checkUpdateStatusBusiness(shelfDTO);
        if(responseForm.getErrorCodes() == null){
            try {
                shelfMapper.updateShelfStatus(shelfDTO);
            }catch (PersistenceException e){
                logger.error("Error at ManagerService: " + e.getMessage());
            }
        }

        return responseForm;
    }

    private void convertRequestShelfListToShelfDTO(RequestShelfListForm requestForm, ShelfDTO shelfDTO){
        shelfDTO.setUserName(requestForm.getUserName());
        shelfDTO.setStoreId(requestForm.getStoreId());
        shelfDTO.setShelfName(requestForm.getShelfName());
        shelfDTO.setStatusId(requestForm.getStatusId());

        shelfDTO.setOffSet(DEFAULT_OFF_SET);
        if(requestForm.getPageNum() > 0){
            shelfDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }

        shelfDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        if(requestForm.getFetchNext() > 0){
            shelfDTO.setFetchNext(requestForm.getFetchNext());
        }
    }

    private void convertRequestShelfDetailFormToShelfDTO(RequestShelfDetailForm requestForm, ShelfDTO shelfDTO){
        shelfDTO.setShelfId(requestForm.getShelfId());
    }

    private void convertRequestCreateShelfFormToShelfDTO(RequestCreateShelfForm requestForm, ShelfDTO shelfDTO){
        shelfDTO.setStoreId(requestForm.getStoreId());
        shelfDTO.setShelfName(requestForm.getShelfName());
        shelfDTO.setDescription(requestForm.getDescription());
        shelfDTO.setNumberOfStack(requestForm.getNumberOfStack());
        shelfDTO.setStatusId(PENDING_STATUS);
        shelfDTO.setCreatedTime(TIME_ZONE_VIETNAMESE);

        List<StackDTO> stackDTOS = new ArrayList<>();
        for(int i = 0; i < requestForm.getNumberOfStack(); i++){
            StackDTO stackDTO = new StackDTO(i + 1, TIME_ZONE_VIETNAMESE, "", PENDING_STATUS);
            stackDTOS.add(stackDTO);
        }

        shelfDTO.setStacks(stackDTOS);
    }

    private void convertRequestUpdateShelfFormToShelfDTO(RequestUpdateShelfForm requestForm, ShelfDTO shelfDTO){
        shelfDTO.setShelfId(requestForm.getShelfId());
        shelfDTO.setShelfName(requestForm.getShelfName());
        shelfDTO.setDescription(requestForm.getDescription());
        shelfDTO.setUpdatedTime(TIME_ZONE_VIETNAMESE);
    }

    private void convertRequestUpdateStatusFormToShelfDTO(RequestUpdateShelfStatusForm requestForm, ShelfDTO shelfDTO){
        shelfDTO.setShelfId(requestForm.getShelfId());
        shelfDTO.setStatusId(requestForm.getStatusId());
        if(!StringHelper.isNullOrEmpty(requestForm.getReasonInactive())){
            shelfDTO.setReasonInactive(requestForm.getReasonInactive());
        }
        shelfDTO.setUpdatedTime(TIME_ZONE_VIETNAMESE);
    }

    /*
    * //ShelfDTO shelfDTO is converted from requestForm
    * */
    private ResponseCommonForm checkUpdateStatusBusiness(ShelfDTO shelfDTO){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        ShelfDTO resultDAO = shelfMapper.getStatusId(shelfDTO);

        // Not found shelf
        if(resultDAO == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_012);
            responseForm.setErrorCodes(errorCodes);
        }
        //shelf is activating, can not change status
        else if(resultDAO.getStatusId() == ACTIVE_STATUS){
            ArrayList<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_076);
            responseForm.setErrorCodes(errorCodes);
        }
        //request inactive, status is pending, check reason inactive is not empty
        else if (shelfDTO.getStatusId() == INACTIVE_STATUS
                && resultDAO.getStatusId() == PENDING_STATUS){
            if( resultDAO.getTotalOfRecord() > 0 || StringHelper.isNullOrEmpty(shelfDTO.getReasonInactive())){
                ArrayList<String> errorCodes = new ArrayList<>();
                errorCodes.add(MSG_066);
                responseForm.setErrorCodes(errorCodes);
            }
        }
        //other wise is true
        return responseForm;
    }

}
