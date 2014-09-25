package com.itechart.courses.enums;

public enum OrderStatusEnum {
    NEW ("новый"),
    ADOPTED ("принят"),
    IN_PROCESSING ("в обработке"),
    READY_FOR_SHIPPING ("готов к доставке"),
    SHIPPING ("доставка"),
    CAN_NOT_BE_MADE ("не может быть выполнен"),
    CANCELED ("отменен"),
    CLOSED ("закрыт");

    OrderStatusEnum(String russianStatus){
        this.russianStatus = russianStatus;
    }

    private String russianStatus;

    public String toRussianStatus(){
        return this.russianStatus;
    }


}
