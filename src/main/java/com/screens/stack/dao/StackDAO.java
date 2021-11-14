package com.screens.stack.dao;

import com.common.dao.BaseDAO;
import com.screens.stack.dto.StackDTO;
import com.screens.stack.form.ResponseStackDetailForm;
import com.screens.stack.form.ResponseStackListForm;
import com.util.IDBHelper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;

@Repository
public class StackDAO extends BaseDAO {

    public StackDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseStackDetailForm getStackDetail(StackDTO stackDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackDetail",stackDTO);
        }finally {
            commit();
        }
    }

    public ResponseStackDetailForm getStackStatus(StackDTO stackDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackStatus",stackDTO);
        }finally {
            commit();
        }
    }

    public ResponseStackListForm getStackListByShelf(StackDTO stackDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackListByShelf",stackDTO);
        }finally {
            commit();
        }
    }

    public ResponseStackListForm getStackListByProductIdStoreId(StackDTO stackDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackListByProductIdStoreId",stackDTO);
        }finally {
            commit();
        }
    }

    public boolean addProduct(StackDTO stackDTO) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.addProduct",stackDTO) > 0){
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

    public boolean addCamera(StackDTO stackDTO) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.activeStack",stackDTO) > 0) {
                if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.activeCamera",stackDTO) > 0){
                    if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.activeMapping",stackDTO) > 0){
                        this.sqlSession.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public boolean removeProduct(StackDTO stackDTO) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.removeProduct",stackDTO) > 0){
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

    public boolean removeCamera(StackDTO stackDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.addNewRecordMapping",stackDTO) > 0){
                if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.updateCameraPending",stackDTO) > 0){
                    if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.updateStackPending",stackDTO) > 0){
                        if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.removeCamera",stackDTO) > 0){
                            return true;
                        }
                    }

                }
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public boolean changeStatus(StackDTO stackDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.changeStatus",stackDTO) > 0){
                return true;
            }
            return false;
        }catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public boolean checkProductActive(StackDTO stackDTO) {
        try{
            getSqlSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkProductActive",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkStackPending(StackDTO stackDTO) {
        try{
            getSqlSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackPending",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkCameraPending(StackDTO stackDTO) {
        try{
            getSqlSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkCameraPending",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkCameraTypeEmotion(StackDTO stackDTO) {
        try{
            getSqlSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkCameraTypeEmotion",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkStackHaveProduct(StackDTO stackDTO) {
        try {
            getSqlSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackHaveProduct",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean stackIsExistInStore(StackDTO stackDTO) {
        try {
            getSqlSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackExist",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean shelfIsExistInStore(StackDTO stackDTO) {
        try{
            getSqlSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.shelfIsExistInStore",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkCameraExist(StackDTO stackDTO) {
        try{
            getSqlSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkCameraExist",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkProductExist(StackDTO stackDTO) {
        try{
            getSqlSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkProductExist",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkStackProductMapping(StackDTO stackDTO) {
        try {
            getSqlSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackProductMapping",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkStackCameraMapping(StackDTO stackDTO) {
        try{
            getSqlSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackCameraMapping",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }
}
