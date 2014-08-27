package com.itechart.cources.bl.service.authorization;

import com.itechart.cources.entity.User;


public interface Authorization {
    public User execute(String login, String password);
}
