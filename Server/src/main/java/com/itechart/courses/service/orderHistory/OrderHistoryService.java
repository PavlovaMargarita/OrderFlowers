package com.itechart.courses.service.orderHistory;

/**
 * Created by Margarita on 26.09.2014.
 */
import com.itechart.courses.dto.PageableOrderHistoryDTO;

import java.util.*;
public interface OrderHistoryService {
    public PageableOrderHistoryDTO readOrderHistory(int first, int count);
}
