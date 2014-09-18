package com.itechart.courses.dto;

import java.sql.Date;

/**
 * Created by Margarita on 18.09.2014.
 */
public class OrderDTO {

    private String orderDescription;
    private Integer sum;
    private Date date;

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
