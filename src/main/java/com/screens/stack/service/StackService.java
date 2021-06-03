package com.screens.stack.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.stack.dao.mapper.StackMapper;
import com.screens.stack.dto.StackDTO;
import com.screens.stack.form.*;
import com.screens.store.dto.StoreDTO;
import com.screens.store.service.StoreService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StackService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private StackMapper stackMapper;

    public ResponseStackDetailForm getStackDetail(RequestGetStackDetailForm requestForm) {
        ResponseStackDetailForm responseStackDetailForm = null;
        StackDTO stackDTO = converGetStackDetailFormToDTO(requestForm);
        try {
            responseStackDetailForm = stackMapper.getStackDetail(stackDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseStackDetailForm;
    }

    public ResponseStackListForm getStackListByShelf(RequestGetStackListForm requestForm){
        ResponseStackListForm responseStackListForm = null;
        StackDTO stackDTO = convertGetStackListFormToDTO(requestForm);
        try {
            responseStackListForm = stackMapper.getStackListByShelf(stackDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseStackListForm;
    }

    public ResponseCommonForm changeProduct(RequestAddProduct requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        StackDTO stackDTO = convertChangeProductFormToDTO(requestForm);
        try {
            List<String> errorMsg = new ArrayList<>();

            // Add product
             if(stackDTO.getAction() == ADD_ACTION) {
                System.out.println("ACTION = ADD_ACTION");
                // Check Stack co ton tai
                if (!stackMapper.checkStackExist(stackDTO)) {
                    errorMsg.add("MSG-022");
                    response.setErrorCodes(errorMsg);
                }
                // Check Product co ton tai
                else if (!stackMapper.checkProductExist(stackDTO)) {
                    errorMsg.add("MSG-023");
                    response.setErrorCodes(errorMsg);
                }
                // Check product active
                else if (!stackMapper.checkProductActive(stackDTO)){
                    System.out.println("*** product NOT active");
                    errorMsg.add("MSG-089");
                    response.setErrorCodes(errorMsg);
                }
                // Check stack co pending hay ko
                else if (!stackMapper.checkStackPending(stackDTO)) {
                    System.out.println("*** STACK ALREADY ACTIVE OR INACTIVE");
                    errorMsg.add("MSG-090");
                    response.setErrorCodes(errorMsg);
                }
                // Check co product nao dang tren stack do hay ko
                else if (stackMapper.checkStackHaveProduct(stackDTO)) {
                    System.out.println("*** STACK ALREADY HAVE PRODUCT");
                    errorMsg.add("MSG-091");
                    response.setErrorCodes(errorMsg);
                } else {
                    stackMapper.addProduct(stackDTO);
                    System.out.println("RS = SUSSCESS");
                }
            }

            // Remove product
            if (stackDTO.getAction() == REMOVE_ACTION) {
                System.out.println("ACTION = REMOVE_ACTION");
                // Check product co nam tren stack
                if (!stackMapper.checkStackProductMapping(stackDTO)) {
                    errorMsg.add("MSG-092");
                    response.setErrorCodes(errorMsg);
                }
                // Check Stack Mapping is pending
                else {
                    stackMapper.removeProduct(stackDTO);
                }
            }

        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    public ResponseCommonForm changeCamera(RequestAddCamera requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        StackDTO stackDTO = convertChangeCameraFormToDTO(requestForm);
        try {
            List<String> errorMsg = new ArrayList<>();

            // Add Camera
            if (stackDTO.getAction() == ADD_ACTION) {
                System.out.println("ACTION = ADD_ACTION");
                // Check Stack co ton tai
                if (!stackMapper.checkStackExist(stackDTO)) {
                    errorMsg.add("MSG-022");
                    response.setErrorCodes(errorMsg);
                }
                // Check Camera co ton tai
                else if (!stackMapper.checkCameraExist(stackDTO)) {
                    errorMsg.add("MSG-020");
                    response.setErrorCodes(errorMsg);
                }
                // Check stack is pending
                else if (!stackMapper.checkStackPending(stackDTO)){
                    System.out.println("*** STACK ALREADY ACTIVE OR INACTIVE");
                    errorMsg.add("MSG-090");
                    response.setErrorCodes(errorMsg);
                }
                // Check Camera is pending
                else if (!stackMapper.checkCameraPending(stackDTO)){
                    System.out.println("*** Camera NOT pending");
                    errorMsg.add("MSG-093");
                    response.setErrorCodes(errorMsg);
                }
                // check stack co product
                else if (!stackMapper.checkStackHaveProduct(stackDTO)){
                    System.out.println("*** Stack not have product");
                    errorMsg.add("MSG-100");
                    response.setErrorCodes(errorMsg);
                }
                else {
                    stackMapper.addCamera(stackDTO);
                    System.out.println("RS = SUSSCESS");
                }
            }

            // Remove Camera
            if (stackDTO.getAction() == REMOVE_ACTION) {
                // Check camera co nam tren stack
                if (!stackMapper.checkStackCameraMapping(stackDTO)) {
                    errorMsg.add("MSG-094");
                    response.setErrorCodes(errorMsg);
                } else {
                    stackMapper.removeCamera(stackDTO);
                    System.out.println("RS = SUSSCESS");
                }
            }

        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    public ResponseCommonForm updateStatus(RequestUpdateStatusForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        StackDTO stackDTO = convertUpdateStatusFormToDTO(requestForm);
        try {
            List<String> errorMsg = new ArrayList<>();
            if (!stackMapper.checkStackExist(stackDTO)) {
                errorMsg.add("MSG-022");
                response.setErrorCodes(errorMsg);
            } else {
                ResponseStackDetailForm rs = stackMapper.getStackStatus(stackDTO);
                if ((rs.getStatusId() == 3) && (stackDTO.getStatusId() == 2)
                        && (StringUtils.isNotEmpty(stackDTO.getReasonInactive()))){
                    System.out.println("ACTION: PENDING => INACTIVE");
                    //check co product nao con tren Stack hay ko
                    if (!stackMapper.checkStackHaveProduct(stackDTO)){
                        stackMapper.changeStatus(stackDTO);
                    } else {
                        errorMsg.add("MSG-095");
                        response.setErrorCodes(errorMsg);
                    }
                } else if((rs.getStatusId() == 2) && (stackDTO.getStatusId() == 3)) {
                    System.out.println("ACTION: INACTIVE => PENDING");
                    stackMapper.changeStatus(stackDTO);
                } else {
                    errorMsg.add("MSG-066");
                    response.setErrorCodes(errorMsg);
                }
            }
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    private StackDTO convertUpdateStatusFormToDTO(RequestUpdateStatusForm requestForm) {
        StackDTO stackDTO = new StackDTO();
        stackDTO.setStackId(requestForm.getStackId());
        stackDTO.setStatusId(requestForm.getStatusId());
        if (StringUtils.isNotEmpty(requestForm.getReasonInactive())) {
            stackDTO.setReasonInactive(requestForm.getReasonInactive());
        }
        return stackDTO;
    }

    private StackDTO convertChangeCameraFormToDTO (RequestAddCamera requestForm) {
        StackDTO stackDTO = new StackDTO();
        stackDTO.setStackId(requestForm.getStackId());
        stackDTO.setCameraId(requestForm.getCameraId());
        stackDTO.setAction(requestForm.getAction());
        return stackDTO;
    }

    private StackDTO convertChangeProductFormToDTO (RequestAddProduct requestForm) {
        StackDTO stackDTO = new StackDTO();
        stackDTO.setStackId(requestForm.getStackId());
        stackDTO.setProductId(requestForm.getProductId());
        stackDTO.setAction(requestForm.getAction());
        return stackDTO;
    }

    private StackDTO convertGetStackListFormToDTO(RequestGetStackListForm requestForm) {
        StackDTO stackDTO = new StackDTO();
        stackDTO.setShelfId(requestForm.getShelfId());
        stackDTO.setStatusId(requestForm.getStatusId());
        return stackDTO;
    }

    private StackDTO converGetStackDetailFormToDTO(RequestGetStackDetailForm requestForm){
        StackDTO stackDTO = new StackDTO();
        stackDTO.setStackId(requestForm.getStackId());
        return stackDTO;
    }


}
