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
            openSession();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackDetail",stackDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseStackDetailForm getStackStatus(StackDTO stackDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackStatus",stackDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseStackListForm getStackListByShelf(StackDTO stackDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackListByShelf",stackDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseStackListForm getStackListByProductIdStoreId(StackDTO stackDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.getStackListByProductIdStoreId",stackDTO);
        }finally {
            closeSession();
        }
    }

    public boolean addProduct(StackDTO stackDTO) throws PersistenceException {
        try{
            openSession();
            if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.addProduct",stackDTO) > 0){
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

    public boolean addCamera(StackDTO stackDTO) throws PersistenceException {
        try{
            openSession();
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
            closeSession();
        }
    }

    public boolean removeProduct(StackDTO stackDTO) throws PersistenceException {
        try{
            openSession();
            if(sqlSession.insert("com.screens.stack.dao.sql.StackDAO.removeProduct",stackDTO) > 0){
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

    public boolean removeCamera(StackDTO stackDTO) throws PersistenceException{
        try{
            openSession();
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
            closeSession();
        }
    }

    public boolean changeStatus(StackDTO stackDTO) throws PersistenceException{
        try{
            openSession();
            if(sqlSession.update("com.screens.stack.dao.sql.StackDAO.changeStatus",stackDTO) > 0){
                return true;
            }
            return false;
        }catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            closeSession();
        }
    }

    public boolean checkProductActive(StackDTO stackDTO) {
        try{
            openSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkProductActive",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean checkStackPending(StackDTO stackDTO) {
        try{
            openSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackPending",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean checkCameraPending(StackDTO stackDTO) {
        try{
            openSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkCameraPending",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean checkCameraTypeEmotion(StackDTO stackDTO) {
        try{
            openSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkCameraTypeEmotion",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean checkStackHaveProduct(StackDTO stackDTO) {
        try {
            openSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackHaveProduct",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean stackIsExistInStore(StackDTO stackDTO) {
        try {
            openSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackExist",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean shelfIsExistInStore(StackDTO stackDTO) {
        try{
            openSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.shelfIsExistInStore",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean checkCameraExist(StackDTO stackDTO) {
        try{
            openSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkCameraExist",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean checkProductExist(StackDTO stackDTO) {
        try{
            openSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkProductExist",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean checkStackProductMapping(StackDTO stackDTO) {
        try {
            openSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackProductMapping",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean checkStackCameraMapping(StackDTO stackDTO) {
        try{
            openSession();
            StackDTO rs =  sqlSession.selectOne("com.screens.stack.dao.sql.StackDAO.checkStackCameraMapping",stackDTO);
            if(rs.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }
}
