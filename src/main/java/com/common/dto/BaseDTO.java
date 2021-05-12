package com.common.dto;

public class BaseDTO {
    private String searchValue;

    private String searchField;

    private String sortField;

    private boolean isAsc;

    private int offSet;

    private int fetchNext;

    public BaseDTO() {
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean asc) {
        isAsc = asc;
    }

    public int getOffSet() {
        return offSet;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }

    public int getFetchNext() {
        return fetchNext;
    }

    public void setFetchNext(int fetchNext) {
        this.fetchNext = fetchNext;
    }
}
