package com.aizz.mindmingle.domain.dto;

import java.io.Serializable;

/**
 * 分页查询基类DTO
 */
public class PageQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer pageNum = 1;
    public Integer pageSize = 10;
    public String keyword;
    public String sortField;
    public String sortOrder;

    public PageQueryDTO() {
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "PageQueryDTO{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", keyword='" + keyword + '\'' +
                ", sortField='" + sortField + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                '}';
    }
}
