package com.screens.video.controller;

import com.filter.dto.AuthorDTO;
import com.screens.store.form.RequestGetStoreListForm;
import com.screens.store.form.ResponseStoreListForm;
import com.screens.store.service.StoreService;
import com.screens.video.form.RequestGetVideoListForm;
import com.screens.video.form.ResponseCountVideosForm;
import com.screens.video.form.ResponseEmotionVideosForm;
import com.screens.video.form.ResponseGetVideoForm;
import com.screens.video.service.VideoService;
import com.util.MessageConstant;
import com.util.ResponseSupporter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController("")
@RequestMapping("admin")
@SecurityRequirement(name = "basicAuth")
public class VideoController {

    @Autowired
    private VideoService videoService;

    private static final int COUNT_TYPE_VIDEO = 1;
    private static final int EMOTION_TYPE_VIDEO = 2;

    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/videos")
    public String getStoreList(@Validated RequestGetVideoListForm requestForm,
                               BindingResult result,
                               HttpServletRequest request){
        if(result.hasErrors()){
            return ResponseSupporter.responseErrorResult(result);
        }
        AuthorDTO authorDTO = (AuthorDTO) request.getAttribute("AUTHOR");
        ResponseGetVideoForm responseGetVideo = videoService.getVideoList(requestForm,authorDTO);

        if(responseGetVideo.getErrorCodes() == null){
            if (requestForm.getVideoType() == COUNT_TYPE_VIDEO && responseGetVideo.getVideoCount() != null){
                return ResponseSupporter.responseResult(responseGetVideo.getVideoCount());
            }
            if (requestForm.getVideoType() == EMOTION_TYPE_VIDEO && responseGetVideo.getVideoEmotion() != null){
                return ResponseSupporter.responseResult(responseGetVideo.getVideoEmotion());
            }
        } else {
            return ResponseSupporter.responseErrorResult(responseGetVideo.getErrorCodes());
        }

        List<String> errorCodes = new ArrayList<>();
        errorCodes.add(MessageConstant.MSG_009);
        return ResponseSupporter.responseErrorResult(errorCodes);
    }

}
