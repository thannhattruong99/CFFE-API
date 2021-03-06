package com.screens.manager.form;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;

public class RequestUpdateManagerForm {
    @Size(min = 1, max = 30, message = "MSG-009")
    private String userName;
    @NotEmpty(message = "MSG-014")
    @Size(min = 1, max = 100, message = "MSG-015")
    private String fullName;
    @Pattern(regexp = "\\d{9,12}", message = "MSG-052")
    private String identifyCard;
    @NotEmpty(message = "MSG-044")
    @Pattern(regexp = "[0]?[0-9]{9,12}", message = "MSG-042")
    private String phone;
    @NotEmpty(message = "MSG-044")
    @Email(message = "MSG-045")
    private String email;
    @Pattern(regexp = "^\\d\\d\\d\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01]) (00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9]):([0-9]|[0-5][0-9])", message = "MSG-073")
    @NotEmpty(message = "MSG-047")
    private String birthDate;
    @Min(value= 0, message = "MSG-054")
    @Max(value = 2, message = "MSG-054")
    private int gender;
    @Nullable
    private String imageURL;
    @NotEmpty(message = "MSG-036")
    @Size(min = 1, max = 250, message = "MSG-037")
    private String address;
    @NumberFormat
    private int districtId;

    public RequestUpdateManagerForm() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentifyCard() {
        return identifyCard;
    }

    public void setIdentifyCard(String identifyCard) {
        this.identifyCard = identifyCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Nullable
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(@Nullable String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
