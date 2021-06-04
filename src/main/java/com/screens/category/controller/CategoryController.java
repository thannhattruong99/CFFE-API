package com.screens.category.controller;


import com.common.form.ResponseCommonForm;
import com.screens.category.form.*;
import com.screens.category.service.CategoryService;
import com.screens.stack.form.RequestGetStackDetailForm;
import com.screens.stack.form.ResponseStackDetailForm;
import com.screens.store.form.RequestChangeStoreStatusForm;
import com.screens.store.form.RequestCreateStoreForm;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final String MSG_029 = "MSG-029";
    private static final String MSG_009 = "MSG-009";

    @GetMapping(value = "/admin/category")
    public String getCategoryDetail(
            @Validated RequestGetCategoryDetailForm requestForm,
            BindingResult result){
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
        return ResponseSupporter.resonpseResult(responseCategoryDetailForm);
    }

    @GetMapping(value = "/admin/categories")
    public String getCategoryList(
            @Validated RequestGetCategoryListForm requestForm,
            BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        ResponseCategoryListForm res = categoryService.getCategoryList(requestForm);
        if(res == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        return ResponseSupporter.resonpseResult(res);
    }

    @PostMapping(value = "/admin/category/create")
    public String createCategory(
            @Validated @RequestBody RequestCreateCategoryForm requestForm,
            BindingResult result) throws IOException {
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        ResponseCommonForm rs = categoryService.createCategory(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        return ResponseSupporter.resonpseResult(true);
    }

    @PostMapping(value = "/admin/category/update-status")
    public String changeStatus(@Validated @RequestBody RequestChangeCategoryStatusForm requestForm,
                               BindingResult result){

        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        ResponseCommonForm rs = categoryService.changeStatus(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        return ResponseSupporter.resonpseResult(true);
    }

}
