package com.itechart.courses.dto;

import com.itechart.courses.enums.OrderStatusEnum;

import java.sql.Date;

/**
 * Created by Margarita on 26.09.2014.
 */
public class OrderHistoryDTO {
    private ContactDTO customer;
    private ContactDTO recipient;
    private Date date;
    private OrderStatusEnum orderStatusEnum;
    private String comment;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatusEnum getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public void setOrderStatusEnum(OrderStatusEnum orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
