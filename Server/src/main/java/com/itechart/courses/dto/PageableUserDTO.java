package com.itechart.courses.dto;

import java.util.List;

/**
 * Created by Admin on 25.09.2014.
 */
public class PageableUserDTO {

    private List<UserDTO> pageableData;

    private int totalCount;

    public PageableUserDTO(List<UserDTO> pageableData, int totalCount) {
        this.pageableData = pageableData;
        this.totalCount = totalCount;
    }

    public List<UserDTO> getPageableData() {
        return pageableData;
    }

    public void setPageableData(List<UserDTO> pageableData) {
        this.pageableData = pageableData;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
