package com.screens.store.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.store.dto.StoreDTO;
import com.screens.store.form.ResponseStoreDetailForm;
import com.screens.store.form.ResponseStoreListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class StoreMapper extends BaseDAO {

    public StoreMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseStoreListForm getStoreList(StoreDTO storeDTO) {
        return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreList",storeDTO);
    }

    public ResponseStoreListForm getStoreListByProduct(StoreDTO storeDTO) {
        return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreListByProduct",storeDTO);
    }

    public ResponseStoreListForm getStoreListShort(StoreDTO storeDTO) {
        return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreListShort",storeDTO);
    }

    public ResponseStoreDetailForm getStoreDetail(StoreDTO storeDTO) {
        return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreDetail",storeDTO);
    }
    public ResponseStoreDetailForm getStoreStatus(StoreDTO storeDTO) {
        return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreStatus",storeDTO);
    }

    public boolean createStore(StoreDTO storeDTO) {
        if(sqlSession.insert("com.screens.store.dao.sql.StoreDAO.createStore",storeDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    public boolean changeStatus(StoreDTO storeDTO) {
        if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeStatus",storeDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    public boolean updateInfo(StoreDTO storeDTO) {
        if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.updateInfo",storeDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    public boolean updateAnalyzedTime(StoreDTO storeDTO) {
        if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.updateAnalyzedTime",storeDTO) > 0){
            this.sqlSession.commit();
            return true;
        }
        return false;
    }

    public boolean addManager(StoreDTO storeDTO) {
        if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeStatus",storeDTO) > 0){
            System.out.println("============== Changed Store Status");
            if (sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeUserStatus",storeDTO) > 0) {
                System.out.println("============== Changed Manager Status");
                if (sqlSession.insert("com.screens.store.dao.sql.StoreDAO.addMangerToStore",storeDTO) > 0) {
                    System.out.println("============== Add new record Mapping table");
                    this.sqlSession.commit();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeManager(StoreDTO storeDTO) {
        if(sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeStatus",storeDTO) > 0){
            System.out.println("============== Changed Store Status");
            if (sqlSession.update("com.screens.store.dao.sql.StoreDAO.changeUserStatus",storeDTO) > 0) {
                System.out.println("============== Changed Manager Status");
                if (sqlSession.update("com.screens.store.dao.sql.StoreDAO.removeMangerFromStore",storeDTO) > 0) {
                    System.out.println("============== Change status record Mapping table (status = 2)");
                    this.sqlSession.commit();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkAvailableManager(StoreDTO storeDTO){
        StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkAvailableManager",storeDTO);
        if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean checkAvailableStore(StoreDTO storeDTO){
        StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkAvailableStore",storeDTO);
        if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean checkShelf(StoreDTO storeDTO){
        StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkShelf",storeDTO);
        if(resultDAO.getTotalOfRecord() > 0){
            return false;
        }
        return true;
    }

    public boolean countStoreById(StoreDTO storeDTO) {
        StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.countStoreById",storeDTO);
        if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean countUserById(StoreDTO storeDTO) {
        StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.countUserById",storeDTO);
        if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }

    public boolean checkStoreManagerMapping(StoreDTO storeDTO) {
        StoreDTO resultDAO = sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.checkStoreManagerMapping",storeDTO);
        if(resultDAO == null || resultDAO.getTotalOfRecord() <= 0){
            return false;
        }
        return true;
    }
}
