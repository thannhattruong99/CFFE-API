package com.screens.shelf.service;

import com.authentication.dto.AuthorDTO;
import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.file.listener.events.EventPublisher;
import com.screens.shelf.dao.ShelfDAO;
import com.screens.shelf.dto.ShelfDTO;
import com.screens.shelf.dto.StackDTO;
import com.screens.shelf.form.*;
import com.util.MessageConstant;
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
    private static final int MAX_CAMERA_ON_SHELF = 2;

    @Autowired
    private ShelfDAO shelfDAO;
    @Autowired
    EventPublisher eventPublisher;

    public ResponseShelfListForm getShelfList(RequestShelfListForm requestForm, AuthorDTO authorDTO){
        ResponseShelfListForm responseForm = null;
        ShelfDTO shelfDTO = new ShelfDTO();
        convertRequestShelfListToShelfDTO(requestForm, shelfDTO, authorDTO);
        try{
            responseForm = shelfDAO.getShelfList(shelfDTO);
        }catch (PersistenceException e){
            logger.error("Error at ShelfService: " + e.getMessage());
        }

        return responseForm;
    }

    public ResponseShelvesByStoreId getShelveByStoreId(RequestShelvesByStoreId requestForm){
        ResponseShelvesByStoreId responseForm = null;
        ShelfDTO shelfDTO = new ShelfDTO();
        shelfDTO.setStoreId(requestForm.getStoreId());
        try{
            responseForm = shelfDAO.getShelveByStoreId(shelfDTO);
        }catch (PersistenceException e){
            logger.error("Error at ShelfService: " + e.getMessage());
        }

        return responseForm;
    }

    public ResponseShelfDetailForm getShelfDetail(RequestShelfDetailForm requestForm, AuthorDTO authorDTO){
        ResponseShelfDetailForm responseForm = null;
        ShelfDTO shelfDTO = new ShelfDTO();
        convertRequestShelfDetailFormToShelfDTO(requestForm, shelfDTO, authorDTO);
        try {
            responseForm = shelfDAO.getShelfDetail(shelfDTO);
        }catch (PersistenceException e){
            logger.error("Error at ShelfService: " + e.getMessage());
        }
        return responseForm;
    }

    public ResponseCommonForm createShelf(RequestCreateShelfForm requestForm, AuthorDTO authorDTO){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        if(!StringHelper.isNullOrEmpty(authorDTO.getStoreId())){
            ShelfDTO shelfDTO = new ShelfDTO();
            convertRequestCreateShelfFormToShelfDTO(requestForm, shelfDTO, authorDTO);
            try {
                shelfDAO.createShelf(shelfDTO);
            }catch (PersistenceException e){
                logger.error("Error at ShelfService: " + e.getMessage());
                responseForm.setErrorCodes(catchSqlException(e.getMessage()));
            }
        }else{
            addErrorMessage(responseForm,MessageConstant.MSG_076);
        }
        return responseForm;
    }

    public ResponseCommonForm updateShelf(RequestUpdateShelfForm requestForm, AuthorDTO authorDTO){
        ResponseCommonForm responseForm = new ResponseCommonForm();

        if(!StringHelper.isNullOrEmpty(authorDTO.getStoreId())){
            ShelfDTO shelfDTO = new ShelfDTO();
            convertRequestUpdateShelfFormToShelfDTO(requestForm, shelfDTO);
            try{
                if(!shelfDAO.updateShelf(shelfDTO)){
                    addErrorMessage(responseForm,MessageConstant.MSG_012);
                }
            }catch (PersistenceException e){
                logger.error("Error at ShelfService: " + e.getMessage());
                responseForm.setErrorCodes(catchSqlException(e.getMessage()));
            }
        }else{
            addErrorMessage(responseForm,MessageConstant.MSG_076);
        }

        return responseForm;
    }

    public ResponseCommonForm updateShelfStatus(RequestUpdateShelfStatusForm requestForm, AuthorDTO authorDTO){
        ResponseCommonForm responseForm = null;
        if(!StringHelper.isNullOrEmpty(authorDTO.getStoreId())){
            ShelfDTO shelfDTO = new ShelfDTO();
            convertRequestUpdateStatusFormToShelfDTO(requestForm, shelfDTO, authorDTO);
            responseForm = checkUpdateStatusBusiness(shelfDTO);
            if(responseForm.getErrorCodes() == null){
                try {
                    shelfDAO.updateShelfStatus(shelfDTO);
                }catch (PersistenceException e){
                    logger.error("Error at ManagerService: " + e.getMessage());
                }
            }
        }else{
            addErrorMessage(responseForm,MessageConstant.MSG_076);
        }


        return responseForm;
    }

    public ResponseCommonForm changeShelfCamera(RequestChangeShelfCameraForm requestForm, AuthorDTO authorDTO){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        if(!StringHelper.isNullOrEmpty(authorDTO.getStoreId())){
            ShelfDTO shelfDTO = new ShelfDTO();
            convertRequestChangeShelfCameraForm(requestForm, shelfDTO, authorDTO);
            responseForm = checkChangeShelfCameraBusiness(shelfDTO);
            if(responseForm.getErrorCodes() == null){
                try{
                    if(shelfDTO.getAction() == ADD_ACTION){
                        shelfDAO.addShelfCameraIntoShelf(shelfDTO);
                    }else {
                        shelfDAO.removeShelfCameraFromShelf(shelfDTO);
                    }
                }catch (PersistenceException e) {
                    logger.error("Error ShelfService: " + e.getMessage());
                    responseForm.setErrorCodes(catchSqlException(e.getMessage()));
                }
            }
        }else{
            addErrorMessage(responseForm,MessageConstant.MSG_076);
        }

        return responseForm;
    }

    private void convertRequestShelfListToShelfDTO(RequestShelfListForm requestForm, ShelfDTO shelfDTO, AuthorDTO authorDTO){
        shelfDTO.setStoreId(authorDTO.getStoreId());
        shelfDTO.setShelfName(requestForm.getShelfName());
        shelfDTO.setStatusId(requestForm.getStatusId());

        if(requestForm.getPageNum() > 1){
            shelfDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }

        shelfDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        if(requestForm.getFetchNext() > 0){
            shelfDTO.setFetchNext(requestForm.getFetchNext());
        }
    }

    private void convertRequestShelfDetailFormToShelfDTO(RequestShelfDetailForm requestForm, ShelfDTO shelfDTO, AuthorDTO authorDTO){
        shelfDTO.setShelfId(requestForm.getShelfId());
        shelfDTO.setStoreId(authorDTO.getStoreId());
    }

    private void convertRequestCreateShelfFormToShelfDTO(RequestCreateShelfForm requestForm, ShelfDTO shelfDTO, AuthorDTO authorDTO){
        shelfDTO.setStoreId(authorDTO.getStoreId());
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

    private void convertRequestUpdateStatusFormToShelfDTO(RequestUpdateShelfStatusForm requestForm, ShelfDTO shelfDTO, AuthorDTO authorDTO){
        shelfDTO.setStoreId(authorDTO.getStoreId());
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
        ShelfDTO resultDAO = shelfDAO.getStatusId(shelfDTO);

        // Not found shelf
        if(resultDAO == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MessageConstant.MSG_012);
            responseForm.setErrorCodes(errorCodes);
        }
        //shelf is activating, can not change status
        else if(resultDAO.getStatusId() == ACTIVE_STATUS){
            ArrayList<String> errorCodes = new ArrayList<>();
            errorCodes.add(MessageConstant.MSG_076);
            responseForm.setErrorCodes(errorCodes);
        }
        //request inactive, check reason inactive is not empty
        else if (shelfDTO.getStatusId() == INACTIVE_STATUS){
            if( resultDAO.getTotalOfRecord() > 0 || StringHelper.isNullOrEmpty(shelfDTO.getReasonInactive())){
                ArrayList<String> errorCodes = new ArrayList<>();
                errorCodes.add(MessageConstant.MSG_066);
                responseForm.setErrorCodes(errorCodes);
            }
        }
        //other wise is true
        return responseForm;
    }

    private void convertRequestChangeShelfCameraForm(RequestChangeShelfCameraForm requestForm, ShelfDTO shelfDTO, AuthorDTO authorDTO){
        shelfDTO.setStoreId(authorDTO.getStoreId());
        shelfDTO.setShelfId(requestForm.getShelfId());
        shelfDTO.setCameraId(requestForm.getCameraId());
        shelfDTO.setAction(requestForm.getAction());
        shelfDTO.setUpdatedTime(TIME_ZONE_VIETNAMESE);
    }

    /*
     * //ShelfDTO shelfDTO is converted from requestForm
     * */
    private ResponseCommonForm checkChangeShelfCameraBusiness(ShelfDTO shelfDTO){
        ResponseCommonForm responseForm = new ResponseCommonForm();

        ShelfDTO shelfResultDAO = shelfDAO.getShelfStatus(shelfDTO);
        ShelfDTO cameraResultDAO = shelfDAO.getCameraStatus(shelfDTO);

        ArrayList<String> errorCodes = new ArrayList<>();
//       check shelf exist and available
        if(shelfResultDAO == null){
            errorCodes.add(MessageConstant.MSG_085);
        }
//        check camera exist and available
        if(cameraResultDAO  == null){
            errorCodes.add(MessageConstant.MSG_086);
        } else if (DETECT_HOT_SPOT != cameraResultDAO.getTypeDetect()) {
            errorCodes.add(MessageConstant.MSG_129);
        }


        if(errorCodes.size() > 0){
            responseForm.setErrorCodes(errorCodes);
            return responseForm;
        }

        if(shelfDTO.getAction() == ADD_ACTION){
            // TODO: xem lại chổ active khi add 1 camera
            if(shelfResultDAO.getStatusId() == INACTIVE_STATUS || cameraResultDAO.getStatusId() != PENDING_STATUS ){
                errorCodes.add(MessageConstant.MSG_087);
                responseForm.setErrorCodes(errorCodes);
            }
            if (shelfDAO.countCameraByShelfId(shelfDTO) >= MAX_CAMERA_ON_SHELF) {
                addErrorMessage(responseForm,MessageConstant.MSG_128);
            }
        }else if(shelfDTO.getAction() == REMOVE_ACTION){
            ShelfDTO mappingResultDAO = shelfDAO.getShelfCameraMappingStatus(shelfDTO);
            if(mappingResultDAO != null){
                errorCodes.add(MessageConstant.MSG_088);
            }
        }

        return responseForm;
    }
}
