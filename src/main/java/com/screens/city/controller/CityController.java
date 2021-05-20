package com.screens.city.controller;

import com.screens.city.form.CityDistrictResponseSupporter;
import com.screens.city.service.CityService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("city")
public class CityController {
    private static String MSG_009 = "MSG-009";
    @Autowired
    private CityService cityService;

    @GetMapping(value = "/city_district_lst")
    public String getCityAndDisctrict(){
        List<CityDistrictResponseSupporter> responseForm = cityService.getCityDistrict();
        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.resonpseResult(errorCodes);
        }
        return ResponseSupporter.resonpseResult(responseForm);
    }
}
