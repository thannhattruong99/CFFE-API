package com.screens.manager.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class RequestChangePasswordForm {
    @NotEmpty(message = "MSG-001")
    private String userName;
    @NotEmpty(message = "MSG-004")
    private String oldPassword;
    @NotEmpty(message = "MSG-005")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "MSG-008")
    private String newPassword;
    @NotEmpty(message = "MSG-006")
    private String retypePassword;

    public RequestChangePasswordForm() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }
}
