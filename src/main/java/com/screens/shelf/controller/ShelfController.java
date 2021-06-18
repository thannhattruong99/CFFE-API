package com.screens.shelf.controller;

import com.common.form.ResponseCommonForm;
import com.common.form.UploadFileResponse;
import com.screens.shelf.dto.StockTransaction;
import com.screens.shelf.form.*;
import com.screens.shelf.service.ShelfService;
import com.screens.shelf.service.StockTransactionService;
import com.util.DocumentStorageHelper;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("admin/manager/store")
public class ShelfController {
    private static final String MSG_009 = "MSG-009";

    @Autowired
    private ShelfService shelfService;

    @RequestMapping(value = "/shelves", method = RequestMethod.GET)
    public String getShelves(@Validated RequestShelfListForm requestForm,
                             BindingResult result){

        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseShelfListForm responseForm = shelfService.getShelfList(requestForm);

        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        return ResponseSupporter.responseResult(responseForm);
    }

    @RequestMapping(value = "/shelf", method = RequestMethod.GET)
    public String getShelfDetail(@Validated RequestShelfDetailForm requestForm, BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseShelfDetailForm responseForm = shelfService.getShelfDetail(requestForm);
        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        return ResponseSupporter.responseResult(responseForm);
    }

    @RequestMapping(value = "/shelf/create", method = RequestMethod.POST)
    public String createShelf(@Validated @RequestBody RequestCreateShelfForm requestForm,
                              BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseResult(result);
        }

        ResponseCommonForm responseForm = shelfService.createShelf(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        return ResponseSupporter.responseResult(true);
    }

    @RequestMapping(value = "/shelf/update", method = RequestMethod.POST)
    public String updateShelf(@Validated @RequestBody RequestUpdateShelfForm requestForm,
                              BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseCommonForm responseForm = shelfService.updateShelf(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.responseResult(true);
    }

    @RequestMapping(value = "/shelf/update-status", method = RequestMethod.POST)
    public String updateStatus(@Validated @RequestBody RequestUpdateShelfStatusForm requestForm, BindingResult result){

        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        ResponseCommonForm responseForm = shelfService.updateShelfStatus(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        return ResponseSupporter.responseResult(true);
    }

    @RequestMapping(value = "/shelf/change-shelf-camera", method = RequestMethod.POST)
    public String changeShelfCamera(@Validated @RequestBody RequestChangeShelfCameraForm requestForm,
                                    BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseResult(result);
        }

        ResponseCommonForm responseForm = shelfService.changeShelfCamera(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.responseResult(true);
    }

    @PostMapping("/shelf/uploadVideo")
    public UploadFileResponse uploadFile2(@RequestParam("file") MultipartFile file,
                                          @RequestParam("userId") Integer UserId,
                                          @RequestParam("docType") String docType) {

        String fileName = shelfService.storeFile(file, UserId, docType);
        return new UploadFileResponse(fileName, fileName,
                file.getContentType(), file.getSize());
    }

    @Autowired
    StockTransactionService stockTransactionService;

    @GetMapping( value = "test-sse", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<StockTransaction> stockTransactionEvents(){
        return stockTransactionService.getStockTransactions();
    }
}
