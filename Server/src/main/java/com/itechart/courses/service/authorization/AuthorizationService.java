package com.itechart.courses.service.authorization;

import com.itechart.courses.dto.UserDTO;

public interface AuthorizationService {
    public UserDTO execute(String login, String password);
}
