package com.screens.store.controller;

import com.common.form.ResponseCommonForm;
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

    @PostMapping(value = "/admin/stores")
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

    @GetMapping(value = "/admin/store")
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

    @PostMapping(value = "/admin/store/create")
    public String createStore(
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

        // Do Create Store
        ResponseCommonForm rs = storeService.createStore(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.resonpseResult(true);
    }

    @PostMapping(value = "/admin/store/update-status")
    public String changeStatus(@Validated @RequestBody RequestChangeStoreStatusForm requestForm,
                               BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Change Status Store
        ResponseCommonForm rs = storeService.changeStatus(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.resonpseResult(true);
    }

    @RequestMapping(value = "/admin/store/update", method = RequestMethod.POST)
    public String updateStoreInfo(@Validated @RequestBody RequestUpdateInfoForm requestForm,
                                  BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = storeService.updateStoreInfo(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.resonpseResult(true);
    }
}
