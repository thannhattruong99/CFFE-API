package com.screens.product.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.product.dto.ProductDTO;
import com.screens.product.form.ResponseProductDetailForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductMapper extends BaseDAO {

    public ProductMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseProductDetailForm getProductDetail(ProductDTO productDTO) {
        return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getProductDetail",productDTO);
    }
}
