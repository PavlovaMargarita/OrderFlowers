package com.itechart.courses.bl.service.authorization;

import com.itechart.courses.bl.dto.UserDTO;

public interface Authorization {
    public UserDTO execute(String login, String password);
}
