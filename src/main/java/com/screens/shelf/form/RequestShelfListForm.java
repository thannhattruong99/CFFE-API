package com.screens.shelf.form;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;

public class RequestShelfListForm implements Serializable {
    @NotEmpty(message = "MSG-041")
    @Size(max = 30, message = "MSG-041")
    private String userName;
    @NotEmpty(message = "MSG-069")
    private String storeId;
    @Nullable @DefaultValue("")
    @Size(min = 0, max = 100, message = "MSG-009")
    private String shelfName;
    @Min(value = 0, message = "MSG-009") @Max(value = 3, message = "MSG-009")
    private int statusId;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int pageNum;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int fetchNext;

    public RequestShelfListForm() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getFetchNext() {
        return fetchNext;
    }

    public void setFetchNext(int fetchNext) {
        this.fetchNext = fetchNext;
    }
}