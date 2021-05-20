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

    public int createStore(StoreDTO storeDTO) {
        System.out.println("aaaaaaaaaaaaaaaaaa"+ storeDTO.getAddress());
        if(sqlSession.insert("com.screens.store.dao.sql.StoreDAO.createStore",storeDTO) > 0){
            this.sqlSession.commit();
            return 1;
        }
        return 0;
    }
}
