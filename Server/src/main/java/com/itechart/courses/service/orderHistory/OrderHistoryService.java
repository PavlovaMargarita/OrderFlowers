package com.itechart.courses.service.orderHistory;

/**
 * Created by Margarita on 26.09.2014.
 */
import com.itechart.courses.dto.PageableOrderHistoryDTO;
import com.itechart.courses.enums.Roles;
import org.springframework.security.access.annotation.Secured;

import java.util.*;
public interface OrderHistoryService {
    @Secured(Roles.ADMIN)
    public PageableOrderHistoryDTO readOrderHistory(int first, int count);
}
