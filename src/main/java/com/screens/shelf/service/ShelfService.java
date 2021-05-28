package com.screens.shelf.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.manager.service.ManagerService;
import com.screens.shelf.dao.mapper.ShelfMapper;
import com.screens.shelf.dto.ShelfDTO;
import com.screens.shelf.dto.StackDTO;
import com.screens.shelf.form.*;
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
            shelfMapper.createShelf(shelfDTO);
        }catch (PersistenceException e){
            logger.error("Error at ShelfService: " + e.getMessage());
            responseForm.setErrorCodes(catchSqlException(e.getMessage()));
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

    private void convertRequestCreateShelfFormToShelfDTO(RequestCreateShelfForm requestForm, ShelfDTO shelfDTO){
        shelfDTO.setStoreId(requestForm.getStoreId());
        shelfDTO.setShelfName(requestForm.getShelfName());
        shelfDTO.setDescription(requestForm.getDescription());
        shelfDTO.setNumberOfStack(requestForm.getNumberOfStack());
        shelfDTO.setStatusId(ACTIVE_STATUS);
        shelfDTO.setCreatedTime(TIME_ZONE_VIETNAMESE);

        List<StackDTO> stackDTOS = new ArrayList<>();
        for(int i = 0; i < requestForm.getNumberOfStack(); i++){
            StackDTO stackDTO = new StackDTO(i + 1, TIME_ZONE_VIETNAMESE, "", ACTIVE_STATUS);
            stackDTOS.add(stackDTO);
        }

        shelfDTO.setStacks(stackDTOS);
    }


}
