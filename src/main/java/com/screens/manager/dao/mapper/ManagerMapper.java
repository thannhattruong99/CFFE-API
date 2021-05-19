package com.screens.manager.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.manager.dto.ManagerDTO;
import com.screens.manager.form.ResponseManagerListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerMapper extends BaseDAO {

    public ManagerMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseManagerListForm getManagers(ManagerDTO managerDTO){
        ResponseManagerListForm responseForm = sqlSession.selectOne("ManagerDAO.getManagers", managerDTO);
        return responseForm;
    }
}
