package com.itechart.courses.dto;

import java.util.List;

/**
 * Created by Alex on 21.09.14.
 */
public class OrderDTO {
    private String currentState;
    private List<String> possibleStates;
    private String customer;
    private String orderDescription;
    private int sum;
    private String handlerManager;
    private String receiveManager;
    private String deliveryManager;
    private String recipient;

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public List<String> getPossibleStates() {
        return possibleStates;
    }

    public void setPossibleStates(List<String> possibleStates) {
        this.possibleStates = possibleStates;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public String getHandlerManager() {
        return handlerManager;
    }

    public void setHandlerManager(String handlerManager) {
        this.handlerManager = handlerManager;
    }

    public String getReceiveManager() {
        return receiveManager;
    }

    public void setReceiveManager(String receiveManager) {
        this.receiveManager = receiveManager;
    }

    public String getDeliveryManager() {
        return deliveryManager;
    }

    public void setDeliveryManager(String deliveryManager) {
        this.deliveryManager = deliveryManager;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
