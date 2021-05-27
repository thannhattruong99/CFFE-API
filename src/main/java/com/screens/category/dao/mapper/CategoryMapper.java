package com.screens.category.dao.mapper;


import com.common.dao.BaseDAO;
import com.screens.category.dto.CategoryDTO;
import com.screens.category.form.ResponseCategoryDetailForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryMapper extends BaseDAO {

    public CategoryMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseCategoryDetailForm getCategoryDetail(CategoryDTO categoryDTO) {
        return sqlSession.selectOne("com.screens.category.dao.sql.CategoryDAO.getCategoryDetail",categoryDTO);
    }

}
