package com.screens.store.service;

import com.screens.store.dao.mapper.StoreMapper;
import com.screens.store.dto.StoreDTO;
import com.screens.store.form.RequestGetStoreDetailForm;
import com.screens.store.form.RequestGetStoreListForm;
import com.screens.store.form.ResponseStoreDetailForm;
import com.screens.store.form.ResponseStoreListForm;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);
    private static final int DEFAULT_FETCH_NEXT = 10;

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

    private StoreDTO convertGetStoreDetailFormToDTO(RequestGetStoreDetailForm requestForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(requestForm.getStoreId());
        return storeDTO;
    }
    private StoreDTO convertGetStoreListFormToDTO(RequestGetStoreListForm requestGetStoreListForm) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setSearchValue(requestGetStoreListForm.getSearchValue().toLowerCase().trim());
        storeDTO.setSearchField(requestGetStoreListForm.getSearchField().toLowerCase().trim());
        storeDTO.setSortField(requestGetStoreListForm.getSortField());
        storeDTO.setDesc(false);

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
