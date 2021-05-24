package com.screens.store.controller;

import com.screens.store.form.*;
import com.screens.store.service.StoreService;
import com.util.ImageHelper;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        ResponseStoreListForm responseStoreListForm = storeService.getStoreList(requestForm);
        if(responseStoreListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.resonpseResult(responseStoreListForm);
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

    @PostMapping(value = "/createStore")
    public String createStore(Model model,
                               @Validated @RequestBody RequestCreateStoreForm requestForm,
                               BindingResult result) throws IOException {
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        // TODO: insert img
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        String uploadDir = "/store-img/" + "fakeid";
//        ImageHelper.saveFile(uploadDir,fileName,multipartFile);

        // Do Get/Search Store
        int rs = storeService.createStore(requestForm);
        if(rs == 0){
            List<String> errorCodes = new ArrayList<>();
                errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.resonpseResult(rs);
    }
}
