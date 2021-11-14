package com.screens.category.dao;


import com.common.dao.BaseDAO;
import com.screens.category.dto.CategoryDTO;
import com.screens.category.form.ResponseCategoryDetailForm;
import com.screens.category.form.ResponseCategoryListForm;
import com.util.IDBHelper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAO extends BaseDAO {

    public CategoryDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseCategoryDetailForm getCategoryDetail(CategoryDTO categoryDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.getCategoryDetail",categoryDTO);
        }finally {
            commit();
        }
    }

    public ResponseCategoryListForm getCategoryList(CategoryDTO categoryDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.getCategoryList",categoryDTO);

        }finally {
            commit();
        }
    }

    public boolean createCategory(CategoryDTO categoryDTO) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.insert("com.screens.category.dao.sql.CategoryDAO.createCategory",categoryDTO) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public boolean checkHaveProductUsing(CategoryDTO categoryDTO){
        try{
            getSqlSession();
            CategoryDTO resultDAO = sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.checkProduct",categoryDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkCategoryExist(CategoryDTO categoryDTO){
        try{
            getSqlSession();
            CategoryDTO resultDAO = sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.checkCategoryExist",categoryDTO);
            if(resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }

    }

    public boolean changeStatus(CategoryDTO categoryDTO) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.update("com.screens.category.dao.sql.CategoryDAO.changeStatus",categoryDTO) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public boolean updateInfo(CategoryDTO categoryDTO) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.update("com.screens.category.dao.sql.CategoryDAO.updateInfo",categoryDTO) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }
}
