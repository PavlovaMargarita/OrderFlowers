package com.itechart.courses.dto;

import java.util.*;

/**
 * Created by Margarita on 10.09.2014.
 */
public class DeleteDTO {
    private List<Integer> deleteId;

    public DeleteDTO() {
        deleteId = new ArrayList<Integer>();
    }

    public List<Integer> getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(List deleteId) {
        this.deleteId = deleteId;
    }
}
