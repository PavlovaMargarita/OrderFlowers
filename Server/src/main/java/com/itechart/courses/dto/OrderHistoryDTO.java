package com.itechart.courses.dto;

import java.sql.Date;

public class OrderHistoryDTO {

    private PersonDTO customer;
    private PersonDTO recipient;
    private Date date;
    private String russianOrderStatus;
    private String comment;

    public PersonDTO getCustomer() {
        return customer;
    }

    public void setCustomer(PersonDTO customer) {
        this.customer = customer;
    }

    public PersonDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(PersonDTO recipient) {
        this.recipient = recipient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRussianOrderStatus() {
        return russianOrderStatus;
    }

    public void setRussianOrderStatus(String russianOrderStatusEnum) {
        this.russianOrderStatus = russianOrderStatusEnum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
