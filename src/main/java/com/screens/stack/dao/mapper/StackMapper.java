package com.screens.stack.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.stack.dto.StackDTO;
import com.screens.stack.form.ResponseStackDetailForm;
import com.screens.stack.form.ResponseStackListForm;
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

    public ResponseStackDetailForm getStackStatus(StackDTO stackDTO) {
        return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackStatus",stackDTO);
    }

    public ResponseStackListForm getStackListByShelf(StackDTO stackDTO) {
        return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackListByShelf",stackDTO);
    }

    public ResponseStackListForm getStackListByProductIdStoreId(StackDTO stackDTO) {
        return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackListByProductIdStoreId",stackDTO);
    }

    public boolean addProduct(StackDTO stackDTO) {
        if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.addProduct",stackDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    public boolean addCamera(StackDTO stackDTO) {
        if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.activeStack",stackDTO) > 0) {
            if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.activeCamera",stackDTO) > 0){
                if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.activeMapping",stackDTO) > 0){
                    this.sqlSession.commit();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeProduct(StackDTO stackDTO) {
        if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.removeProduct",stackDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    public boolean removeCamera(StackDTO stackDTO) {
        if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.addNewRecordMapping",stackDTO) > 0){
            if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.updateCameraPending",stackDTO) > 0){
                if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.updateStackPending",stackDTO) > 0){
                    if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.removeCamera",stackDTO) > 0){
                        this.sqlSession.commit();
                        return true;
                    }
                }

            }

        }
        return false;
    }

    public boolean changeStatus(StackDTO stackDTO) {
        if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.changeStatus",stackDTO) > 0){
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

    public boolean checkCameraPending(StackDTO stackDTO) {
        StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkCameraPending",stackDTO);
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

    public boolean stackIsExistInStore(StackDTO stackDTO) {
        StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackExist",stackDTO);
        if(rs.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean checkCameraExist(StackDTO stackDTO) {
        StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkCameraExist",stackDTO);
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

    public boolean checkStackProductMapping(StackDTO stackDTO) {
        StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackProductMapping",stackDTO);
        if(rs.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean checkStackCameraMapping(StackDTO stackDTO) {
        StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackCameraMapping",stackDTO);
        if(rs.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }
}
