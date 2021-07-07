package com.screens.store.dao;

import com.common.dao.BaseDAO;
import com.screens.store.dto.StoreDTO;
import com.screens.store.form.ResponseStoreDetailForm;
import com.screens.store.form.ResponseStoreListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class StoreDAO extends BaseDAO {

    public StoreDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseStoreListForm getStoreList(StoreDTO storeDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreList",storeDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseStoreListForm getStoreListByProduct(StoreDTO storeDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreListByProduct",storeDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseStoreListForm getStoreListShort(StoreDTO storeDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreListShort",storeDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseStoreDetailForm getStoreDetail(StoreDTO storeDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreDetail",storeDTO);
        }finally {
            closeSession();
        }
    }
    public ResponseStoreDetailForm getStoreStatus(StoreDTO storeDTO) {
        try{
            openSession();
            return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreStatus",storeDTO);
        }finally {
            closeSession();
        }
    }

    public boolean createStore(StoreDTO storeDTO) {
        try {
            openSession();
            if(sqlSession.insert("com.screens.store.dao.sql.StoreDAO.createStore",storeDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public boolean changeStatus(StoreDTO storeDTO) {
        try{
            openSession();
            if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeStatus",storeDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public boolean updateInfo(StoreDTO storeDTO) {
        try{
            openSession();
            if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.updateInfo",storeDTO) > 0){
                this.sqlSession.commit();
                return true;
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public boolean addManager(StoreDTO storeDTO) {
        try{
            openSession();
            if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeStatus",storeDTO) > 0){
                if (sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeUserStatus",storeDTO) > 0) {
                    if (sqlSession.insert("com.screens.store.dao.sql.StoreDAO.addMangerToStore",storeDTO) > 0) {
                        this.sqlSession.commit();
                        return true;
                    }
                }
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public boolean removeManager(StoreDTO storeDTO) {
        try{
            openSession();
            if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeStatus",storeDTO) > 0){
                if (sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeUserStatus",storeDTO) > 0) {
                    if (sqlSession.update("com.screens.store.dao.sql.StoreDAO.removeMangerFromStore",storeDTO) > 0) {
                        this.sqlSession.commit();
                        return true;
                    }
                }
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public boolean checkAvailableManager(StoreDTO storeDTO){
        try{
            openSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkAvailableManager",storeDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean checkAvailableStore(StoreDTO storeDTO){
        try{
            openSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkAvailableStore",storeDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean checkShelf(StoreDTO storeDTO){
        try{
            openSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkShelf",storeDTO);
            if(resultDAO.getTotalOfRecord() > 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean countStoreById(StoreDTO storeDTO) {
        try{
            openSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.countStoreById",storeDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean countUserById(StoreDTO storeDTO) {
        try {
            openSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.countUserById",storeDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean checkStoreManagerMapping(StoreDTO storeDTO) {
        try{
            openSession();
            StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkStoreManagerMapping",storeDTO);
            if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }
}
