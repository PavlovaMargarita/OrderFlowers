package com.itechart.courses.dto;

import java.util.List;

public class PageableOrderDTO {

    private List<TableOrderDTO> pageableData;

    private int totalCount;

    public PageableOrderDTO(List<TableOrderDTO> pageableData, int totalCount) {
        this.pageableData = pageableData;
        this.totalCount = totalCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<TableOrderDTO> getPageableData() {
        return pageableData;
    }

    public void setPageableData(List<TableOrderDTO> pageableData) {
        this.pageableData = pageableData;
    }
}
