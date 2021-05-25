package com.common.service;

import java.util.ArrayList;
import java.util.List;

public class BaseService {
    protected static final int DEFAULT_FETCH_NEXT = 15;
    protected static final boolean IS_DESCENDING = false;
    protected static final String NUMBER_REGEXP = "[0-9]*";
    protected static final String TIME_ZONE_VIETNAMESE = "+07:00";
    protected static final int PASSWORD_LENGTH = 6;
    protected static final int MANAGER_ROLE = 2;
    protected static int ACTIVE_STATUS = 1;
    protected static int INACTIVE_STATUS = 2;
    protected static final int PENDING_STATUS = 3;
    protected static final String ACTIVE_STATUS_STR = "ACTIVE";
    protected static final String INACTIVE_STATUS_STR = "INACTIVE";
    protected static final String PENDING_STATUS_STR = "ACTIVE";
    protected static final String MSG_063 = "MSG-063";
    private static final String DUPLICATE_ERROR_KEY = "Duplicate entry";

    public List<String> sqlException(String errorMsg){
        List<String> errorCodes = null;
        String[] parts = errorMsg.trim().split("'");
        if(parts[0].contains(DUPLICATE_ERROR_KEY)){
            String[] subParts = parts[3].split("_");
            //subParts[1] is error field name
            String errorCode = ServiceSupporter.getMsgCodeByMsgKey(DUPLICATE_ERROR_KEY + " " + subParts[1]);
            if(errorCode != null){
                errorCodes = new ArrayList<>();
                errorCodes.add(errorCode);
            }
        }
        return errorCodes;
    }


}
