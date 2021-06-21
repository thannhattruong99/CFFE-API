package com.screens.manager.dao;

import com.common.dao.BaseDAO;
import com.screens.manager.dto.ManagerDTO;
import com.screens.manager.form.ResponseManagerDetailForm;
import com.screens.manager.form.ResponseManagerListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerDAO extends BaseDAO {

    public ManagerDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseManagerListForm getManagers(ManagerDTO managerDTO){
        try {
            openConnection();
            return sqlSession.selectOne("ManagerDAO.getManagers", managerDTO);
        }finally {
            closeConnection();
        }
    }

    public ResponseManagerDetailForm getManagerDetail(ManagerDTO managerDTO){
        try {
            openConnection();
            return sqlSession.selectOne( "ManagerDAO.getManagerDetail",managerDTO);
        }finally {
            closeConnection();
        }
    }

    public boolean createManager(ManagerDTO managerDTO){
        try{
            openConnection();
            if(sqlSession.insert("ManagerDAO.insertManger", managerDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public int countRecordLikeUserName(ManagerDTO managerDTO){
        try{
            openConnection();
            ManagerDTO resultDAO = sqlSession.selectOne("ManagerDAO.getTotalOfManagerLikeUserName", managerDTO);
            return resultDAO.getAffectedRecords();
        }finally {
            closeConnection();
        }
    }

    public boolean updateManagerInformation(ManagerDTO managerDTO){
        try {
            openConnection();
            if(sqlSession.update("ManagerDAO.updateManagerInformation", managerDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean resetPassword(ManagerDTO managerDTO){
        try{
            openConnection();
            if(sqlSession.update("ManagerDAO.resetPassword", managerDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public String getEmailByUserName(ManagerDTO managerDTO){
        try{
            openConnection();
            ManagerDTO resultDAO = sqlSession.selectOne("ManagerDAO.getEmailByUserName", managerDTO);
            return resultDAO.getEmail();
        }finally {
            closeConnection();
        }
    }

    public ManagerDTO getStatusIdAndStoreIdByUserName(ManagerDTO managerDTO){
        try{
            openConnection();
            return sqlSession.selectOne("ManagerDAO.getStatusIdAndStoreIdByUserName", managerDTO);
        }finally {
            closeConnection();
        }
    }

    public boolean updateManagerStatus(ManagerDTO managerDTO){
        try{
            openConnection();
            if(sqlSession.update("ManagerDAO.updateManagerStatus", managerDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean checkUserNameAndPassword(ManagerDTO managerDTO){
        try{
            openConnection();
            ManagerDTO resultDAO = sqlSession.selectOne("ManagerDAO.checkUserNameAndPassword", managerDTO);
            if( resultDAO == null || resultDAO.getAffectedRecords() <= 0){
                return false;
            }
            return true;
        }finally {
            closeConnection();
        }
    }

    public boolean updatePassword(ManagerDTO managerDTO){
        try{
            openConnection();
            if(sqlSession.update("ManagerDAO.updatePassword", managerDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }
}