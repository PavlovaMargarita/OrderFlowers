package com.itechart.courses.dto;

import java.util.List;

public class PageableOrderHistoryDTO {

    private List<OrderHistoryDTO> pageableData;
    private int totalCount;

    public PageableOrderHistoryDTO(List<OrderHistoryDTO> pageableData, int totalCount) {
        this.pageableData = pageableData;
        this.totalCount = totalCount;
    }

    public List<OrderHistoryDTO> getPageableData() {
        return pageableData;
    }

    public void setPageableData(List<OrderHistoryDTO> pageableData) {
        this.pageableData = pageableData;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
