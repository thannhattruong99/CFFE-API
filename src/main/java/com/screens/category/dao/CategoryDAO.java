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
        return sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.getCategoryDetail",categoryDTO);
    }

    public ResponseCategoryListForm getCategoryList(CategoryDTO categoryDTO) {
        return sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.getCategoryList",categoryDTO);
    }

    public boolean createCategory(CategoryDTO categoryDTO) {
        if(sqlSession.insert("com.screens.category.dao.sql.CategoryDAO.createCategory",categoryDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    public boolean checkHaveProductUsing(CategoryDTO categoryDTO){
        CategoryDTO resultDAO = sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.checkProduct",categoryDTO);
        if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean checkCategoryExist(CategoryDTO categoryDTO){
        CategoryDTO resultDAO = sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.checkCategoryExist",categoryDTO);
        if(resultDAO.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean changeStatus(CategoryDTO categoryDTO) {
        if(sqlSession.update("com.screens.category.dao.sql.CategoryDAO.changeStatus",categoryDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    public boolean updateInfo(CategoryDTO categoryDTO) {
        if(sqlSession.update("com.screens.category.dao.sql.CategoryDAO.updateInfo",categoryDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

}
