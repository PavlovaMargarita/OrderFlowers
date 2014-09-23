package com.itechart.courses.dto;

import java.util.List;

/**
 * Created by Admin on 21.09.2014.
 */
public class PageableContactDTO {

    private List<ContactDTO> pageableContacts;

    private int totalCount;

    public PageableContactDTO(List<ContactDTO> pageableContacts, int totalCount) {
        this.pageableContacts = pageableContacts;
        this.totalCount = totalCount;
    }

    public List<ContactDTO> getPageableContacts() {
        return pageableContacts;
    }

    public void setPageableContacts(List<ContactDTO> pageableContacts) {
        this.pageableContacts = pageableContacts;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
