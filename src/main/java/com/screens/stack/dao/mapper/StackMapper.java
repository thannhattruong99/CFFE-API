package com.screens.stack.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.stack.dto.StackDTO;
import com.screens.stack.form.ResponseStackDetailForm;
import com.screens.stack.form.ResponseStackListForm;
import com.screens.store.dto.StoreDTO;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class StackMapper extends BaseDAO {

    public StackMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseStackDetailForm getStackDetail(StackDTO stackDTO) {
        return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackDetail",stackDTO);
    }

    public ResponseStackListForm getStackListByShelf(StackDTO stackDTO) {
        return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackListByShelf",stackDTO);
    }

    public boolean addProduct(StackDTO stackDTO) {
        if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.addProduct",stackDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    public boolean checkProductActive(StackDTO stackDTO) {
        StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkProductActive",stackDTO);
        if(rs.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean checkStackPending(StackDTO stackDTO) {
        StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackPending",stackDTO);
        if(rs.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean checkStackHaveProduct(StackDTO stackDTO) {
        StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackHaveProduct",stackDTO);
        if(rs.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean checkStackExist(StackDTO stackDTO) {
        StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackExist",stackDTO);
        if(rs.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean checkProductExist(StackDTO stackDTO) {
        StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkProductExist",stackDTO);
        if(rs.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

}
