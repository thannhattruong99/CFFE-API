package com.screens.camera.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

public class RequestCreateCameraForm {
    @NotEmpty(message = "MSG-082") @Size(min = 1, max = 100, message = "MSG-083")
    private String cameraName;
    @Nullable
    private String imageUrl;
    @NotEmpty(message = "MSG-125") @Pattern(regexp = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})|([0-9a-fA-F]{4}\\.[0-9a-fA-F]{4}\\.[0-9a-fA-F]{4})$", message = "MSG-126")
    private String macAddress;
    @NotEmpty(message = "MSG-031") @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$", message = "MSG-032")
    private String ipAddress;
    @NotEmpty(message = "MSG-030")
    private String rtspString;
    @Min(value = 1, message = "MSG-084") @Max(value = 2, message = "MSG-084")
    private int typeDetect;

    public RequestCreateCameraForm() {

    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }



    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRtspString() {
        return rtspString;
    }

    public void setRtspString(String rtspString) {
        this.rtspString = rtspString;
    }

    public int getTypeDetect() {
        return typeDetect;
    }

    public void setTypeDetect(int typeDetect) {
        this.typeDetect = typeDetect;
    }
}
