package com.screens.category.service;

import com.common.service.BaseService;
import com.screens.category.dao.mapper.CategoryMapper;
import com.screens.category.dto.CategoryDTO;
import com.screens.category.form.RequestGetCategoryDetailForm;
import com.screens.category.form.RequestGetCategoryListForm;
import com.screens.category.form.ResponseCategoryDetailForm;
import com.screens.category.form.ResponseCategoryListForm;
import com.screens.store.dto.StoreDTO;
import com.screens.store.form.RequestGetStoreListForm;
import com.screens.store.form.ResponseStoreListForm;
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

    public ResponseCategoryListForm getCategoryList(RequestGetCategoryListForm requestForm){
        ResponseCategoryListForm responseStoreListForm = null;
        CategoryDTO categoryDTO = converGetCategoryListFormToDTO(requestForm);
        try {
//            responseStoreListForm = categoryMapper.getCategoryList(categoryDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseStoreListForm;
    }

    private CategoryDTO converGetCategoryListFormToDTO(RequestGetCategoryListForm requestForm){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setSearchValue(requestForm.getSearchValue().toLowerCase().trim());
        categoryDTO.setSearchField(requestForm.getSearchField().toLowerCase().trim());
        categoryDTO.setStatusId(requestForm.getStatusId());
        if(requestForm.getPageNum() > 0){
            categoryDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }
        categoryDTO.setFetchNext(requestForm.getFetchNext());
        if(requestForm.getFetchNext() <= 0){
            categoryDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        }
        return categoryDTO;
    }

    private CategoryDTO converGetCategoryDetailFormToDTO(RequestGetCategoryDetailForm requestForm){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(requestForm.getCategoryId());
        return categoryDTO;
    }

}
