package com.screens.category.controller;


import com.common.form.ResponseCommonForm;
import com.screens.category.form.*;
import com.screens.category.service.CategoryService;
import com.util.ResponseSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController("")
@RequestMapping("admin")
@SecurityRequirement(name = "basicAuth")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final String MSG_029 = "MSG-029";
    private static final String MSG_009 = "MSG-009";

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/category")
    public String getCategoryDetail(
            @Validated RequestGetCategoryDetailForm requestForm,
            BindingResult result,
            HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Get Category Detail
        ResponseCategoryDetailForm responseCategoryDetailForm = categoryService.getCategoryDetail(requestForm);
        if(responseCategoryDetailForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_029);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(responseCategoryDetailForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/categories")
    public String getCategoryList(
            @Validated RequestGetCategoryListForm requestForm,
            BindingResult result,
            HttpServletRequest request){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        ResponseCategoryListForm res = categoryService.getCategoryList(requestForm);
        if(res == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        return ResponseSupporter.responseResult(res);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/category/create")
    public String createCategory(
            @Validated @RequestBody RequestCreateCategoryForm requestForm,
            BindingResult result,
            HttpServletRequest request) throws IOException {
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        ResponseCommonForm rs = categoryService.createCategory(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/category/update-status")
    public String changeStatus(@Validated @RequestBody RequestChangeCategoryStatusForm requestForm,
                               BindingResult result,
                               HttpServletRequest request){

        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        ResponseCommonForm rs = categoryService.changeStatus(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/category/update")
    public String updateCategoryInfo(@Validated @RequestBody RequestUpdateInfoCategoryForm requestForm,
                                    BindingResult result,
                                     HttpServletRequest request){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        ResponseCommonForm responseForm = categoryService.updateCategoryInfo(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        return ResponseSupporter.responseResult(true);
    }

}
