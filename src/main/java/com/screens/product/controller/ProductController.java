package com.screens.product.controller;

import com.screens.product.form.RequestGetProductDetailForm;
import com.screens.product.form.ResponseProductDetailForm;
import com.screens.product.service.ProductService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final String MSG_023 = "MSG-023";

    @GetMapping(value = "/admin/product")
    public String getProductDetail(
            @Validated RequestGetProductDetailForm requestForm,
            BindingResult result){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Get Product Detail
        ResponseProductDetailForm res = productService.getProductDetail(requestForm);
        if(res == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_023);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.resonpseResult(res);
    }

}
