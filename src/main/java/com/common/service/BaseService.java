package com.common.service;

import com.common.form.ResponseCommonForm;
import com.filter.dto.AuthorDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseService {
    protected  static final int DEFAULT_OFF_SET = 0;

    protected static final int DEFAULT_FETCH_NEXT = 15;
    protected static final boolean IS_DESCENDING = false;
    protected static final String NUMBER_REGEXP = "[0-9]*";
    protected static final String TIME_ZONE_VIETNAMESE = "+07:00";
    protected static final int ADD_ACTION = 1;
    protected static final int REMOVE_ACTION = 2;
    protected static int ACTIVE_STATUS = 1;
    protected static int INACTIVE_STATUS = 2;
    protected static final int PENDING_STATUS = 3;
    protected static final int MANAGER_ROLE = 2;
    protected static final String ACTIVE_STATUS_STR = "ACTIVE";
    protected static final String INACTIVE_STATUS_STR = "INACTIVE";
    protected static final String PENDING_STATUS_STR = "PENDING";
    protected static final String MSG_063 = "MSG-063";
    protected static final String MSG_066 = "MSG-066";
    protected static final String MSG_076 = "MSG-076";
    protected static final String MSG_020 = "MSG-020";
    private static final String DUPLICATE_ERROR_KEY = "Duplicate entry";
    protected static final String DEFAULT_IMAGE = "https://storage.googleapis.com/capstone_storeage/images/default-image.jpg";
    protected static final int ADMIN = 1;
    protected static final int MANAGER_WITHIN_STORE = 2;
    protected static final int MANAGER_WITHOUT_STORE = 3;

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
            errorCodes.add(MSG_076);
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
}
