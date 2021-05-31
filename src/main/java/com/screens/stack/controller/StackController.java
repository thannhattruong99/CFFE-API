package com.screens.stack.controller;

import com.common.form.ResponseCommonForm;
import com.screens.stack.form.*;
import com.screens.stack.service.StackService;
import com.screens.store.form.RequestChangeStoreStatusForm;
import com.screens.store.form.RequestGetStoreListForm;
import com.screens.store.form.ResponseStoreListForm;
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
        return ResponseSupporter.resonpseResult(responseStackDetailForm);
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
        return ResponseSupporter.resonpseResult(responseStackListForm);
    }

    @PostMapping(value = "/admin/manager/store/shelf/stack/change-product")
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
        return ResponseSupporter.resonpseResult(true);
    }


}
