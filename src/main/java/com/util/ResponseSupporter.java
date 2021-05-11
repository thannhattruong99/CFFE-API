package com.util;

import com.common.config.CasptoneAPIApplication;
import com.common.dto.ErrorObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.validation.*;

import java.util.*;


public class ResponseSupporter {
    private static final String errorMsgPath = "";
    private static Map<String, String> errorMsg;

    public static AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(CasptoneAPIApplication.Config.class);

    public ResponseSupporter(Map<String, String> errorMsg) {
        ResponseSupporter.errorMsg = errorMsg;
    }


    public static String responseResult(Object object){
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(object);
        }catch (JsonProcessingException exception){
            System.out.println("Parsing error: " + exception.getMessage());
            return exception.getMessage();
        }
    }

    public static List<ErrorObject> mappingErrorCodeAndMsg(List<String> errorCodes){
        List<ErrorObject> errorObjectList = null;
        if(errorCodes != null && errorCodes.size() > 0){
            errorObjectList = new ArrayList<>();
            for (String err: errorCodes) {
//                errorCodeMappingMsg.put(err, context.getMessage(err, null, Locale.getDefault()));
                errorObjectList.add(new ErrorObject(err, context.getMessage(err, null, Locale.getDefault())));
            }

        }
        return errorObjectList;
    }

    public static List<ErrorObject> mappingErrorCodeAndMsg(BindingResult result){
        List<ErrorObject> errorObjectList = null;
        if(result.hasErrors()){

            errorObjectList = new ArrayList<>();

            for (Object object : result.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    errorObjectList.add(new ErrorObject(fieldError.getDefaultMessage(),
                            context.getMessage(fieldError.getDefaultMessage(), null, Locale.getDefault())));
                }
            }

        }
        return errorObjectList;
    }

    private static String parseObjectToJson(Object object){
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(object);
        }catch (JsonProcessingException exception){
            return exception.getMessage();
        }
    }
}
