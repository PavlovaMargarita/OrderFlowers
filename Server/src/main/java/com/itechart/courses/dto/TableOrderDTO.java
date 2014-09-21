package com.itechart.courses.dto;

import java.sql.Date;

public class TableOrderDTO {

    private int id;
    private String orderDescription;
    private Integer sum;
    private Date date;
    private ContactDTO customer;
    private ContactDTO recipient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public ContactDTO getCustomer() {
        return customer;
    }

    public void setCustomer(ContactDTO customer) {
        this.customer = customer;
    }

    public ContactDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(ContactDTO recipient) {
        this.recipient = recipient;
    }
}
