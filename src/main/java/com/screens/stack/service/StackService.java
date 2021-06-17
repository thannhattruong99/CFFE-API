package com.screens.stack.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.filter.dto.AuthorDTO;
import com.screens.stack.dao.StackDAO;
import com.screens.stack.dto.RequestGetStackListByProductForm;
import com.screens.stack.dto.StackDTO;
import com.screens.stack.form.*;
import com.screens.store.service.StoreService;
import com.util.MessageConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StackService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private StackDAO stackDAO;

    public ResponseStackDetailForm getStackDetail(RequestGetStackDetailForm requestForm, AuthorDTO authorDTO) {
        ResponseStackDetailForm responseStackDetailForm = null;
        int authorStatus = checkAuthor(authorDTO);
        if (authorStatus == MANAGER_WITHIN_STORE) {
            StackDTO stackDTO = converGetStackDetailFormToDTO(requestForm,authorDTO);
            try {
                //check stack in store
                if (stackDAO.stackIsExistInStore(stackDTO)) {
                    responseStackDetailForm = stackDAO.getStackDetail(stackDTO);
                }
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
            }
        }
        return responseStackDetailForm;
    }

    public ResponseStackListForm getStackListByShelf(RequestGetStackListForm requestForm, AuthorDTO authorDTO){
        ResponseStackListForm responseStackListForm = null;
        int authorStatus = checkAuthor(authorDTO);
        if (authorStatus == MANAGER_WITHIN_STORE) {
            StackDTO stackDTO = convertGetStackListFormToDTO(requestForm,authorDTO);
            try {
                //check shelf in store
                if (stackDAO.shelfIsExistInStore(stackDTO)) {
                    responseStackListForm = stackDAO.getStackListByShelf(stackDTO);
                }
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
            }
        }
        return responseStackListForm;
    }

    public ResponseStackListForm getStackListByProductIdStoreId(RequestGetStackListByProductForm requestForm,AuthorDTO authorDTO){
        ResponseStackListForm responseStackListForm = null;
        int authorStatus = checkAuthor(authorDTO);
        if (authorStatus == MANAGER_WITHIN_STORE) {
            StackDTO stackDTO = convertGetStackListByProductIdStoreIdFormToDTO(requestForm,authorDTO);
            try {
                if (stackDAO.stackIsExistInStore(stackDTO)) {
                    responseStackListForm = stackDAO.getStackListByProductIdStoreId(stackDTO);
                }
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
            }
        }
        return responseStackListForm;
    }

    public ResponseCommonForm changeProduct(RequestAddProduct requestForm, AuthorDTO authorDTO) {
        ResponseCommonForm response = new ResponseCommonForm();
        int authorStatus = checkAuthor(authorDTO);
        if (authorStatus == MANAGER_WITHIN_STORE) {
            StackDTO stackDTO = convertChangeProductFormToDTO(requestForm,authorDTO);
            try {

                // Add product
                if(stackDTO.getAction() == ADD_ACTION) {
                    System.out.println("ACTION = ADD_ACTION");
                    // Check Stack co ton tai trong store
                    if (!stackDAO.stackIsExistInStore(stackDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_022);
                    }
                    // Check Product co ton tai
                    else if (!stackDAO.checkProductExist(stackDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_023);
                    }
                    // Check product active
                    else if (!stackDAO.checkProductActive(stackDTO)){
                        addErrorMessage(response,MessageConstant.MSG_089);
                    }
                    // Check stack co pending hay ko
                    else if (!stackDAO.checkStackPending(stackDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_090);
                    }
                    // Check co product nao dang tren stack do hay ko
                    else if (stackDAO.checkStackHaveProduct(stackDTO)) {
                        System.out.println("*** STACK ALREADY HAVE PRODUCT");
                        addErrorMessage(response,MessageConstant.MSG_091);
                    } else {
                        stackDAO.addProduct(stackDTO);
                        System.out.println("RS = SUSSCESS");
                    }
                }

                // Remove product
                if (stackDTO.getAction() == REMOVE_ACTION) {
                    System.out.println("ACTION = REMOVE_ACTION");
                    // Check Stack co ton tai trong store
                    if (!stackDAO.stackIsExistInStore(stackDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_022);
                    }
                    // Check product co nam tren stack
                    else if (!stackDAO.checkStackProductMapping(stackDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_092);
                    }
                    // Check Stack Mapping is pending
                    else {
                        stackDAO.removeProduct(stackDTO);
                    }
                }
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
                response.setErrorCodes(catchSqlException(e.getMessage()));
            }
        } else {
            addErrorMessage(response,MessageConstant.MSG_076);
        }
        return response;
    }

    public ResponseCommonForm changeCamera(RequestAddCamera requestForm, AuthorDTO authorDTO) {
        ResponseCommonForm response = new ResponseCommonForm();
        int authorStatus = checkAuthor(authorDTO);
        if (authorStatus == MANAGER_WITHIN_STORE) {
            StackDTO stackDTO = convertChangeCameraFormToDTO(requestForm,authorDTO);
            try {
                // Add Camera
                if (stackDTO.getAction() == ADD_ACTION) {
                    System.out.println("ACTION = ADD_ACTION");
                    // Check Stack co ton tai trong store
                    if (!stackDAO.stackIsExistInStore(stackDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_022);
                    }
                    // Check Camera co ton tai
                    else if (!stackDAO.checkCameraExist(stackDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_020);
                    }
                    // Check stack is pending
                    else if (!stackDAO.checkStackPending(stackDTO)){
                        System.out.println("*** STACK ALREADY ACTIVE OR INACTIVE");
                        addErrorMessage(response,MessageConstant.MSG_090);
                    }
                    // Check Camera is pending
                    else if (!stackDAO.checkCameraPending(stackDTO)){
                        System.out.println("*** Camera NOT pending");
                        addErrorMessage(response,MessageConstant.MSG_093);
                    }
                    // check stack co product
                    else if (!stackDAO.checkStackHaveProduct(stackDTO)){
                        System.out.println("*** Stack not have product");
                        addErrorMessage(response,MessageConstant.MSG_100);
                    }
                    else {
                        stackDAO.addCamera(stackDTO);
                        System.out.println("RS = SUSSCESS");
                    }
                }

                // Remove Camera
                if (stackDTO.getAction() == REMOVE_ACTION) {
                    // Check Stack co ton tai trong store
                    if (!stackDAO.stackIsExistInStore(stackDTO)) {
                        addErrorMessage(response, MessageConstant.MSG_022);
                    } else
                    // Check camera co nam tren stack
                    if (!stackDAO.checkStackCameraMapping(stackDTO)) {
                        addErrorMessage(response,MessageConstant.MSG_094);
                    } else {
                        stackDAO.removeCamera(stackDTO);
                        System.out.println("RS = SUSSCESS");
                    }
                }
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
                response.setErrorCodes(catchSqlException(e.getMessage()));
            }
        } else {
            addErrorMessage(response,MessageConstant.MSG_076);
        }

        return response;
    }

    public ResponseCommonForm updateStatus(RequestUpdateStatusForm requestForm,AuthorDTO authorDTO) {
        ResponseCommonForm response = new ResponseCommonForm();
        int authorStatus = checkAuthor(authorDTO);
        if (authorStatus == MANAGER_WITHIN_STORE) {
            StackDTO stackDTO = convertUpdateStatusFormToDTO(requestForm,authorDTO);
            try {
                if (!stackDAO.stackIsExistInStore(stackDTO)) {
                    addErrorMessage(response,MessageConstant.MSG_022);
                } else {
                    ResponseStackDetailForm rs = stackDAO.getStackStatus(stackDTO);
                    if ((rs.getStatusId() == 3) && (stackDTO.getStatusId() == 2)
                            && (StringUtils.isNotEmpty(stackDTO.getReasonInactive()))){
                        System.out.println("ACTION: PENDING => INACTIVE");
                        //check co product nao con tren Stack hay ko
                        if (!stackDAO.checkStackHaveProduct(stackDTO)){
                            stackDAO.changeStatus(stackDTO);
                        } else {
                            addErrorMessage(response,MessageConstant.MSG_095);
                        }
                    } else if((rs.getStatusId() == 2) && (stackDTO.getStatusId() == 3)) {
                        System.out.println("ACTION: INACTIVE => PENDING");
                        stackDAO.changeStatus(stackDTO);
                    } else {
                        addErrorMessage(response,MessageConstant.MSG_066);
                    }
                }
            } catch (PersistenceException e) {
                logger.error("Error Message: " + e.getMessage());
                response.setErrorCodes(catchSqlException(e.getMessage()));
            }
        } else {
            addErrorMessage(response,MessageConstant.MSG_076);
        }
        return response;
    }

    private StackDTO convertUpdateStatusFormToDTO(RequestUpdateStatusForm requestForm,AuthorDTO authorDTO) {
        StackDTO stackDTO = new StackDTO();
        stackDTO.setStackId(requestForm.getStackId());
        stackDTO.setStatusId(requestForm.getStatusId());
        stackDTO.setStoreId(authorDTO.getStoreId());
        if (StringUtils.isNotEmpty(requestForm.getReasonInactive())) {
            stackDTO.setReasonInactive(requestForm.getReasonInactive());
        }
        return stackDTO;
    }

    private StackDTO convertChangeCameraFormToDTO (RequestAddCamera requestForm,AuthorDTO authorDTO) {
        StackDTO stackDTO = new StackDTO();
        stackDTO.setStackId(requestForm.getStackId());
        stackDTO.setCameraId(requestForm.getCameraId());
        stackDTO.setAction(requestForm.getAction());
        stackDTO.setStoreId(authorDTO.getStoreId());
        return stackDTO;
    }

    private StackDTO convertChangeProductFormToDTO (RequestAddProduct requestForm,AuthorDTO authorDTO) {
        StackDTO stackDTO = new StackDTO();
        stackDTO.setStackId(requestForm.getStackId());
        stackDTO.setProductId(requestForm.getProductId());
        stackDTO.setAction(requestForm.getAction());
        stackDTO.setStoreId(authorDTO.getStoreId());
        return stackDTO;
    }

    private StackDTO convertGetStackListFormToDTO(RequestGetStackListForm requestForm, AuthorDTO authorDTO) {
        StackDTO stackDTO = new StackDTO();
        stackDTO.setShelfId(requestForm.getShelfId());
        stackDTO.setStatusId(requestForm.getStatusId());
        stackDTO.setStoreId(authorDTO.getStoreId());
        return stackDTO;
    }

    private StackDTO convertGetStackListByProductIdStoreIdFormToDTO(RequestGetStackListByProductForm requestForm,AuthorDTO authorDTO) {
        StackDTO stackDTO = new StackDTO();
        stackDTO.setProductId(requestForm.getProductId());
        stackDTO.setStoreId(authorDTO.getStoreId());
        stackDTO.setStatusId(requestForm.getStatusId());
        if(requestForm.getPageNum() > 0){
            stackDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }
        stackDTO.setFetchNext(requestForm.getFetchNext());
        if(requestForm.getFetchNext() <= 0){
            stackDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        }
        return stackDTO;
    }

    private StackDTO converGetStackDetailFormToDTO(RequestGetStackDetailForm requestForm,AuthorDTO authorDTO){
        StackDTO stackDTO = new StackDTO();
        stackDTO.setStackId(requestForm.getStackId());
        stackDTO.setStoreId(authorDTO.getStoreId());
        return stackDTO;
    }


}
