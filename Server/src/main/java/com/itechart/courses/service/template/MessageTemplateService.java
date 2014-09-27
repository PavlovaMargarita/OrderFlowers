package com.itechart.courses.service.template;

import com.itechart.courses.enums.Roles;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface MessageTemplateService {
    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN})
    public List showTemplate();
}
