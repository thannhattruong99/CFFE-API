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

    public ResponseStoreDetailForm getStoreDetail(StoreDTO storeDTO) {
        return sqlSession.selectOne("com.screens.store.dao.sql.StoreDAO.getStoreDetail",storeDTO);
    }
}
