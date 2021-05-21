package com.screens.manager.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.manager.dto.ManagerDTO;
import com.screens.city.form.CityResponseSupporter;
import com.screens.manager.form.ResponseManagerDetailForm;
import com.screens.manager.form.ResponseManagerListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManagerMapper extends BaseDAO {

    public ManagerMapper(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseManagerListForm getManagers(ManagerDTO managerDTO){
        return sqlSession.selectOne("ManagerDAO.getManagers", managerDTO);
    }

    public ResponseManagerDetailForm getManagerDetail(ManagerDTO managerDTO){
        return sqlSession.selectOne( "ManagerDAO.getManagerDetail",managerDTO);
    }

    public boolean createManager(ManagerDTO managerDTO){
        if(sqlSession.insert("ManagerDAO.insertManger", managerDTO) > 0){
            sqlSession.commit(true);
            return true;
        }
        return false;
    }

    public int countRecordLikeUserName(ManagerDTO managerDTO){
        ManagerDTO resultDAO = sqlSession.selectOne("ManagerDAO.getTotalOfManagerLikeUserName", managerDTO);
        return resultDAO.getAffectedRecords();
    }
}
