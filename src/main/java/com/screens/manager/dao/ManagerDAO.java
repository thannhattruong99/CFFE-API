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
            openSession();
            return sqlSession.selectOne("ManagerDAO.getManagers", managerDTO);
        }finally {
            closeSession();
        }
    }

    public ResponseManagerDetailForm getManagerDetail(ManagerDTO managerDTO){
        try {
            openSession();
            return sqlSession.selectOne( "ManagerDAO.getManagerDetail",managerDTO);
        }finally {
            closeSession();
        }
    }

    public boolean createManager(ManagerDTO managerDTO){
        try{
            openSession();
            if(sqlSession.insert("ManagerDAO.insertManger", managerDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public int countRecordLikeUserName(ManagerDTO managerDTO){
        try{
            openSession();
            ManagerDTO resultDAO = sqlSession.selectOne("ManagerDAO.getTotalOfManagerLikeUserName", managerDTO);
            return resultDAO.getAffectedRecords();
        }finally {
            closeSession();
        }
    }

    public boolean updateManagerInformation(ManagerDTO managerDTO){
        try {
            openSession();
            if(sqlSession.update("ManagerDAO.updateManagerInformation", managerDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public boolean resetPassword(ManagerDTO managerDTO){
        try{
            openSession();
            if(sqlSession.update("ManagerDAO.resetPassword", managerDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public String getEmailByUserName(ManagerDTO managerDTO){
        try{
            openSession();
            ManagerDTO resultDAO = sqlSession.selectOne("ManagerDAO.getEmailByUserName", managerDTO);
            return resultDAO.getEmail();
        }finally {
            closeSession();
        }
    }

    public ManagerDTO getStatusIdAndStoreIdByUserName(ManagerDTO managerDTO){
        try{
            openSession();
            return sqlSession.selectOne("ManagerDAO.getStatusIdAndStoreIdByUserName", managerDTO);
        }finally {
            closeSession();
        }
    }

    public boolean updateManagerStatus(ManagerDTO managerDTO){
        try{
            openSession();
            if(sqlSession.update("ManagerDAO.updateManagerStatus", managerDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeSession();
        }
    }

    public boolean checkUserNameAndPassword(ManagerDTO managerDTO){
        try{
            openSession();
            ManagerDTO resultDAO = sqlSession.selectOne("ManagerDAO.checkUserNameAndPassword", managerDTO);
            if( resultDAO == null || resultDAO.getAffectedRecords() <= 0){
                return false;
            }
            return true;
        }finally {
            closeSession();
        }
    }

    public boolean updatePassword(ManagerDTO managerDTO){
        try{
            openSession();
            if(sqlSession.update("ManagerDAO.updatePassword", managerDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeSession();
        }
    }
}