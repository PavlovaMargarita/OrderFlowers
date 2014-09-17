package com.itechart.courses.dto;

import java.util.*;

/**
 * Created by Margarita on 10.09.2014.
 */
public class CheckDTO {

    private List<Integer> checkId;

    public CheckDTO() {
        checkId = new ArrayList<Integer>();
    }

    public List<Integer> getCheckId() {
        return checkId;
    }

    public void setCheckId(List<Integer> checkId) {
        this.checkId = checkId;
    }
}
