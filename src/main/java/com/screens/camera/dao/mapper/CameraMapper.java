package com.screens.camera.dao.mapper;

import com.common.dao.BaseDAO;
import com.screens.camera.dto.CameraDTO;
import com.screens.camera.form.ResponseAvailableCameraListForm;
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
}
