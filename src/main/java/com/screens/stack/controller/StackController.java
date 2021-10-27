package com.screens.stack.controller;

import com.authentication.dto.AuthorDTO;
import com.common.form.ResponseCommonForm;
import com.screens.stack.dto.RequestGetStackListByProductForm;
import com.screens.stack.form.*;
import com.screens.stack.service.StackService;
import com.util.ResponseSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController("")
@RequestMapping("admin")
@SecurityRequirement(name = "basicAuth")
public class StackController {

    @Autowired
    private StackService stackService;


    private static final String MSG_022 = "MSG-022";
    private static final String MSG_009 = "MSG-009";

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/manager/store/shelf/stack")
    public String getStackDetail(
            @Validated RequestGetStackDetailForm requestForm,
            BindingResult result,
            HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        // Do Get Stack Detail
        ResponseStackDetailForm responseStackDetailForm = stackService.getStackDetail(requestForm,authorDTO);
        if(responseStackDetailForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_022);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseStackDetailForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/manager/store/shelf/stacks-by-shelf")
    public String getStackListByShelf(@Valid RequestGetStackListForm requestForm,
                                        BindingResult result,
                                        HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        // Do Get/Search Store
        ResponseStackListForm responseStackListForm = stackService.getStackListByShelf(requestForm,authorDTO);
        if(responseStackListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseStackListForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/manager/store/shelf/stacks-by-product-store")
    public String getStackListByProductIdStoreId(@Valid RequestGetStackListByProductForm requestForm,
                                      BindingResult result,
                                                 HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        // Do Get/Search Store
        ResponseStackListForm responseStackListForm = stackService.getStackListByProductIdStoreId(requestForm,authorDTO);
        if(responseStackListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseStackListForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/manager/store/shelf/stack/update-product")
    public String changeProduct(@Valid RequestAddProduct requestForm,
                               BindingResult result,
                                HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");

        // Do Change Status Store
        ResponseCommonForm rs = stackService.changeProduct(requestForm,authorDTO);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/manager/store/shelf/stack/update-camera")
    public String changeCamera(@Valid RequestAddCamera requestForm,
                                BindingResult result,
                               HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        // Do Change Status Store
        ResponseCommonForm rs = stackService.changeCamera(requestForm,authorDTO);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/manager/store/shelf/stack/update-status")
    public String updateStatus(@Valid RequestUpdateStatusForm requestForm,
                               BindingResult result,
                               HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        // Do Change Status Store
        ResponseCommonForm rs = stackService.updateStatus(requestForm,authorDTO);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }
}
