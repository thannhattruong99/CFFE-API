package com.screens.manager.dao;

import com.common.dao.BaseDAO;
import com.screens.manager.dto.ManagerDTO;
import com.screens.manager.form.ResponseManagerDetailForm;
import com.screens.manager.form.ResponseManagerListForm;
import com.util.IDBHelper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerDAO extends BaseDAO {

    public ManagerDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseManagerListForm getManagers(ManagerDTO managerDTO){
        try {
            getSqlSession();
            return sqlSession.selectOne("ManagerDAO.getManagers", managerDTO);
        }finally {
            commit();
        }
    }

    public ResponseManagerDetailForm getManagerDetail(ManagerDTO managerDTO){
        try {
            getSqlSession();
            return sqlSession.selectOne( "ManagerDAO.getManagerDetail",managerDTO);
        }finally {
            commit();
        }
    }

    public boolean createManager(ManagerDTO managerDTO) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.insert("ManagerDAO.insertManger", managerDTO) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public int countRecordLikeUserName(ManagerDTO managerDTO){
        try{
            getSqlSession();
            ManagerDTO resultDAO = sqlSession.selectOne("ManagerDAO.getTotalOfManagerLikeUserName", managerDTO);
            return resultDAO.getAffectedRecords();
        }finally {
            commit();
        }
    }

    public boolean updateManagerInformation(ManagerDTO managerDTO) throws PersistenceException{
        try {
            getSqlSession();
            if(sqlSession.update("ManagerDAO.updateManagerInformation", managerDTO) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public boolean resetPassword(ManagerDTO managerDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.update("ManagerDAO.resetPassword", managerDTO) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public String getEmailByUserName(ManagerDTO managerDTO){
        try{
            getSqlSession();
            ManagerDTO resultDAO = sqlSession.selectOne("ManagerDAO.getEmailByUserName", managerDTO);
            return resultDAO.getEmail();
        }finally {
            commit();
        }
    }

    public ManagerDTO getStatusIdAndStoreIdByUserName(ManagerDTO managerDTO){
        try{
            getSqlSession();
            return sqlSession.selectOne("ManagerDAO.getStatusIdAndStoreIdByUserName", managerDTO);
        }finally {
            commit();
        }
    }

    public boolean updateManagerStatus(ManagerDTO managerDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.update("ManagerDAO.updateManagerStatus", managerDTO) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }

    public boolean checkUserNameAndPassword(ManagerDTO managerDTO){
        try{
            getSqlSession();
            ManagerDTO resultDAO = sqlSession.selectOne("ManagerDAO.checkUserNameAndPassword", managerDTO);
            if( resultDAO == null || resultDAO.getAffectedRecords() <= 0){
                return false;
            }
            return true;
        }finally {
            commit();
        }
    }

    public boolean updatePassword(ManagerDTO managerDTO) throws PersistenceException{
        try{
            getSqlSession();
            if(sqlSession.update("ManagerDAO.updatePassword", managerDTO) > 0){
                return true;
            }
            return false;
        } catch (PersistenceException persistenceException) {
            this.sqlSession.rollback();
            throw persistenceException;
        } finally {
            commit();
        }
    }
}