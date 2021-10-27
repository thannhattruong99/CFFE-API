package com.screens.shelf.controller;

import com.authentication.dto.AuthorDTO;
import com.common.form.ResponseCommonForm;
import com.screens.shelf.form.*;
import com.screens.shelf.service.ShelfService;
import com.util.ResponseSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController("")
@RequestMapping("admin/manager/store")
@SecurityRequirement(name = "basicAuth")
public class ShelfController {
    private static final String MSG_009 = "MSG-009";
    private static final String AUTHOR = "AUTHOR";

    @Autowired
    private ShelfService shelfService;

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/shelves", method = RequestMethod.GET)
    public String getShelves(@Valid RequestShelfListForm requestForm,
                             BindingResult result, HttpServletRequest request){

        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute(AUTHOR);

        ResponseShelfListForm responseForm = shelfService.getShelfList(requestForm, authorDTO);

        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        return ResponseSupporter.responseResult(responseForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/shelves-by-storeid", method = RequestMethod.GET)
    public String getShelvesByStoreId(@Valid RequestShelvesByStoreId requestForm,
                             BindingResult result, HttpServletRequest request){

        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute(AUTHOR);

        ResponseShelvesByStoreId responseForm = shelfService.getShelveByStoreId(requestForm);

        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }
        return ResponseSupporter.responseResult(responseForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/shelf", method = RequestMethod.GET)
    public String getShelfDetail(@Valid RequestShelfDetailForm requestForm, BindingResult result,
                                 HttpServletRequest request){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute(AUTHOR);

        ResponseShelfDetailForm responseForm = shelfService.getShelfDetail(requestForm, authorDTO);
        if(responseForm == null){
            List<String> errorCodes = new ArrayList<>();
            errorCodes.add(MSG_009);
            return ResponseSupporter.responseErrorResult(errorCodes);
        }

        return ResponseSupporter.responseResult(responseForm);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/shelf/create", method = RequestMethod.POST)
    public String createShelf(@Valid RequestCreateShelfForm requestForm,
                              BindingResult result, HttpServletRequest request){
        if(result.hasErrors()){
            return ResponseSupporter.responseResult(result);
        }

        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute(AUTHOR);

        ResponseCommonForm responseForm = shelfService.createShelf(requestForm, authorDTO);

        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/shelf/update", method = RequestMethod.POST)
    public String updateShelf(@Valid RequestUpdateShelfForm requestForm,
                              BindingResult result, HttpServletRequest request){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute(AUTHOR);

        ResponseCommonForm responseForm = shelfService.updateShelf(requestForm, authorDTO);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/shelf/update-status", method = RequestMethod.POST)
    public String updateStatus(@Valid RequestUpdateShelfStatusForm requestForm,
                               BindingResult result, HttpServletRequest request){

        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }

        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute(AUTHOR);

        ResponseCommonForm responseForm = shelfService.updateShelfStatus(requestForm, authorDTO);

        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }
        return ResponseSupporter.responseResult(true);
    }

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/shelf/change-shelf-camera", method = RequestMethod.POST)
    public String changeShelfCamera(@Valid RequestChangeShelfCameraForm requestForm,
                                    BindingResult result, HttpServletRequest request){
        if(result.hasErrors()){
            return ResponseSupporter.responseResult(result);
        }

        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute(AUTHOR);

        ResponseCommonForm responseForm = shelfService.changeShelfCamera(requestForm, authorDTO);
        if(responseForm.getErrorCodes() != null){
            return ResponseSupporter.responseErrorResult(responseForm.getErrorCodes());
        }

        return ResponseSupporter.responseResult(true);
    }
}