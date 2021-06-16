package com.screens.city.controller;

import com.screens.city.form.CityDistrictResponseSupporter;
import com.screens.city.service.CityService;
import com.util.ResponseSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController("")
@RequestMapping("city")
@SecurityRequirement(name = "basicAuth")
public class CityController {
    private static final String MSG_009 = "MSG-009";
    @Autowired
    private CityService cityService;

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/city-district-lst")
    public String getCityAndDistrict(){
        List<CityDistrictResponseSupporter> responseForm = cityService.getCityDistrict();
        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseResult(errorCodes);
        }
        return ResponseSupporter.responseResult(responseForm);
    }
}
