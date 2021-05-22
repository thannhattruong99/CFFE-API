package com.common.service;

public enum ServiceSupporter {
    MSG_055("Duplicate entry Email","MSG-055"),
    MSG_056("Duplicate entry IdentifyCard","MSG-056"),
    MSG_057("Duplicate entry IPAddress","MSG-057"),
    MSG_058("Duplicate entry RTSP String","MSG-058"),
    MSG_059("Duplicate entry CategoryName","MSG-059"),
    MSG_060("Duplicate entry ProductName","MSG-060"),
    MSG_061("Duplicate entry ShelfName","MSG-061"),
    MSG_062("Duplicate entry StoreName","MSG-062");

    private String msgKey;
    private String msgCode;

    ServiceSupporter(String errorMsgKey, String errorCode) {
        this.msgKey = errorMsgKey;
        this.msgCode = errorCode;
    }

    public static String getMsgCodeByMsgKey(String keyField){
        for (ServiceSupporter col : ServiceSupporter.values()) {
            if(col.msgKey.equalsIgnoreCase(keyField)){
                return col.msgCode;
            }
        }
        return null;
    }

    public String getErrorMsgKey() {
        return msgKey;
    }

    public void setErrorMsgKey(String errorMsgKey) {
        this.msgKey = errorMsgKey;
    }

    public String getErrorCode() {
        return msgCode;
    }

    public void setErrorCode(String errorCode) {
        this.msgCode = errorCode;
    }
}
