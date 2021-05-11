package com.screenname.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class AccountForm {
    @NotEmpty @Email
    private String email;

    private String password;

    @Size(min=2, max=30)
    private String fullname;

    @NotNull @Min(1) @Max(3)
    private Integer role;

    @NotNull @Min(1) @Max(3)
    private Integer status;

    public AccountForm(String email, String password, String fullname, Integer role, Integer status) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
        this.status = status;
    }

    public AccountForm() {
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
}