package com.screens.shelf.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.shelf.dto.ShelfDTO;
import com.screens.shelf.form.ResponseShelfListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class ShelfMapper extends BaseDAO {

    public ShelfMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseShelfListForm getShelfList(ShelfDTO shelfDTO){
        return sqlSession.selectOne("ShelfDAO.getShelves", shelfDTO);
    }
}
