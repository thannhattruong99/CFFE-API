package com.screens.store.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.store.dto.StoreDTO;
import com.screens.store.form.ResponseCreateStoreForm;
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
}
