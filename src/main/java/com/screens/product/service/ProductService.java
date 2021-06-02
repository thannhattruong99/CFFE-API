package com.screens.product.service;

import com.common.service.BaseService;
import com.screens.product.dao.mapper.ProductMapper;
import com.screens.product.dto.ProductDTO;
import com.screens.product.form.RequestGetProductDetailForm;
import com.screens.product.form.RequestGetProductListForm;
import com.screens.product.form.ResponseProductDetailForm;
import com.screens.product.form.ResponseProductListForm;
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
