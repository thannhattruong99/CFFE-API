package com.screens.video.form;

import java.io.Serializable;
import java.util.List;

public class ResponseGetVideoForm implements Serializable {
    private ResponseCountVideosForm videoCount;
    private ResponseEmotionVideosForm videoEmotion;
    private List<String> errorCodes;

    public ResponseGetVideoForm() {
    }

    public ResponseCountVideosForm getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(ResponseCountVideosForm videoCount) {
        this.videoCount = videoCount;
    }

    public ResponseEmotionVideosForm getVideoEmotion() {
        return videoEmotion;
    }

    public void setVideoEmotion(ResponseEmotionVideosForm videoEmotion) {
        this.videoEmotion = videoEmotion;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}
