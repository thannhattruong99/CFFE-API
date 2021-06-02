package com.screens.product.service;

import com.common.service.BaseService;
import com.screens.product.dao.mapper.ProductMapper;
import com.screens.product.dto.ProductDTO;
import com.screens.product.form.RequestGetProductDetailForm;
import com.screens.product.form.ResponseProductDetailForm;
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

    private ProductDTO converGetProductDetailFormToDTO(RequestGetProductDetailForm requestForm){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(requestForm.getProductId());
        return productDTO;
    }
}
