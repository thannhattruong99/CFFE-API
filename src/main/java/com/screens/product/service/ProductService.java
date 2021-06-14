package com.screens.product.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.product.dao.mapper.ProductMapper;
import com.screens.product.dto.ProductDTO;
import com.screens.product.form.*;
import com.screens.store.service.StoreService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public ResponseCommonForm updateStatus(RequestUpdateStatusProductForm requestForm) {
        ResponseCommonForm response = new ResponseCommonForm();
        ProductDTO productDTO = convertUpdateStatusFormToDTO(requestForm);
        try {
            List<String> errorMsg = new ArrayList<>();
            if (!productMapper.checkProductExist(productDTO)) {
                errorMsg.add("MSG-023");
                response.setErrorCodes(errorMsg);
            } else {
                ResponseProductDetailForm rs = productMapper.getProductStatus(productDTO);
                if ((rs.getStatusId() == 1) && (productDTO.getStatusId() == 2)
                        && (StringUtils.isNotEmpty(productDTO.getReasonInactive()))){
                    System.out.println("ACTION: ACTIVE => INACTIVE");
                    //check co product nao con tren any Stack hay ko
                    if (!productMapper.checkAnyStackHaveProduct(productDTO)){
                        productMapper.changeStatus(productDTO);
                    } else {
                        errorMsg.add("MSG-097");
                        response.setErrorCodes(errorMsg);
                    }
                } else if((rs.getStatusId() == 2) && (productDTO.getStatusId() == 1)) {
                    System.out.println("ACTION: INACTIVE => PENDING");
                    productMapper.changeStatus(productDTO);
                } else {
                    errorMsg.add("MSG-098");
                    response.setErrorCodes(errorMsg);
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
            productMapper.updateInfo(productDTO);
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
                List<String> errorMsg = new ArrayList<>();
                errorMsg.add("MSG-103");
                response.setErrorCodes(errorMsg);
            }
            //check product
            else if (!(productMapper.checkProductExist(productDTO))) {
                List<String> errorMsg = new ArrayList<>();
                errorMsg.add("MSG-023");
                response.setErrorCodes(errorMsg);
            }
            // check cate valid
            else if (productMapper.checkCategoriesValid(productDTO) != productDTO.getCategories().size()){
                List<String> errorMsg = new ArrayList<>();
                errorMsg.add("MSG-104");
                response.setErrorCodes(errorMsg);
            }
            else {
                // add moi
                productMapper.addCategories(productDTO);
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
}
