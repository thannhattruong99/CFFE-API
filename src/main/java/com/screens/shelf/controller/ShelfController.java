package com.screens.shelf.controller;

import com.screens.shelf.form.RequestShelfDetailForm;
import com.screens.shelf.form.RequestShelfListForm;
import com.screens.shelf.form.ResponseShelfDetailForm;
import com.screens.shelf.form.ResponseShelfListForm;
import com.screens.shelf.service.ShelfService;
import com.util.ResponseSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("admin/store")
public class ShelfController {
    private static final String MSG_009 = "MSG-009";

    @Autowired
    private ShelfService shelfService;

    @RequestMapping(value = "/shelves", method = RequestMethod.GET)
    public String getShelves(@Validated RequestShelfListForm requestForm,
                             BindingResult result){

        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseShelfListForm responseForm = shelfService.getShelfList(requestForm);

        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        return ResponseSupporter.resonpseResult(responseForm);
    }

    @RequestMapping(value = "/shelf", method = RequestMethod.GET)
    public String getShelfDetail(@Validated RequestShelfDetailForm requestForm, BindingResult result){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        ResponseShelfDetailForm responseForm = shelfService.getShelfDetail(requestForm);
        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        return ResponseSupporter.resonpseResult(responseForm);
    }

//    @RequestMapping(value = "/shelf/update", method = RequestMethod.POST)
//    public String updateShelf(@Validated @RequestBody )
}
