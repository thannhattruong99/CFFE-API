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
        return sqlSession.selectOne("CameraDAO.getAvailableCameraList", cameraDTO);
    }

    public ResponseCameraListForm getCameraList(CameraDTO cameraDTO){
        return sqlSession.selectOne("CameraDAO.getCameraList", cameraDTO);
    }

    public boolean createCamera(CameraDTO cameraDTO){
        if(sqlSession.insert("CameraDAO.createCamera", cameraDTO) > 0){
            sqlSession.commit(true);
            return true;
        }
        return false;
    }

    public boolean updateCamera(CameraDTO cameraDTO){
        if(sqlSession.update("CameraDAO.updateCamera", cameraDTO) > 0){
            sqlSession.commit(true);
            return true;
        }
        return false;
    }

    public boolean updateStatus(CameraDTO cameraDTO){
        if(sqlSession.update("CameraDAO.updateStatus", cameraDTO) > 0){
            sqlSession.commit(true);
            return true;
        }
        return false;
    }

    public CameraDTO countCameraById(CameraDTO cameraDTO){
        return sqlSession.selectOne("CameraDAO.getCameraStatusById", cameraDTO);
    }

    public ResponseCameraDetailForm getCameraDetailById(CameraDTO cameraDTO){
        return sqlSession.selectOne("CameraDAO.getCameraDetailById", cameraDTO);
    }

    public String getStoreIdByCamera(CameraDTO cameraDTO) {
        String storeId;
        storeId = sqlSession.selectOne("CameraDAO.getStoreIdByShelfMapping", cameraDTO);
        if (StringUtils.isEmpty(storeId)){
            storeId = sqlSession.selectOne("CameraDAO.getStoreIdByStackMapping", cameraDTO);
        }
        return storeId;
    }
}
