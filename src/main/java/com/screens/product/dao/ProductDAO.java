package com.screens.product.dao;

import com.common.dao.BaseDAO;
import com.screens.product.dto.ProductDTO;
import com.screens.product.form.ResponseProductDetailForm;
import com.screens.product.form.ResponseProductListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO extends BaseDAO {

    public ProductDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseProductDetailForm getProductDetail(ProductDTO productDTO) {
        return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getProductDetail",productDTO);
    }

    public ResponseProductListForm getProductList(ProductDTO productDTO) {
        return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getProductList",productDTO);
    }

    public boolean createProduct(ProductDTO productDTO) {
        if(sqlSession.insert("com.screens.product.dao.sql.ProductDAO.createProduct",productDTO) > 0){
            System.out.println("id =========== "+productDTO.getProductId());
//            this.sqlSession.commit();
            if(sqlSession.insert("com.screens.product.dao.sql.ProductDAO.productAddCategory",productDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
        }
        return false;
    }

    public boolean checkProductExist(ProductDTO productDTO) {
        ProductDTO rs =  sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.checkProductExist",productDTO);
        if(rs.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public ResponseProductDetailForm getProductStatus(ProductDTO productDTO) {
        return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getProductStatus",productDTO);
    }

    public boolean checkAnyStackHaveProduct(ProductDTO productDTO) {
        ProductDTO rs =  sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.checkAnyStackHaveProduct",productDTO);
        if(rs.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public int checkCategoriesValid(ProductDTO productDTO) {
        ProductDTO rs =  sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.checkCategoriesValid",productDTO);
        System.out.println("===========" + rs.getTotalOfRecord());
        return rs.getTotalOfRecord();
    }

    public boolean changeStatus(ProductDTO productDTO) {
        if(sqlSession.update("com.screens.product.dao.sql.ProductDAO.changeStatus",productDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    public boolean updateInfo(ProductDTO productDTO) {
        if(sqlSession.update("com.screens.product.dao.sql.ProductDAO.updateInfo",productDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    public boolean addCategories(ProductDTO productDTO) {
        if(sqlSession.delete("com.screens.product.dao.sql.ProductDAO.removeCategories",productDTO) > 0){
            if(sqlSession.insert("com.screens.product.dao.sql.ProductDAO.addCategories",productDTO) > 0){
                this.sqlSession.commit();
                return true;
            }

        }
        return false;
    }
}
