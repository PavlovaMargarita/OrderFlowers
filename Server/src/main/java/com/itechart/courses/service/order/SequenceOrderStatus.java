package com.itechart.courses.service.order;

import com.itechart.courses.enums.OrderStatusEnum;

import java.util.List;

public interface SequenceOrderStatus {
    public List<OrderStatusEnum> getValues(OrderStatusEnum key);
}


