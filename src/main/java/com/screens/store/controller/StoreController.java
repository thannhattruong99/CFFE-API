package com.screens.store.controller;

import com.authentication.dto.AuthorDTO;
import com.common.form.ResponseCommonForm;
import com.screens.store.form.*;
import com.screens.store.service.StoreService;
import com.util.MessageConstant;
import com.util.ResponseSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController("")
@RequestMapping("admin")
@SecurityRequirement(name = "basicAuth")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/stores")
    public String getStoreList(@Valid RequestGetStoreListForm requestForm,
                               BindingResult result,
                               HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");

        // Do Get/Search Store
        ResponseStoreListForm responseStoreListForm = storeService.getStoreList(requestForm,authorDTO);
        if(responseStoreListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MessageConstant.MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseStoreListForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/stores-by-product")
    public String getStoreListByProduct(@Valid RequestGetStoreListByProductForm requestForm,
                               BindingResult result,
                               HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");

        // Do Get/Search Store
        ResponseStoreListForm responseStoreListForm = storeService.getStoreListByProduct(requestForm,authorDTO);
        if(responseStoreListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MessageConstant.MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseStoreListForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/operation-stores")
    public String getStoreListShort(@Valid RequestGetStoreListShort requestForm,
                               BindingResult result,
                                    HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        // Do Get/Search Store
        ResponseStoreListForm responseStoreListForm = storeService.getStoreListShort(requestForm,authorDTO);
        if(responseStoreListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MessageConstant.MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseStoreListForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/store")
    public String getStoreDetail(
                               @Valid RequestGetStoreDetailForm requestForm,
                               BindingResult result,
                               HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        // Do Get Store Detail
        ResponseStoreDetailForm responseStoreDetailForm = storeService.getStoreDetail(requestForm,authorDTO);
        if(responseStoreDetailForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MessageConstant.MSG_035);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        if (responseStoreDetailForm.getErrorCodes() != null) {
            return ResponseSupporter.responseErrorResult(responseStoreDetailForm.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(responseStoreDetailForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/store/create")
    public String createStore(
                               @Valid RequestCreateStoreForm requestForm,
                               BindingResult result,
                               HttpServletRequest request) {
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        // Do Create Store
        ResponseCommonForm rs = storeService.createStore(requestForm,authorDTO);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/store/update-status")
    public String changeStatus(@Valid RequestChangeStoreStatusForm requestForm,
                               BindingResult result,
                               HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        // Do Change Status Store
        ResponseCommonForm rs = storeService.changeStatus(requestForm,authorDTO);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/store/update", method = RequestMethod.POST)
    public String updateStoreInfo(@Valid RequestUpdateInfoForm requestForm,
                                  BindingResult result,
                                  HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        // Do Update Infomation Store
        ResponseCommonForm responseForm = storeService.updateStoreInfo(requestForm,authorDTO);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/store/change-manager", method = RequestMethod.POST)
    public String changeManager(@Valid RequestChangeManager requestForm,
                                     BindingResult result,
                                HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        ResponseCommonForm responseForm = storeService.changeManager(requestForm,authorDTO);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

}
