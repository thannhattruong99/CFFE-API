package com.screens.store.dao;

import com.common.dao.BaseDAO;
import com.screens.store.dto.StoreDTO;
import com.screens.store.form.ResponseStoreDetailForm;
import com.screens.store.form.ResponseStoreListForm;
import com.util.IDBHelper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;

@Repository
public class StoreDAO extends BaseDAO {

    public StoreDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseStoreListForm getStoreList(StoreDTO storeDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreList",storeDTO);
        }finally {
            commit();
        }
    }

    public ResponseStoreListForm getStoreListByProduct(StoreDTO storeDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreListByProduct",storeDTO);
        }finally {
            commit();
        }
    }

    public ResponseStoreListForm getStoreListShort(StoreDTO storeDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreListShort",storeDTO);
        }finally {
            commit();
        }
    }

    public ResponseStoreDetailForm getStoreDetail(StoreDTO storeDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreDetail",storeDTO);
        }finally {
            commit();
        }
    }
    public ResponseStoreDetailForm getStoreStatus(StoreDTO storeDTO) {
        try{
            getSqlSession();
            return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreStatus",storeDTO);
        }finally {
            commit();
        }
    }

    public boolean createStore(StoreDTO storeDTO) throws PersistenceException {
        try {
            getSqlSession();
            if(sqlSession.insert("com.screens.store.dao.sql.StoreDAO.createStore",storeDTO) > 0){
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

    public boolean changeStatus(StoreDTO storeDTO) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeStatus",storeDTO) > 0){
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

    public boolean updateInfo(StoreDTO storeDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.updateInfo",storeDTO) > 0){
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

    public boolean addManager(StoreDTO storeDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeStatus",storeDTO) > 0){
                if (sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeUserStatus",storeDTO) > 0) {
                    if (sqlSession.insert("com.screens.store.dao.sql.StoreDAO.addMangerToStore",storeDTO) > 0) {
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

    public boolean removeManager(StoreDTO storeDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeStatus",storeDTO) > 0){
                if (sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeUserStatus",storeDTO) > 0) {
                    if (sqlSession.update("com.screens.store.dao.sql.StoreDAO.removeMangerFromStore",storeDTO) > 0) {
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

    public boolean checkAvailableManager(StoreDTO storeDTO){
        try{
            getSqlSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkAvailableManager",storeDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkAvailableStore(StoreDTO storeDTO){
        try{
            getSqlSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkAvailableStore",storeDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkShelf(StoreDTO storeDTO){
        try{
            getSqlSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkShelf",storeDTO);
            if(resultDAO.getTotalOfRecord() > 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean countStoreById(StoreDTO storeDTO) {
        try{
            getSqlSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.countStoreById",storeDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean countUserById(StoreDTO storeDTO) {
        try {
            getSqlSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.countUserById",storeDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean checkStoreManagerMapping(StoreDTO storeDTO) {
        try{
            getSqlSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkStoreManagerMapping",storeDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }
}
