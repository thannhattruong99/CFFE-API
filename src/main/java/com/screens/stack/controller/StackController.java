package com.screens.stack.controller;

import com.common.form.ResponseCommonForm;
import com.screens.stack.dto.RequestGetStackListByProductForm;
import com.screens.stack.form.*;
import com.screens.stack.service.StackService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StackController {

    @Autowired
    private StackService stackService;


    private static final String MSG_022 = "MSG-022";
    private static final String MSG_009 = "MSG-009";

    @GetMapping(value = "/admin/manager/store/shelf/stack")
    public String getStackDetail(
            @Validated RequestGetStackDetailForm requestForm,
            BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Get Stack Detail
        ResponseStackDetailForm responseStackDetailForm = stackService.getStackDetail(requestForm);
        if(responseStackDetailForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_022);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseStackDetailForm);
    }

    @GetMapping(value = "/admin/manager/store/shelf/stacks-by-shelf")
    public String getStackListByShelf(@Validated RequestGetStackListForm requestForm,
                               BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Get/Search Store
        ResponseStackListForm responseStackListForm = stackService.getStackListByShelf(requestForm);
        if(responseStackListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseStackListForm);
    }

    @GetMapping(value = "/admin/manager/store/shelf/stacks-by-product-store")
    public String getStackListByProductIdStoreId(@Validated RequestGetStackListByProductForm requestForm,
                                      BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Get/Search Store
        ResponseStackListForm responseStackListForm = stackService.getStackListByProductIdStoreId(requestForm);
        if(responseStackListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseStackListForm);
    }

    @PostMapping(value = "/admin/manager/store/shelf/stack/update-product")
    public String changeProduct(@Validated @RequestBody RequestAddProduct requestForm,
                               BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Change Status Store
        ResponseCommonForm rs = stackService.changeProduct(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

    @PostMapping(value = "/admin/manager/store/shelf/stack/update-camera")
    public String changeCamera(@Validated @RequestBody RequestAddCamera requestForm,
                                BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Change Status Store
        ResponseCommonForm rs = stackService.changeCamera(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

    @PostMapping(value = "/admin/manager/store/shelf/stack/update-status")
    public String updateStatus(@Validated @RequestBody RequestUpdateStatusForm requestForm,
                               BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Change Status Store
        ResponseCommonForm rs = stackService.updateStatus(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }


}
