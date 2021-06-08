package com.screens.store.controller;

import com.common.form.ResponseCommonForm;
import com.screens.store.form.RequestGetStoreListByProductForm;
import com.screens.store.form.*;
import com.screens.store.service.DocumentStorageService;
import com.screens.store.service.StoreService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private DocumentStorageService documneStorageService;


    private static final String MSG_009 = "MSG-009";
    private static final String MSG_035 = "MSG-035";

    @GetMapping(value = "/admin/stores")
    public String getStoreList(@Validated RequestGetStoreListForm requestForm,
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
        return ResponseSupporter.responseResult(responseStoreListForm);
    }

    @GetMapping(value = "/admin/stores-by-product")
    public String getStoreListByProduct(@Validated RequestGetStoreListByProductForm requestForm,
                               BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Get/Search Store
        ResponseStoreListForm responseStoreListForm = storeService.getStoreListByProduct(requestForm);
        if(responseStoreListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseStoreListForm);
    }

    @GetMapping(value = "/admin/operation-stores")
    public String getStoreListShort(@Validated RequestGetStoreListShort requestForm,
                               BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Get/Search Store
        ResponseStoreListForm responseStoreListForm = storeService.getStoreListShort(requestForm);
        if(responseStoreListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseStoreListForm);
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
        return ResponseSupporter.responseResult(responseStoreDetailForm);
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
//        FileHelper.saveFile(uploadDir,fileName,multipartFile);

        // Do Create Store
        ResponseCommonForm rs = storeService.createStore(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
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
        return ResponseSupporter.responseResult(true);
    }

    @RequestMapping(value = "/admin/store/update", method = RequestMethod.POST)
    public String updateStoreInfo(@Validated @RequestBody RequestUpdateInfoForm requestForm,
                                  BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Update Infomation Store
        ResponseCommonForm responseForm = storeService.updateStoreInfo(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

//    @RequestMapping(value = "/admin/store/update-analyzed-time", method = RequestMethod.POST)
//    public String updateAnalyzedTime(@Validated @RequestBody RequestUpdateAnalyzedTime requestForm,
//                                  BindingResult result){
//        // Check Validate
//        if(result.hasErrors()){
//            return ResponseSupporter.responseErrorResult(result);
//        }
//        // Do Update Infomation Store
//        ResponseCommonForm responseForm = storeService.updateAnalyzedTime(requestForm);
//        if(responseForm.getErrorCodes() != null){
//            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
//        }
//        // Return result
//        return ResponseSupporter.responseResult(true);
//    }

    @RequestMapping(value = "/admin/store/change-manager", method = RequestMethod.POST)
    public String changeManager(@Validated @RequestBody RequestChangeManager requestForm,
                                     BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Update Infomation Store
        ResponseCommonForm responseForm = storeService.changeManager(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

    @PostMapping("/uploadImage")
    public UploadFileResponse uploadFile(@RequestParam("image") MultipartFile file,
                                         @RequestParam("userId") Integer UserId,
                                         @RequestParam("docType") String docType) {

        String fileName = documneStorageService.storeFile(file, UserId, docType);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

}
