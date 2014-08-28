package com.itechart.courses.bl.service.authorization;

import com.itechart.courses.bl.dao.user.UserDAO;
import com.itechart.courses.bl.dao.user.UserDAOImpl;
import com.itechart.courses.bl.dto.UserDTO;
import com.itechart.courses.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorizationImpl implements Authorization {

    @Autowired
    public UserDTO userDTO;

    @Override
    public UserDTO execute(String login, String password) {
        UserDAO userDAO = UserDAOImpl.getInstance();
        User user = userDAO.readUser(login, password);
        userDTO.setRole(user.getRole());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
}
