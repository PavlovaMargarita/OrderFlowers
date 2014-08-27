package com.itechart.courses.bl.service.authorization;

import com.itechart.courses.bl.dao.user.UserDAO;
import com.itechart.courses.bl.dao.user.UserDAOImpl;
import com.itechart.courses.entity.User;
import org.springframework.stereotype.Service;


@Service
public class AuthorizationImpl implements Authorization {
    @Override
    public User execute(String login, String password) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        User user = userDAO.readUser(login, password);
        return user;
    }
}
