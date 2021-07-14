package com.screens.product.dao;

import com.common.dao.BaseDAO;
import com.screens.product.dto.ProductDTO;
import com.screens.product.form.ResponseAllProductForm;
import com.screens.product.form.ResponseProductDetailForm;
import com.screens.product.form.ResponseProductListForm;
import com.util.IDBHelper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAO extends BaseDAO {

    public ProductDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseProductDetailForm getProductDetail(ProductDTO productDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getProductDetail",productDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseProductListForm getProductListByStoreId(ProductDTO productDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getProductListByStoreId",productDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseProductListForm getProductList(ProductDTO productDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getProductList",productDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseAllProductForm getAllProduct(ProductDTO productDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getAllProduct",productDTO);
        }finally {
            closeSession();
        }
    }

    public boolean checkProductExist(ProductDTO productDTO) {
        try {
            openSession();
            ProductDTO rs =  sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.checkProductExist",productDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public ResponseProductDetailForm getProductStatus(ProductDTO productDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getProductStatus",productDTO);
        }finally {
            closeSession();
        }
    }

    public boolean checkAnyStackHaveProduct(ProductDTO productDTO) {
        try{
            openSession();
            ProductDTO rs =  sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.checkAnyStackHaveProduct",productDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public int checkCategoriesValid(ProductDTO productDTO) {
        try{
            openSession();
            ProductDTO rs =  sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.checkCategoriesValid",productDTO);
            return rs.getTotalOfRecord();
        }finally {
            closeSession();
        }
    }

    public boolean createProduct(ProductDTO productDTO) throws PersistenceException {
        try{
            openSession();
            if(sqlSession.insert("com.screens.product.dao.sql.ProductDAO.createProduct",productDTO) > 0){
                if(sqlSession.insert("com.screens.product.dao.sql.ProductDAO.productAddCategory",productDTO) > 0){
                    return true;
                }
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            closeSession();
        }
    }

    public boolean changeStatus(ProductDTO productDTO) throws PersistenceException {
        try {
            openSession();
            if(sqlSession.update("com.screens.product.dao.sql.ProductDAO.changeStatus",productDTO) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            closeSession();
        }
    }

    public boolean updateInfo(ProductDTO productDTO) throws PersistenceException {
        try {
            openSession();
            if(sqlSession.update("com.screens.product.dao.sql.ProductDAO.updateInfo",productDTO) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            closeSession();
        }
    }

    public boolean addCategories(ProductDTO productDTO) throws PersistenceException {
        try{
            openSession();
            if(sqlSession.delete("com.screens.product.dao.sql.ProductDAO.removeCategories",productDTO) > 0){
                if(sqlSession.insert("com.screens.product.dao.sql.ProductDAO.addCategories",productDTO) > 0){
                    return true;
                }
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            closeSession();
        }
    }
}
