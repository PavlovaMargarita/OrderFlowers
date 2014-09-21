package com.itechart.courses.enums;

public enum OrderStatusEnum {
    NEW ("Новый"),
    ADOPTED ("Принят"),
    IN_PROCESSING ("В обработке"),
    READY_FOR_SHIPPING ("Готов к доставке"),
    SHIPPING ("Доставка"),
    CAN_NOT_BE_MADE ("Не может быть выполнен"),
    CANCELED ("Отменен"),
    CLOSED ("Закрыт");

    OrderStatusEnum(String russianStatus){
        this.russianStatus = russianStatus;
    }

    private String russianStatus;

    public String toRussianStatus(){
        return this.russianStatus;
    }
}
