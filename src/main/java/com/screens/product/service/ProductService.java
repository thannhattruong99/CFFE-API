package com.screens.product.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.product.dao.ProductDAO;
import com.screens.product.dto.ProductDTO;
import com.screens.product.form.*;
import com.screens.store.service.StoreService;
import com.util.MessageConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private ProductDAO productDAO;

    public ResponseProductDetailForm getProductDetail(RequestGetProductDetailForm requestForm) {
        ResponseProductDetailForm responseProductDetailForm = null;
        ProductDTO productDTO = converGetProductDetailFormToDTO(requestForm);
        try {
            responseProductDetailForm = productDAO.getProductDetail(productDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseProductDetailForm;
    }

    public ResponseProductListForm getProductListByStoreId(RequestGetProductListByStoreIdForm requestForm){
        ResponseProductListForm responseProductListForm = null;
        ProductDTO productDTO = convertGetProductListByStoreIdFormToDTO(requestForm);
        try {
            responseProductListForm = productDAO.getProductListByStoreId(productDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseProductListForm;
    }

    public ResponseProductListForm getProductList(RequestGetProductListForm requestForm){
        ResponseProductListForm responseProductListForm = null;
        ProductDTO productDTO = convertGetProductListFormToDTO(requestForm);
        try {
            responseProductListForm = productDAO.getProductList(productDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseProductListForm;
    }

    public ResponseAllProductForm getAllProduct(){
        ResponseAllProductForm responseProductListForm = null;
        ProductDTO productDTO = new ProductDTO();
        try {
            responseProductListForm = productDAO.getAllProduct(productDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseProductListForm;
    }

    public ResponseCommonForm createProduct(RequestCreateProductForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        ProductDTO productDTO = convertCreateProductFormToDTO(requestForm);
        try {
            productDAO.createProduct(productDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    public ResponseCommonForm updateStatus(RequestUpdateStatusProductForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        ProductDTO productDTO = convertUpdateStatusFormToDTO(requestForm);
        try {
            if (!productDAO.checkProductExist(productDTO)) {
                addErrorMessage(response,MessageConstant.MSG_023);
            } else {
                ResponseProductDetailForm rs = productDAO.getProductStatus(productDTO);
                if ((rs.getStatusId() == ACTIVE_STATUS) && (productDTO.getStatusId() == INACTIVE_STATUS)
                        && (StringUtils.isNotEmpty(productDTO.getReasonInactive()))){
                    System.out.println("ACTION: ACTIVE => INACTIVE");
                    //check co product nao con tren any Stack hay ko
                    if (!productDAO.checkAnyStackHaveProduct(productDTO)){
                        productDAO.changeStatus(productDTO);
                    } else {
                        addErrorMessage(response, MessageConstant.MSG_097);
                    }
                } else if((rs.getStatusId() == INACTIVE_STATUS) && (productDTO.getStatusId() == ACTIVE_STATUS)) {
                    System.out.println("ACTION: INACTIVE => ACTIVE");
                    productDAO.changeStatus(productDTO);
                } else {
                    addErrorMessage(response,MessageConstant.MSG_098);
                }
            }
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    public ResponseCommonForm updateProductInfo(RequestUpdateInfoProductForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        ProductDTO productDTO = convertUpdateInfoProductFormToDTO(requestForm);
        try {
            productDAO.updateInfo(productDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    public ResponseCommonForm updateCategory(RequestUpdateCategoryForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        ProductDTO productDTO = convertUpdateCategoryFormToDTO(requestForm);
        try {
            // Check empty
            if ((productDTO.getCategories() == null)||(productDTO.getCategories().size() <= 0)
            ||(productDTO.getCategories().size() >3)) {
                addErrorMessage(response,MessageConstant.MSG_103);
            }
            //check product
            else if (!(productDAO.checkProductExist(productDTO))) {
                addErrorMessage(response,MessageConstant.MSG_023);
            }
            // check cate valid
            else if (productDAO.checkCategoriesValid(productDTO) != productDTO.getCategories().size()){
                addErrorMessage(response,MessageConstant.MSG_104);
            }
            else {
                // add moi
                productDAO.addCategories(productDTO);
            }
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    private ProductDTO convertUpdateCategoryFormToDTO(RequestUpdateCategoryForm requestForm) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(requestForm.getProductId());
        productDTO.setCategories(requestForm.getCategories());
        return productDTO;
    }

    private ProductDTO convertUpdateInfoProductFormToDTO(RequestUpdateInfoProductForm requestForm) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(requestForm.getProductId());
        if (StringUtils.isNotEmpty(requestForm.getImageUrl()))
            productDTO.setImageUrl(requestForm.getImageUrl());
        if (StringUtils.isNotEmpty(requestForm.getProductName()))
            productDTO.setProductName(requestForm.getProductName());
        if (StringUtils.isNotEmpty(requestForm.getImageUrl()))
            productDTO.setImageUrl(requestForm.getImageUrl());
        if (StringUtils.isNotEmpty(requestForm.getDescription()))
            productDTO.setDescription(requestForm.getDescription());
        return productDTO;
    }

    private ProductDTO convertUpdateStatusFormToDTO(RequestUpdateStatusProductForm requestForm) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(requestForm.getProductId());
        productDTO.setStatusId(requestForm.getStatusId());
        if (StringUtils.isNotEmpty(requestForm.getReasonInactive())) {
            productDTO.setReasonInactive(requestForm.getReasonInactive());
        }
        return productDTO;
    }

    private ProductDTO convertCreateProductFormToDTO(RequestCreateProductForm requestForm) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(requestForm.getProductName());
        if (StringUtils.isNotEmpty(requestForm.getDescription()))
            productDTO.setDescription(requestForm.getDescription());
        if (StringUtils.isNotEmpty(requestForm.getImageUrl())){
            productDTO.setImageUrl(requestForm.getImageUrl());
        } else {
            productDTO.setImageUrl(DEFAULT_IMAGE);
        }
        if (requestForm.getCategories().size() > 0)
            productDTO.setCategories(requestForm.getCategories());
        productDTO.setStatusId(ACTIVE_STATUS);
        return productDTO;
    }


    private ProductDTO converGetProductDetailFormToDTO(RequestGetProductDetailForm requestForm){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(requestForm.getProductId());
        return productDTO;
    }

    private ProductDTO convertGetProductListFormToDTO(RequestGetProductListForm requestForm) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setSearchValue(requestForm.getSearchValue().toLowerCase().trim());
        productDTO.setSearchField(requestForm.getSearchField().toLowerCase().trim());
        productDTO.setStatusId(requestForm.getStatusId());
        if(requestForm.getPageNum() > 0){
            productDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }
        productDTO.setCategoryId(requestForm.getCategoryId());
        productDTO.setFetchNext(requestForm.getFetchNext());
        if(requestForm.getFetchNext() <= 0){
            productDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        }
        return productDTO;
    }

    private ProductDTO convertGetProductListByStoreIdFormToDTO(RequestGetProductListByStoreIdForm requestForm) {
        ProductDTO productDTO = new ProductDTO();
        if(requestForm.getPageNum() > 0){
            productDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }
        productDTO.setFetchNext(requestForm.getFetchNext());
        if(requestForm.getFetchNext() <= 0){
            productDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        }
        productDTO.setStoreId(requestForm.getStoreId());
        return productDTO;
    }
}
