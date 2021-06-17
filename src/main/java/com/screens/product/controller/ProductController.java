package com.screens.product.controller;

import com.common.form.ResponseCommonForm;
import com.screens.product.form.*;
import com.screens.product.service.ProductService;
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
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final String MSG_009 = "MSG-009";
    private static final String MSG_023 = "MSG-023";

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/product")
    public String getProductDetail(
            @Validated RequestGetProductDetailForm requestForm,
            BindingResult result,
            HttpServletRequest request){
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
        return ResponseSupporter.responseResult(res);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/products")
    public String getProductList(
            @Validated RequestGetProductListForm requestForm,
            BindingResult result,
            HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Get Product Detail
        ResponseProductListForm res = productService.getProductList(requestForm);
        if(res == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        // Return result
        return ResponseSupporter.responseResult(res);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/product/create")
    public String createProduct(
            @Validated @RequestBody RequestCreateProductForm requestForm,
            BindingResult result,
            HttpServletRequest request) throws IOException {
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        // Do Create Product
        ResponseCommonForm rs = productService.createProduct(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/product/update-status")
    public String updateStatus(@Validated @RequestBody RequestUpdateStatusProductForm requestForm,
                               BindingResult result,
                               HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Change Status Store
        ResponseCommonForm rs = productService.updateStatus(requestForm);
        if(rs.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(rs.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/product/update")
    public String updateProductInfo(@Validated @RequestBody RequestUpdateInfoProductForm requestForm,
                                  BindingResult result,
                                    HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Update Infomation Stack
        ResponseCommonForm responseForm = productService.updateProductInfo(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/product/update-category")
    public String updateCategory(@Validated @RequestBody RequestUpdateCategoryForm requestForm,
                                    BindingResult result,
                                 HttpServletRequest request){
        // Check Validate
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        // Do Update Category
        ResponseCommonForm responseForm = productService.updateCategory(requestForm);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        // Return result
        return ResponseSupporter.responseResult(true);
    }

}
