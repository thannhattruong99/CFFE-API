package com.screens.store.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.store.dao.mapper.StoreMapper;
import com.screens.store.dto.StoreDTO;
import com.screens.store.form.*;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);
    private static final int DEFAULT_FETCH_NEXT = 15;
    private static final int ACTIVE = 1;
    private static final int INACTIVE = 2;

    @Autowired
    StoreMapper storeMapper;

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
            response.setErrorCodes(sqlException(e.getMessage()));
        }
        return response;
    }

    public ResponseCommonForm changeStatus(RequestChangeStoreStatusForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        StoreDTO storeDTO = convertChangeStatusFormToDTO(requestForm);
        try {
            ResponseStoreDetailForm responseStoreDetailForm = storeMapper.getStoreStatus(storeDTO);
            if (((responseStoreDetailForm.getStatusId() == 2) && (storeDTO.getStatusId() == 3))
            || ((responseStoreDetailForm.getStatusId() == 3) && (storeDTO.getStatusId() == 2))){
                storeMapper.changeStatus(storeDTO);
            } else {
                System.out.println("Can not change status!!!!!!!!");
            }
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(sqlException(e.getMessage()));
        }
        return response;
    }

    private StoreDTO convertChangeStatusFormToDTO(RequestChangeStoreStatusForm requestForm){
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(requestForm.getStoreId());
        storeDTO.setStatusId(requestForm.getStatusId());
        return storeDTO;
    }
    private StoreDTO convertCreateStoreFormToDTO(RequestCreateStoreForm requestForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreName(requestForm.getStoreName());
//        storeDTO.setImageUrl(requestForm.getImageUrl());
        storeDTO.setAddress(requestForm.getAddress());
        storeDTO.setDistrictId(requestForm.getDistrictId());
        storeDTO.setAnalyzedTime(requestForm.getAnalyzedTime());
        storeDTO.setStatusId(ACTIVE);
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
//        storeDTO.setSortField(requestGetStoreListForm.getSortField());
        storeDTO.setStatusId(requestGetStoreListForm.getStatusId());
        if(requestGetStoreListForm.getPageNum() > 0){
            storeDTO.setOffSet((requestGetStoreListForm.getPageNum() - 1) * requestGetStoreListForm.getFetchNext());
        }

        storeDTO.setFetchNext(requestGetStoreListForm.getFetchNext());
        if(requestGetStoreListForm.getFetchNext() <= 0){
            storeDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        }
        return storeDTO;
    }

}
