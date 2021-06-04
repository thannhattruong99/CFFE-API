package com.screens.camera.service;

import com.common.form.ResponseCommonForm;
import com.common.service.BaseService;
import com.screens.camera.dao.mapper.CameraMapper;
import com.screens.camera.dto.CameraDTO;
import com.screens.camera.form.*;
import com.screens.manager.service.ManagerService;
import com.util.StringHelper;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CameraService extends BaseService {
    private static final Logger logger = LoggerFactory.getLogger(CameraService.class);
    private static final String MSG_020 = "MSG-020";
    @Autowired
    private CameraMapper cameraMapper;

    public ResponseAvailableCameraListForm getAvailableCameraList(RequestAvailableCameraListForm requestForm){
        ResponseAvailableCameraListForm responseForm = null;
        CameraDTO cameraDTO = new CameraDTO();
        convertRequestAvailableCameraListFormToCameraDTO(requestForm, cameraDTO);

        try{
            responseForm = cameraMapper.getAvailableCameraList(cameraDTO);
        }catch (PersistenceException e){
            logger.error("Error at CameraService: " + e.getMessage());
        }

        return responseForm;
    }

    public ResponseCameraListForm getCameraList(RequestCameraListForm requestForm){
        ResponseCameraListForm responseForm = new ResponseCameraListForm();
        CameraDTO cameraDTO = new CameraDTO();
        convertRequestCameraListFormToCameraDTO(requestForm, cameraDTO);
        try {
            responseForm = cameraMapper.getCameraList(cameraDTO);
        }catch (PersistenceException e){
            logger.error("Error at CameraService: " + e.getMessage());
        }

        return responseForm;
    }

    public ResponseCommonForm createCamera(RequestCreateCameraForm requestForm){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        CameraDTO cameraDTO = new CameraDTO();
        convertRequestCreateCameraFormToCameraDTO(requestForm, cameraDTO);
        try{
            cameraMapper.createCamera(cameraDTO);
        }catch (PersistenceException e){
            logger.error("Error at CameraService: " + e.getMessage());
            responseForm.setErrorCodes(catchSqlException(e.getMessage()));
        }

        return responseForm;

    }

    public ResponseCommonForm updateCamera(RequestUpdateCameraForm requestForm){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        CameraDTO cameraDTO = new CameraDTO();
        convertRequestUpdateCameraFormToCameraDTO(requestForm, cameraDTO);
        try {
            if(!cameraMapper.updateCamera(cameraDTO)){
                List<String> errorCodes = new ArrayList<>();
                errorCodes.add(MSG_020);
                responseForm.setErrorCodes(errorCodes);
            }
        }catch (PersistenceException e){
            logger.error("Error at CameraService: " + e.getMessage());
            responseForm.setErrorCodes(catchSqlException(e.getMessage()));
        }
        return responseForm;
    }

    public ResponseCommonForm updateCameraStatus(RequestUpdateCameraStatusForm requestForm){
        CameraDTO cameraDTO = new CameraDTO();
        convertRequestUpdateCameraStatusFormToCameraDTO(requestForm, cameraDTO);
        ResponseCommonForm responseForm = checkUpdateCameraStatusBusiness(cameraDTO);
        if(responseForm.getErrorCodes() == null){
            try {
                cameraMapper.updateStatus(cameraDTO);
            }catch (PersistenceException e){
                logger.error("Error at CameraService: " + e.getMessage());
                responseForm.setErrorCodes(catchSqlException(e.getMessage()));
            }
        }
        return responseForm;
    }

    public ResponseCameraDetailForm getCameraDetail(RequestCameraDetailForm requestForm){
        ResponseCameraDetailForm responseForm = new ResponseCameraDetailForm();
        CameraDTO cameraDTO = new CameraDTO();
        convertRequestCameraDetailFormToCameraDTO(requestForm, cameraDTO);
        try {
            responseForm = cameraMapper.getCameraDetailById(cameraDTO);
        }catch (PersistenceException e){
            logger.error("Error at CameraService: " + e.getMessage());
        }
        return responseForm;
    }

    private void convertRequestAvailableCameraListFormToCameraDTO(RequestAvailableCameraListForm requestForm, CameraDTO cameraDTO){
        if(!StringHelper.isNullOrEmpty(requestForm.getCameraName())){
            cameraDTO.setCameraName(requestForm.getCameraName());
        }

        if(requestForm.getPageNum() > 0){
            cameraDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }

        cameraDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        if(requestForm.getFetchNext() > 0){
            cameraDTO.setFetchNext(requestForm.getFetchNext());
        }
        cameraDTO.setTypeDetect(requestForm.getTypeDetect());
    }

    private void convertRequestCameraListFormToCameraDTO(RequestCameraListForm requestForm, CameraDTO cameraDTO){
        if(!StringHelper.isNullOrEmpty(requestForm.getStoreId())){
            cameraDTO.setStoreId(requestForm.getStoreId());
        }
        if(!StringHelper.isNullOrEmpty(requestForm.getCameraName())){
            cameraDTO.setCameraName(requestForm.getCameraName());
        }

        if(requestForm.getPageNum() > 0){
            cameraDTO.setOffSet((requestForm.getPageNum() - 1) * requestForm.getFetchNext());
        }

        cameraDTO.setFetchNext(DEFAULT_FETCH_NEXT);
        if(requestForm.getFetchNext() > 0){
            cameraDTO.setFetchNext(requestForm.getFetchNext());
        }

        cameraDTO.setStatusId(requestForm.getStatusId());
    }

    private void convertRequestCreateCameraFormToCameraDTO(RequestCreateCameraForm requestForm, CameraDTO cameraDTO){
        cameraDTO.setCameraName(requestForm.getCameraName());
        cameraDTO.setImageURL("image/test");
        cameraDTO.setIpAddress(requestForm.getIpAddress());
        cameraDTO.setRtspString(requestForm.getRtspString());
        cameraDTO.setTypeDetect(requestForm.getTypeDetect());
        cameraDTO.setCreatedTime(TIME_ZONE_VIETNAMESE);
        cameraDTO.setStatusId(PENDING_STATUS);
    }

    private void convertRequestUpdateCameraFormToCameraDTO(RequestUpdateCameraForm requestForm, CameraDTO cameraDTO){
        cameraDTO.setCameraId(requestForm.getCameraId());
        cameraDTO.setCameraName(requestForm.getCameraName());
        cameraDTO.setImageURL("image/test");
        cameraDTO.setIpAddress(requestForm.getIpAddress());
        cameraDTO.setRtspString(requestForm.getRtspString());
        cameraDTO.setUpdatedTime(TIME_ZONE_VIETNAMESE);
    }

    private void convertRequestUpdateCameraStatusFormToCameraDTO(RequestUpdateCameraStatusForm requestForm, CameraDTO cameraDTO){
        cameraDTO.setCameraId(requestForm.getCameraId());
        cameraDTO.setStatusId(requestForm.getStatusId());
        if(!StringHelper.isNullOrEmpty(requestForm.getReasonInactive())){
            cameraDTO.setReasonInactive(requestForm.getReasonInactive());
        }
        cameraDTO.setUpdatedTime(TIME_ZONE_VIETNAMESE);
    }

    private ResponseCommonForm checkUpdateCameraStatusBusiness(CameraDTO cameraDTO){
        ResponseCommonForm responseForm = new ResponseCommonForm();
        CameraDTO resultDAO = cameraMapper.countCameraById(cameraDTO);
        if(resultDAO == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_020);
            responseForm.setErrorCodes(errorCodes);
        }else if(resultDAO.getStatusId() == ACTIVE_STATUS){
            ArrayList<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_076);
            responseForm.setErrorCodes(errorCodes);

        }else if(cameraDTO.getStatusId() == INACTIVE_STATUS && StringHelper.isNullOrEmpty(cameraDTO.getReasonInactive())){
                List<String> errorCodes = new ArrayList<>();
                errorCodes.add(MSG_066);
                responseForm.setErrorCodes(errorCodes);
        }
        return responseForm;
    }

    private void convertRequestCameraDetailFormToCameraDTO(RequestCameraDetailForm requestForm, CameraDTO cameraDTO){
        cameraDTO.setStoreId(requestForm.getStoreId());
        cameraDTO.setCameraId(requestForm.getCameraId());
    }
}
