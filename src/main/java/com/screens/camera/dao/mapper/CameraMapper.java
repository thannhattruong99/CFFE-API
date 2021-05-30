package com.screens.camera.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.camera.dto.CameraDTO;
import com.screens.camera.form.ResponseAvailableCameraListForm;
import com.screens.camera.form.ResponseCameraListForm;
import com.util.IDBHelper;
import org.springframework.stereotype.Repository;

@Repository
public class CameraMapper extends BaseDAO {
    public CameraMapper(IDBHelper idbHelper) {
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
}
