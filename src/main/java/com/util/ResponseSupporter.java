package com.util;

import com.common.config.CasptoneAPIApplication;
import com.common.dto.ErrorObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ResponseSupporter {

    public static AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(CasptoneAPIApplication.Config.class);

    private static String convertObjectToJSon(Object object){
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(object);
        }catch (JsonProcessingException exception){
            return exception.getMessage();
        }
    }

    public static String responseResult(Object object){
        return convertObjectToJSon(object);
    }

    public static String responseErrorResult(List<String> errorCodes){
        ErrorObject errorObject = null;
        if(errorCodes != null && errorCodes.size() > 0){
            Map<String, String> errorCodeAndMsg = new HashMap<>();
            errorObject = new ErrorObject();
            for (String err: errorCodes) {
                errorCodeAndMsg.put(err, context.getMessage(err, null, Locale.getDefault()));
            }
            errorObject.setErrorCodeAndMsg(errorCodeAndMsg);
        }
        return convertObjectToJSon(errorObject);
    }

    public static String responseErrorResult(BindingResult result){
        ErrorObject errorObject = null;
        if(result.hasErrors()){

            Map<String, String> errorCodeAndMsg = new HashMap<>();
            errorObject = new ErrorObject();

            for (Object object : result.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    errorCodeAndMsg.put(fieldError.getDefaultMessage(),
                            context.getMessage(fieldError.getDefaultMessage(), null, Locale.getDefault()));
                }
            }
            errorObject.setErrorCodeAndMsg(errorCodeAndMsg);
        }
        return convertObjectToJSon(errorObject);
    }
}
