package com.screens.product.dao;

import com.common.dao.BaseDAO;
import com.screens.product.dto.ProductDTO;
import com.screens.product.form.ResponseAllProductForm;
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
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getProductDetail",productDTO);
        }finally {
            closeConnection();
        }
    }

    public ResponseProductListForm getProductList(ProductDTO productDTO) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getProductList",productDTO);
        }finally {
            closeConnection();
        }
    }

    public ResponseAllProductForm getAllProduct(ProductDTO productDTO) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getAllProduct",productDTO);
        }finally {
            closeConnection();
        }
    }

    public boolean createProduct(ProductDTO productDTO) {
        try{
            openConnection();
            if(sqlSession.insert("com.screens.product.dao.sql.ProductDAO.createProduct",productDTO) > 0){
                if(sqlSession.insert("com.screens.product.dao.sql.ProductDAO.productAddCategory",productDTO) > 0){
                    this.sqlSession.commit();
                    return true;
                }
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean checkProductExist(ProductDTO productDTO) {
        try {
            openConnection();
            ProductDTO rs =  sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.checkProductExist",productDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public ResponseProductDetailForm getProductStatus(ProductDTO productDTO) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.getProductStatus",productDTO);
        }finally {
            closeConnection();
        }
    }

    public boolean checkAnyStackHaveProduct(ProductDTO productDTO) {
        try{
            openConnection();
            ProductDTO rs =  sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.checkAnyStackHaveProduct",productDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public int checkCategoriesValid(ProductDTO productDTO) {
        try{
            openConnection();
            ProductDTO rs =  sqlSession.selectOne("com.screens.product.dao.sql.ProductDAO.checkCategoriesValid",productDTO);
            return rs.getTotalOfRecord();
        }finally {
            closeConnection();
        }
    }

    public boolean changeStatus(ProductDTO productDTO) {
        try {
            openConnection();
            if(sqlSession.update("com.screens.product.dao.sql.ProductDAO.changeStatus",productDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean updateInfo(ProductDTO productDTO) {
        try {
            openConnection();
            if(sqlSession.update("com.screens.product.dao.sql.ProductDAO.updateInfo",productDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean addCategories(ProductDTO productDTO) {
        try{
            openConnection();
            if(sqlSession.delete("com.screens.product.dao.sql.ProductDAO.removeCategories",productDTO) > 0){
                if(sqlSession.insert("com.screens.product.dao.sql.ProductDAO.addCategories",productDTO) > 0){
                    this.sqlSession.commit();
                    return true;
                }
            }
            return false;
        }finally {
            closeConnection();
        }
    }
}
