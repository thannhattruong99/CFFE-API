package com.screens.camera.form;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RequestUpdateCameraForm {
    @NotEmpty(message = "MSG-024")
    private String cameraId;
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

    public RequestUpdateCameraForm() {
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
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

}
