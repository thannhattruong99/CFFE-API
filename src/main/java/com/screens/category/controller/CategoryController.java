package com.screens.category.controller;


import com.screens.category.form.RequestGetCategoryDetailForm;
import com.screens.category.form.ResponseCategoryDetailForm;
import com.screens.category.service.CategoryService;
import com.screens.stack.form.RequestGetStackDetailForm;
import com.screens.stack.form.ResponseStackDetailForm;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final String MSG_029 = "MSG-029";

    @GetMapping(value = "/admin/manager/category")
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

}
