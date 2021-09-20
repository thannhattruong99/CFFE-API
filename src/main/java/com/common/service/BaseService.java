package com.common.service;

import com.authentication.dto.AuthorDTO;
import com.common.form.ResponseCommonForm;
import com.util.MessageConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseService {
    protected static final int DEFAULT_FETCH_NEXT = 15;
    protected static final int ADD_ACTION = 1;
    protected static final int REMOVE_ACTION = 2;
    protected static final int ACTIVE_STATUS = 1;
    protected static final int INACTIVE_STATUS = 2;
    protected static final int PENDING_STATUS = 3;
    protected static final int MANAGER_ROLE = 2;
    protected static final int ADMIN = 1;
    protected static final int MANAGER_WITHIN_STORE = 2;
    protected static final int MANAGER_WITHOUT_STORE = 3;
    protected static final int PASSWORD_LENGTH = 8;
    protected static final int DETECT_HOT_SPOT = 1;
    protected static final int DETECT_EMOTION = 2;
    protected static final boolean IS_DESCENDING = false;
    protected static final String NUMBER_REGEXP = "[0-9]*";
    protected static final String TIME_ZONE_VIETNAMESE = "+07:00";
    protected static final String ACTIVE_STATUS_STR = "ACTIVE";
    protected static final String INACTIVE_STATUS_STR = "INACTIVE";
    protected static final String PENDING_STATUS_STR = "PENDING";
    protected static final String DAY_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
    protected static final String DEFAULT_IMAGE = "https://storage.googleapis.com/capstone_storeage/images/default-image.jpg";
    private static final String DUPLICATE_ERROR_KEY = "Duplicate entry";


    public List<String> catchSqlException(String errorMsg){
        List<String> errorCodes = new ArrayList<>();
        String[] parts = errorMsg.trim().split("'");
        if(parts[0].contains(DUPLICATE_ERROR_KEY)){
            String[] subParts = parts[3].split("_");
            //subParts[1] is error field name
            String errorCode = ServiceSupporter.getMsgCodeByMsgKey(DUPLICATE_ERROR_KEY + " " + subParts[1]);
            if(errorCode != null){
                errorCodes.add(errorCode);
            }
        }else{
            errorCodes.add(MessageConstant.MSG_076);
        }
        return errorCodes;
    }

    public int checkAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            return ADMIN;
        }
        if (StringUtils.isNotEmpty(authorDTO.getStoreId())) {
            return MANAGER_WITHIN_STORE;
        } else {
            return MANAGER_WITHOUT_STORE;
        }
    }

    public void addErrorMessage(ResponseCommonForm response, String messCode) {
        List<String> errorMsg = new ArrayList<>();
        errorMsg.add(messCode);
        response.setErrorCodes(errorMsg);
    }

    public List<String> getError(String errorCode){
        List<String> errorList = new ArrayList<>();
        errorList.add(errorCode);
        return errorList;
    }
}
