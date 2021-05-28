package com.common.form;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

import javax.ws.rs.DefaultValue;

public class RequestGetBaseForm {
    @Nullable @DefaultValue("")
    private String searchValue;
    @Nullable @DefaultValue("")
    private String searchField;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int pageNum;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int fetchNext;

    public RequestGetBaseForm() {
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getFetchNext() {
        return fetchNext;
    }

    public void setFetchNext(int fetchNext) {
        this.fetchNext = fetchNext;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

}
