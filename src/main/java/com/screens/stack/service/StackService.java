package com.screens.stack.service;

import com.common.service.BaseService;
import com.screens.stack.dao.mapper.StackMapper;
import com.screens.stack.dto.StackDTO;
import com.screens.stack.form.RequestGetStackDetailForm;
import com.screens.stack.form.ResponseStackDetailForm;
import com.screens.store.service.StoreService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private StackDTO converGetStackDetailFormToDTO(RequestGetStackDetailForm requestForm){
        StackDTO stackDTO = new StackDTO();
        stackDTO.setStackId(requestForm.getStackId());
        return stackDTO;
    }


}
