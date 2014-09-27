package com.itechart.courses.service.email;

import com.itechart.courses.dto.EmailDTO;
import com.itechart.courses.enums.Roles;
import org.springframework.security.access.annotation.Secured;

public interface EmailService {
    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN})
    public void sendEmail(EmailDTO email);
}
