package com.itechart.courses.bl.service.authorization;

import com.itechart.courses.entity.User;


public interface Authorization {
    public User execute(String login, String password);
}
