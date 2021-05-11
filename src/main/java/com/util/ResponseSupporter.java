package com.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.*;

import java.util.*;

public class ResponseErrorMsgSupporter{
    private static final String errorMsgPath = "";
    private static Map<String, String> errorMsg;

    @Autowired
    private static MessageSource messageSource;

    public ResponseErrorMsgSupporter(Map<String, String> errorMsg) {
        ResponseErrorMsgSupporter.errorMsg = errorMsg;
    }

    public List<String> errorMsg(BindingResult result){
        List<String> errorList = new ArrayList<String>();
        String errorCode = "";
        for (Object object : result.getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;

                System.out.println(fieldError.getCode());
                fieldError.getField();
                errorCode += " " + fieldError.getCode();
            }
            String  errorMsg = "";
            if (object instanceof ObjectError) {
                ObjectError objectError = (ObjectError) object;

                System.out.println(objectError.getCode());
                errorMsg += " " + objectError.getCode();
            }
        }
        return errorList;
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

    public static Map<String, String> mappingErrorCodeAndMsg(List<String> errorCodes){
        Map<String, String> result = null;
        if(errorCodes != null && errorCodes.size() > 0){
            result = new HashMap<>();
            for (String err: errorCodes) {
                result.put(err, messageSource.getMessage(err, null, Locale.getDefault()));
            }
        }
        return result;
    }

    private static String parseObjectToJson(Object object){
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(object);
        }catch (JsonProcessingException exception){
            System.out.println("Parsing error: " + exception.getMessage());
            return exception.getMessage();
        }
    }
}
