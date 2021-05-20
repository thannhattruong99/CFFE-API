package com.screens.store.controller;

import com.screens.store.form.RequestGetStoreDetailForm;
import com.screens.store.form.RequestGetStoreListForm;
import com.screens.store.form.ResponseStoreDetailForm;
import com.screens.store.form.ResponseStoreListForm;
import com.screens.store.service.StoreService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StoreController {

    @Autowired
    StoreService storeService;

    private static final String MSG_009 = "MSG-009";
    private static final String MSG_035 = "MSG-035";

    @PostMapping(value = "/getStores")
    public String getStoreList(Model model,
                               @Validated @RequestBody RequestGetStoreListForm requestForm,
                               BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Get/Search Store
        ResponseStoreListForm requestGetStoreListForm = storeService.getStoreList(requestForm);
        if(requestGetStoreListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.resonpseResult(requestGetStoreListForm);
    }

    @GetMapping(value = "/getStoreDetail")
    public String getStoreDetail(
                               @Validated RequestGetStoreDetailForm requestForm,
                               BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Get Store Detail
        ResponseStoreDetailForm responseStoreDetailForm = storeService.getStoreDetail(requestForm);
        if(responseStoreDetailForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_035);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.resonpseResult(responseStoreDetailForm);
    }
}
