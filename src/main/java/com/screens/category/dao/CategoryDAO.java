package com.screens.category.dao;


import com.common.dao.BaseDAO;
import com.screens.category.dto.CategoryDTO;
import com.screens.category.form.ResponseCategoryDetailForm;
import com.screens.category.form.ResponseCategoryListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAO extends BaseDAO {

    public CategoryDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseCategoryDetailForm getCategoryDetail(CategoryDTO categoryDTO) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.getCategoryDetail",categoryDTO);
        }finally {
            closeConnection();
        }
    }

    public ResponseCategoryListForm getCategoryList(CategoryDTO categoryDTO) {
        try{
            openConnection();
            return sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.getCategoryList",categoryDTO);

        }finally {
            closeConnection();
        }
    }

    public boolean createCategory(CategoryDTO categoryDTO) {
        try{
            openConnection();
            if(sqlSession.insert("com.screens.category.dao.sql.CategoryDAO.createCategory",categoryDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean checkHaveProductUsing(CategoryDTO categoryDTO){
        try{
            openConnection();
            CategoryDTO resultDAO = sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.checkProduct",categoryDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public boolean checkCategoryExist(CategoryDTO categoryDTO){
        try{
            openConnection();
            CategoryDTO resultDAO = sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.checkCategoryExist",categoryDTO);
            if(resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }

    }

    public boolean changeStatus(CategoryDTO categoryDTO) {
        try{
            openConnection();
            if(sqlSession.update("com.screens.category.dao.sql.CategoryDAO.changeStatus",categoryDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean updateInfo(CategoryDTO categoryDTO) {
        try{
            openConnection();
            if(sqlSession.update("com.screens.category.dao.sql.CategoryDAO.updateInfo",categoryDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }
}
