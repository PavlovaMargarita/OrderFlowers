package com.itechart.courses.service.orderHistory;

import com.itechart.courses.dto.PageableOrderHistoryDTO;
import com.itechart.courses.enums.Roles;
import org.springframework.security.access.annotation.Secured;

public interface OrderHistoryService {
    @Secured(Roles.ADMIN)
    public PageableOrderHistoryDTO readOrderHistory(int first, int count);
}
