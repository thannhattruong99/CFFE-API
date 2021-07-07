package com.screens.category.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.category.dao.CategoryDAO;
import com.screens.category.dto.CategoryDTO;
import com.screens.category.form.*;
import com.util.MessageConstant;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryDAO categoryDAO;

    public ResponseCategoryDetailForm getCategoryDetail(RequestGetCategoryDetailForm requestForm) {
        ResponseCategoryDetailForm responseCategoryDetailForm = null;
        CategoryDTO categoryDTO = converGetCategoryDetailFormToDTO(requestForm);
        try {
            responseCategoryDetailForm = categoryDAO.getCategoryDetail(categoryDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseCategoryDetailForm;
    }

    public ResponseCategoryListForm getCategoryList(RequestGetCategoryListForm requestForm){
        ResponseCategoryListForm responseStoreListForm = null;
        CategoryDTO categoryDTO = converGetCategoryListFormToDTO(requestForm);
        try {
            responseStoreListForm = categoryDAO.getCategoryList(categoryDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseStoreListForm;
    }

    public ResponseCommonForm createCategory(RequestCreateCategoryForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        CategoryDTO categoryDTO = convertCreateCategoryFormToDTO(requestForm);
        try {
            categoryDAO.createCategory(categoryDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    public ResponseCommonForm changeStatus(RequestChangeCategoryStatusForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        CategoryDTO categoryDTO = convertChangeStatusFormToDTO(requestForm);
        try {
            ResponseCategoryDetailForm res = categoryDAO.getCategoryDetail(categoryDTO);
            if (res == null) {
                addErrorMessage(response, MessageConstant.MSG_029);
            } else {
                if ((res.getStatusId() == 1) && (categoryDTO.getStatusId() == 2)) {
                    System.out.println("ACTION: CATEGORY ACTIVE => INACTIVE");
                    //check co ton tai PRODUCT nao hay khong
                    if (!categoryDAO.checkHaveProductUsing(categoryDTO)) {
                        categoryDAO.changeStatus(categoryDTO);
                    } else {
                        addErrorMessage(response,MessageConstant.MSG_109);
                    }
                } else if ((res.getStatusId() == 2) && (categoryDTO.getStatusId() == 1)) {
                    System.out.println("ACTION: CATEGORY INACTIVE => ACTIVE");
                    categoryDAO.changeStatus(categoryDTO);
                } else {
                    addErrorMessage(response,MessageConstant.MSG_110);
                }
            }
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    public ResponseCommonForm updateCategoryInfo(RequestUpdateInfoCategoryForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        CategoryDTO categoryDTO = convertUpdateInfoCategoryFormToDTO(requestForm);
        try {
            if(!categoryDAO.checkCategoryExist(categoryDTO)) {
                addErrorMessage(response,MessageConstant.MSG_029);
            } else {
                categoryDAO.updateInfo(categoryDTO);
            }
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    private CategoryDTO convertUpdateInfoCategoryFormToDTO(RequestUpdateInfoCategoryForm requestForm) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(requestForm.getCategoryId());
        categoryDTO.setCategoryName(requestForm.getCategoryName());
        return categoryDTO;
    }

    private CategoryDTO convertChangeStatusFormToDTO(RequestChangeCategoryStatusForm requestForm){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(requestForm.getCategoryId());
        categoryDTO.setStatusId(requestForm.getStatusId());
        return categoryDTO;
    }

    private CategoryDTO convertCreateCategoryFormToDTO(RequestCreateCategoryForm requestForm) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName(requestForm.getCategoryName());
        categoryDTO.setStatusId(ACTIVE_STATUS);
        return categoryDTO;
    }

    private CategoryDTO converGetCategoryListFormToDTO(RequestGetCategoryListForm requestForm){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setSearchValue(requestForm.getSearchValue());
        categoryDTO.setSearchField(requestForm.getSearchField());
        categoryDTO.setStatusId(requestForm.getStatusId());
        if(requestForm.getPageNum() > 0){
            categoryDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }
        categoryDTO.setFetchNext(requestForm.getFetchNext());
        if(requestForm.getFetchNext() <= 0){
            categoryDTO.setFetchNext(0);
        }
        return categoryDTO;
    }

    private CategoryDTO converGetCategoryDetailFormToDTO(RequestGetCategoryDetailForm requestForm){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(requestForm.getCategoryId());
        return categoryDTO;
    }

}
