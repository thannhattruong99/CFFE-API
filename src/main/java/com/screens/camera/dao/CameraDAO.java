package com.screens.camera.dao;

import com.common.dao.BaseDAO;
import com.screens.camera.dto.CameraDTO;
import com.screens.camera.form.ResponseAvailableCameraListForm;
import com.screens.camera.form.ResponseCameraDetailForm;
import com.screens.camera.form.ResponseCameraListForm;
import com.util.IDBHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class CameraDAO extends BaseDAO {
    public CameraDAO(IDBHelper idbHelper) {
        super(idbHelper);
    }

    public ResponseAvailableCameraListForm getAvailableCameraList(CameraDTO cameraDTO){
        try{
            openConnection();
            return sqlSession.selectOne("CameraDAO.getAvailableCameraList", cameraDTO);
        }finally {
            closeConnection();
        }
    }

    public ResponseCameraListForm getCameraList(CameraDTO cameraDTO){
        try{
            openConnection();
            return sqlSession.selectOne("CameraDAO.getCameraList", cameraDTO);
        }finally {
            closeConnection();
        }
    }

    public boolean createCamera(CameraDTO cameraDTO){
        try{
            openConnection();
            if(sqlSession.insert("CameraDAO.createCamera", cameraDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean updateCamera(CameraDTO cameraDTO){
        try{
            openConnection();
            if(sqlSession.update("CameraDAO.updateCamera", cameraDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public boolean updateStatus(CameraDTO cameraDTO){
        try{
            openConnection();
            if(sqlSession.update("CameraDAO.updateStatus", cameraDTO) > 0){
                sqlSession.commit(true);
                return true;
            }
            return false;
        }finally {
            closeConnection();
        }
    }

    public CameraDTO countCameraById(CameraDTO cameraDTO){
        try {
            openConnection();
            return sqlSession.selectOne("CameraDAO.getCameraStatusById", cameraDTO);
        }finally {
            closeConnection();
        }
    }

    public ResponseCameraDetailForm getCameraDetailById(CameraDTO cameraDTO){
        try {
            openConnection();
            return sqlSession.selectOne("CameraDAO.getCameraDetailById", cameraDTO);
        }finally {
            closeConnection();
        }
    }

    public String getStoreIdByCamera(CameraDTO cameraDTO) {
        try{
            openConnection();
            String storeId;
            storeId = sqlSession.selectOne("CameraDAO.getStoreIdByShelfMapping", cameraDTO);
            if (StringUtils.isEmpty(storeId)){
                storeId = sqlSession.selectOne("CameraDAO.getStoreIdByStackMapping", cameraDTO);
            }
            return storeId;
        }finally {
            closeConnection();
        }
    }
}
