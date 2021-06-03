package com.screens.product.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.product.dao.mapper.ProductMapper;
import com.screens.product.dto.ProductDTO;
import com.screens.product.form.*;
import com.screens.store.dto.StoreDTO;
import com.screens.store.form.RequestCreateStoreForm;
import com.screens.store.form.RequestGetStoreListForm;
import com.screens.store.form.ResponseStoreListForm;
import com.screens.store.service.StoreService;
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
    private ProductMapper productMapper;

    public ResponseProductDetailForm getProductDetail(RequestGetProductDetailForm requestForm) {
        ResponseProductDetailForm responseProductDetailForm = null;
        ProductDTO productDTO = converGetProductDetailFormToDTO(requestForm);
        try {
            responseProductDetailForm = productMapper.getProductDetail(productDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseProductDetailForm;
    }

    public ResponseProductListForm getProductList(RequestGetProductListForm requestForm){
        ResponseProductListForm responseProductListForm = null;
        ProductDTO productDTO = convertGetProductListFormToDTO(requestForm);
        try {
            responseProductListForm = productMapper.getProductList(productDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
        }
        return responseProductListForm;
    }

    public ResponseCommonForm createProduct(RequestCreateProductForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        ProductDTO productDTO = convertCreateProductFormToDTO(requestForm);
        try {
            productMapper.createProduct(productDTO);
        } catch (PersistenceException e) {
            logger.error("Error Message: " + e.getMessage());
            response.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return response;
    }

    private ProductDTO convertCreateProductFormToDTO(RequestCreateProductForm requestForm) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName(requestForm.getProductName());
        if (StringUtils.isNotEmpty(requestForm.getDescription()))
            productDTO.setDescription(requestForm.getDescription());
        if (StringUtils.isNotEmpty(requestForm.getImageUrl()))
            productDTO.setImageUrl(requestForm.getImageUrl());
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
}
