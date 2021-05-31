package com.screens.stack.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.stack.dao.mapper.StackMapper;
import com.screens.stack.dto.StackDTO;
import com.screens.stack.form.*;
import com.screens.store.dto.StoreDTO;
import com.screens.store.form.RequestCreateStoreForm;
import com.screens.store.service.StoreService;
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
            // Add product
            else if(stackDTO.getAction() == ADD_ACTION) {
                System.out.println("STATUS = STACK AND PRODUCT ARE EXIST");
                System.out.println("ACTION = ADD_ACTION");
                // Check product active
                if (!stackMapper.checkProductActive(stackDTO)){
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
            else if (stackDTO.getAction() == REMOVE_ACTION) {
                // Check product co nam tren stack
                if (stackMapper.checkStackProductMapping(stackDTO)) {
                    errorMsg.add("MSG-091");
                    response.setErrorCodes(errorMsg);
                } else {
                    stackMapper.removeProduct(stackDTO);
                }

            }



        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
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
