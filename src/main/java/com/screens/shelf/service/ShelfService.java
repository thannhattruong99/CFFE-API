package com.screens.shelf.service;

import com.common.service.BaseService;
import com.screens.manager.service.ManagerService;
import com.screens.shelf.dao.mapper.ShelfMapper;
import com.screens.shelf.dto.ShelfDTO;
import com.screens.shelf.form.RequestShelfDetailForm;
import com.screens.shelf.form.RequestShelfListForm;
import com.screens.shelf.form.ResponseShelfDetailForm;
import com.screens.shelf.form.ResponseShelfListForm;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShelfService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(ShelfService.class);
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

    private void convertRequestShelfListToShelfDTO(RequestShelfListForm requestForm, ShelfDTO shelfDTO){
        shelfDTO.setUserName(requestForm.getUserName());
        shelfDTO.setStoreId(requestForm.getStoreId());
        shelfDTO.setShelfName(requestForm.getShelfName());
        shelfDTO.setStatusId(requestForm.getStatusId());

        int offSet = 0;
        if(requestForm.getPageNum() > 0){
            offSet = (requestForm.getPageNum() - 1) * requestForm.getFetchNext();
        }
        shelfDTO.setOffSet(offSet);

        shelfDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        if(requestForm.getFetchNext() > 0){
            shelfDTO.setFetchNext(requestForm.getFetchNext());
        }
    }

    private void convertRequestShelfDetailFormToShelfDTO(RequestShelfDetailForm requestForm, ShelfDTO shelfDTO){
        shelfDTO.setShelfId(requestForm.getShelfId());
    }
}
