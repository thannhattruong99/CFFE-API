package com.common.form;

import org.springframework.format.annotation.NumberFormat;

public class RequestGetBaseForm {
    private String searchValue;
    private String searchField;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int pageNum;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private int fetchNext;
    private String sortField;
    private boolean isDescending;

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

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public boolean isDescending() {
        return isDescending;
    }

    public void setDescending(boolean descending) {
        isDescending = descending;
    }
}
