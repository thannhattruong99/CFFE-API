package com.screens.camera.dao;

import com.common.dao.BaseDAO;
import com.screens.camera.dto.CameraDTO;
import com.screens.camera.form.ResponseAvailableCameraListForm;
import com.screens.camera.form.ResponseCameraDetailForm;
import com.screens.camera.form.ResponseCameraListForm;
import com.util.IDBHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;

@Repository
public class CameraDAO extends BaseDAO {
    public CameraDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseAvailableCameraListForm getAvailableCameraList(CameraDTO cameraDTO){
        try{
            getSqlSession();
            return sqlSession.selectOne("CameraDAO.getAvailableCameraList", cameraDTO);
        }finally {
            commit();
        }
    }

    public ResponseCameraListForm getCameraList(CameraDTO cameraDTO){
        try{
            getSqlSession();
            return sqlSession.selectOne("CameraDAO.getCameraList", cameraDTO);
        }finally {
            commit();
        }
    }

    public boolean createCamera(CameraDTO cameraDTO) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.insert("CameraDAO.createCamera", cameraDTO) > 0){
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

    public boolean updateCamera(CameraDTO cameraDTO) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.update("CameraDAO.updateCamera", cameraDTO) > 0){
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

    public boolean updateStatus(CameraDTO cameraDTO) throws PersistenceException {
        try{
            getSqlSession();
            if(sqlSession.update("CameraDAO.updateStatus", cameraDTO) > 0){
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

    public CameraDTO countCameraById(CameraDTO cameraDTO){
        try {
            getSqlSession();
            return sqlSession.selectOne("CameraDAO.getCameraStatusById", cameraDTO);
        }finally {
            commit();
        }
    }

    public ResponseCameraDetailForm getCameraDetailById(CameraDTO cameraDTO){
        try {
            getSqlSession();
            return sqlSession.selectOne("CameraDAO.getCameraDetailById", cameraDTO);
        }finally {
            commit();
        }
    }

    public String getStoreIdByCamera(CameraDTO cameraDTO) {
        try{
            getSqlSession();
            String storeId;
            storeId = sqlSession.selectOne("CameraDAO.getStoreIdByShelfMapping", cameraDTO);
            if (StringUtils.isEmpty(storeId)){
                storeId = sqlSession.selectOne("CameraDAO.getStoreIdByStackMapping", cameraDTO);
            }
            return storeId;
        }finally {
            commit();
        }
    }
}
