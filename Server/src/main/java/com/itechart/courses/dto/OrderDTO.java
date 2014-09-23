package com.itechart.courses.dto;

import com.itechart.courses.enums.OrderStatusEnum;

/**
 * Created by User on 21.09.14.
 */
public class OrderDTO {
    private int id;
    private OrderStatusEnum currentState;
    private String russianCurrentState;
    private String statusComment;
    private String orderDescription;
    private int sum;


    private PersonDTO receiveManager;
    private PersonDTO handlerManager;
    private PersonDTO deliveryManager;

    private PersonDTO customer;
    private PersonDTO recipient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatusEnum getCurrentState() {
        return currentState;
    }

    public void setCurrentState(OrderStatusEnum currentState) {
        this.currentState = currentState;
    }

    public String getRussianCurrentState() {
        return russianCurrentState;
    }

    public void setRussianCurrentState(String russianCurrentState) {
        this.russianCurrentState = russianCurrentState;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public PersonDTO getReceiveManager() {
        return receiveManager;
    }

    public void setReceiveManager(PersonDTO receiveManager) {
        this.receiveManager = receiveManager;
    }

    public PersonDTO getHandlerManager() {
        return handlerManager;
    }

    public void setHandlerManager(PersonDTO handlerManager) {
        this.handlerManager = handlerManager;
    }

    public PersonDTO getDeliveryManager() {
        return deliveryManager;
    }

    public void setDeliveryManager(PersonDTO deliveryManager) {
        this.deliveryManager = deliveryManager;
    }

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
}
