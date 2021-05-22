package com.common.service;

import java.util.ArrayList;
import java.util.List;

public class BaseService {
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
