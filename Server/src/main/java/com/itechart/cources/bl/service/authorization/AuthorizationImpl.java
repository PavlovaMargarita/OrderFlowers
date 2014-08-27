package com.itechart.cources.bl.service.authorization;

import com.itechart.cources.bl.dao.user.UserDAO;
import com.itechart.cources.bl.dao.user.UserDAOImpl;
import com.itechart.cources.entity.User;



public class AuthorizationImpl implements Authorization {
    @Override
    public User execute(String login, String password) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        User user = userDAO.readUser(login, password);
        return user;
    }
}
