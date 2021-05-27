package com.screens.stack.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.stack.dto.StackDTO;
import com.screens.stack.form.ResponseStackDetailForm;
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
}
