package com.itechart.courses.dto;

import java.util.List;

public class PageableContactDTO {

    private List<PersonDTO> pageableContacts;
    private int totalCount;

    public PageableContactDTO(int totalCount, List<PersonDTO> pageableContacts) {
        this.totalCount = totalCount;
        this.pageableContacts = pageableContacts;
    }

    public List<PersonDTO> getPageableContacts() {
        return pageableContacts;
    }

    public void setPageableContacts(List<PersonDTO> pageableContacts) {
        this.pageableContacts = pageableContacts;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
