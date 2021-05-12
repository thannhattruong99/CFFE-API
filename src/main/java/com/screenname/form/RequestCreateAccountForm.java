package com.screenname.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

public class RequestCreateAccountForm implements Serializable {
//    @Email
    @NotEmpty
    private String email;

    @NotNull(message = "E009") @Size(min=5, max=10, message = "E002")
    private String password;

    @NotNull @Size(min=5, max=10, message = "E002")
    private String confirmPassword;

    @Size(min=2, max=30, message = "E004")
    private String fullname;

    @NotNull(message = "E009") @Min(value = 1, message = "E005") @Max(value = 3, message = "E005")
    private Integer role;

    private Integer status;

    public RequestCreateAccountForm() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname == null ? null : fullname.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}