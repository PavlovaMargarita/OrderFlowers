package com.itechart.courses.service.order;

import com.itechart.courses.enums.OrderStatusEnum;

import java.util.List;

/**
 * Created by User on 20.09.14.
 */
public interface SequenceOrderStatus {
    public List<OrderStatusEnum> getValues(OrderStatusEnum key);
}
