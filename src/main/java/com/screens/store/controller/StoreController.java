package com.screens.store.controller;

import com.screens.store.form.RequestGetStoreListForm;
import com.screens.store.form.ResponseStoreListForm;
import com.screens.store.service.StoreService;
import com.util.ResponseSupporter;
import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StoreController {

    @Autowired
    StoreService storeService;

    private static final String MSG_009 = "MSG_009";

    @RequestMapping(value = "/getStores", method = RequestMethod.POST)
    public String getStoreList(Model model,
                               @Validated @RequestBody RequestGetStoreListForm requestForm,
                               BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseStoreListForm requestGetStoreListForm = storeService.getStoreList(requestForm);
        if(requestGetStoreListForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        return ResponseSupporter.resonpseResult(requestGetStoreListForm);

    }
}
