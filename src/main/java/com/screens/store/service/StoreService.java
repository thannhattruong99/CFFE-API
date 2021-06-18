package com.screens.store.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.store.dao.mapper.StoreMapper;
import com.screens.store.form.RequestGetStoreListByProductForm;
import com.screens.store.dto.StoreDTO;
import com.screens.store.form.*;
import com.util.FileHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);
    private static final int ADD_MANAGER = 1;
    private static final int REMOVE_MANAGER = 2;


    @Autowired
    private StoreMapper storeMapper;

    public ResponseStoreListForm getStoreList(RequestGetStoreListForm requestGetStoreListForm){
        ResponseStoreListForm responseStoreListForm = null;
        StoreDTO storeDTO = convertGetStoreListFormToDTO(requestGetStoreListForm);
        try {
            responseStoreListForm = storeMapper.getStoreList(storeDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseStoreListForm;
    }

    public ResponseStoreListForm getStoreListByProduct(RequestGetStoreListByProductForm requestForm){
        ResponseStoreListForm responseStoreListForm = null;
        StoreDTO storeDTO = convertGetStoreListByProductFormToDTO(requestForm);
        try {
            responseStoreListForm = storeMapper.getStoreListByProduct(storeDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseStoreListForm;
    }

    public ResponseStoreListForm getStoreListShort(RequestGetStoreListShort requestForm){
        ResponseStoreListForm responseStoreListForm = null;
        StoreDTO storeDTO = new StoreDTO();
        try {
            responseStoreListForm = storeMapper.getStoreListShort(storeDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseStoreListForm;
    }

    public ResponseStoreDetailForm getStoreDetail(RequestGetStoreDetailForm requestForm) {
        ResponseStoreDetailForm responseStoreDetailForm = null;
        StoreDTO storeDTO = convertGetStoreDetailFormToDTO(requestForm);
        try {
            responseStoreDetailForm = storeMapper.getStoreDetail(storeDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseStoreDetailForm;
    }

    public ResponseCommonForm createStore(RequestCreateStoreForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        StoreDTO storeDTO = convertCreateStoreFormToDTO(requestForm);
        try {
            storeMapper.createStore(storeDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    public ResponseCommonForm changeStatus(RequestChangeStoreStatusForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        StoreDTO storeDTO = convertChangeStatusFormToDTO(requestForm);
        try {
            ResponseStoreDetailForm responseStoreDetailForm = storeMapper.getStoreStatus(storeDTO);
            if ((responseStoreDetailForm.getStatusId() == 3) && (storeDTO.getStatusId() == 2)
            && (StringUtils.isNotEmpty(storeDTO.getReasonInactive()))) {
                System.out.println("ACTION: STORE PENDING => INACTIVE");
                //check co ton tai shelf nao ko inactive hay khong
                if (storeMapper.checkShelf(storeDTO)) {
                    storeMapper.changeStatus(storeDTO);
                } else {
                    List<String> errorMsg = new ArrayList<>();
                    errorMsg.add("MSG-081");
                    response.setErrorCodes(errorMsg);
                }
            } else if ((responseStoreDetailForm.getStatusId() == 2) && (storeDTO.getStatusId() == 3)) {
                System.out.println("ACTION: STORE INACTIVE => PENDING");
                storeMapper.changeStatus(storeDTO);
            } else {
                List<String> errorMsg = new ArrayList<>();
                errorMsg.add("MSG-066");
                response.setErrorCodes(errorMsg);
            }
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    public ResponseCommonForm updateStoreInfo(RequestUpdateInfoForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        StoreDTO storeDTO = convertUpdateInfoFormToDTO(requestForm);
        try {
            storeMapper.updateInfo(storeDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

//    public ResponseCommonForm updateAnalyzedTime(RequestUpdateAnalyzedTime requestForm) {
//        ResponseCommonForm response = new ResponseCommonForm();
//        StoreDTO storeDTO = convertUpdateAnalyzedTimeFormToDTO(requestForm);
//        try {
//            storeMapper.updateAnalyzedTime(storeDTO);
//        } catch (PersistenceException e) {
//            logger.error("Error Message: " + e.getMessage());
//            response.setErrorCodes(catchSqlException(e.getMessage()));
//        }
//        return response;
//    }

    public ResponseCommonForm changeManager(RequestChangeManager requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        StoreDTO storeDTO = convertChangeManagerFormToDTO(requestForm);
        List<String> errorMsg = new ArrayList<>();
        try {
            // Add manager
            if (requestForm.getActive() == ADD_MANAGER) {
                // check 2 thang ton tai va pending
                if (!storeMapper.checkAvailableStore(storeDTO)) {
                    errorMsg.add("MSG-075");
                    response.setErrorCodes(errorMsg);
                }
                else if (!storeMapper.checkAvailableManager(storeDTO)) {
                    errorMsg.add("MSG-074");
                    response.setErrorCodes(errorMsg);
                } else {
                    // do add manager
                    storeMapper.addManager(storeDTO);
                }
            }
            // Remove manager
            if (requestForm.getActive() == REMOVE_MANAGER) {
                // check 2 thang co ton tai ko
                if (!storeMapper.countStoreById(storeDTO)) {
                    errorMsg.add("MSG-035");
                    response.setErrorCodes(errorMsg);
                }
                else if (!storeMapper.countUserById(storeDTO)) {
                    errorMsg.add("MSG-041");
                    response.setErrorCodes(errorMsg);
                }
                // check 2 thang co mapping voi nhau ko
                else if (!storeMapper.checkStoreManagerMapping(storeDTO)) {
                    errorMsg.add("MSG-077");
                    response.setErrorCodes(errorMsg);
                } else {
                    // do remove manager
                    storeMapper.removeManager(storeDTO);
                }
            }
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    private StoreDTO convertChangeManagerFormToDTO(RequestChangeManager requestForm){
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(requestForm.getStoreId());
        if (requestForm.getActive() == ADD_MANAGER) {
            storeDTO.setStatusId(ACTIVE_STATUS);
        }
        if (requestForm.getActive() == REMOVE_MANAGER) {
            storeDTO.setStatusId(PENDING_STATUS);
        }
        storeDTO.setUserId(requestForm.getUserId());
        return storeDTO;
    }

//    private StoreDTO convertUpdateAnalyzedTimeFormToDTO(RequestUpdateAnalyzedTime requestForm){
//        StoreDTO storeDTO = new StoreDTO();
//        storeDTO.setStoreId(requestForm.getStoreId());
//        storeDTO.setAnalyzedTime(requestForm.getAnalyzedTime());
//        return storeDTO;
//    }

    private StoreDTO convertChangeStatusFormToDTO(RequestChangeStoreStatusForm requestForm){
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(requestForm.getStoreId());
        storeDTO.setStatusId(requestForm.getStatusId());
        if (StringUtils.isNotEmpty(requestForm.getReasonInactive())) {
            storeDTO.setReasonInactive(requestForm.getReasonInactive());
        }
        return storeDTO;
    }

    private StoreDTO convertUpdateInfoFormToDTO(RequestUpdateInfoForm requestForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(requestForm.getStoreId());
        if (StringUtils.isNotEmpty(requestForm.getStoreName()))
            storeDTO.setStoreName(requestForm.getStoreName());
        if (StringUtils.isNotEmpty(requestForm.getImageUrl()))
            storeDTO.setImageUrl(requestForm.getImageUrl());
        if (StringUtils.isNotEmpty(requestForm.getAddress()))
            storeDTO.setAddress(requestForm.getAddress());
        if (requestForm.getDistrictId() != 0) {
            storeDTO.setDistrictId(requestForm.getDistrictId());
        }
        return storeDTO;
    }

    private StoreDTO convertCreateStoreFormToDTO(RequestCreateStoreForm requestForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreName(requestForm.getStoreName());
        if (StringUtils.isNotEmpty(requestForm.getImageUrl())){
            storeDTO.setImageUrl(requestForm.getImageUrl());
        } else {
            storeDTO.setImageUrl(DEFAULT_IMAGE);
        }
        storeDTO.setAddress(requestForm.getAddress());
        storeDTO.setDistrictId(requestForm.getDistrictId());
        storeDTO.setStatusId(PENDING_STATUS);
        return storeDTO;
    }

    private StoreDTO convertGetStoreDetailFormToDTO(RequestGetStoreDetailForm requestForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(requestForm.getStoreId());
        return storeDTO;
    }

    private StoreDTO convertGetStoreListFormToDTO(RequestGetStoreListForm requestGetStoreListForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setSearchValue(requestGetStoreListForm.getSearchValue().toLowerCase().trim());
        storeDTO.setSearchField(requestGetStoreListForm.getSearchField().toLowerCase().trim());
        storeDTO.setStatusId(requestGetStoreListForm.getStatusId());
        if(requestGetStoreListForm.getPageNum() > 0){
            storeDTO.setOffSet((requestGetStoreListForm.getPageNum() - 1) * requestGetStoreListForm.getFetchNext());
        }
        storeDTO.setCityId(requestGetStoreListForm.getCityId());
        storeDTO.setFetchNext(requestGetStoreListForm.getFetchNext());
        if(requestGetStoreListForm.getFetchNext() <= 0){
            storeDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        }
        return storeDTO;
    }

    private StoreDTO convertGetStoreListByProductFormToDTO(RequestGetStoreListByProductForm requestForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setSearchValue(requestForm.getSearchValue().toLowerCase().trim());
        storeDTO.setSearchField(requestForm.getSearchField().toLowerCase().trim());
        storeDTO.setStatusId(requestForm.getStatusId());
        if(requestForm.getPageNum() > 0){
            storeDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }
        storeDTO.setProductId(requestForm.getProductId());
        storeDTO.setFetchNext(requestForm.getFetchNext());
        if(requestForm.getFetchNext() <= 0){
            storeDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        }
        return storeDTO;
    }

}
