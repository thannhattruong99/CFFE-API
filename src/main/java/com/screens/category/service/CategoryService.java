package com.screens.category.service;

import com.common.service.BaseService;
import com.screens.category.dao.mapper.CategoryMapper;
import com.screens.category.dto.CategoryDTO;
import com.screens.category.form.RequestGetCategoryDetailForm;
import com.screens.category.form.ResponseCategoryDetailForm;
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
public class CategoryService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private CategoryMapper categoryMapper;

    public ResponseCategoryDetailForm getCategoryDetail(RequestGetCategoryDetailForm requestForm) {
        ResponseCategoryDetailForm responseCategoryDetailForm = null;
        CategoryDTO categoryDTO = converGetCategoryDetailFormToDTO(requestForm);
        try {
            responseCategoryDetailForm = categoryMapper.getCategoryDetail(categoryDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseCategoryDetailForm;
    }

    private CategoryDTO converGetCategoryDetailFormToDTO(RequestGetCategoryDetailForm requestForm){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(requestForm.getCategoryId());
        return categoryDTO;
    }

}
