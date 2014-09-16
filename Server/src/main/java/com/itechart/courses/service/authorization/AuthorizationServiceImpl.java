package com.itechart.courses.service.authorization;

import com.itechart.courses.dao.user.UserDAO;
import com.itechart.courses.dto.UserDTO;
import com.itechart.courses.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired(required = true)
    UserDAO userDAO;

    @Override
    public UserDTO execute(String login, String password) {
        UserDTO userDTO = null;
        User user = userDAO.readUser(login, password);
        if (user != null){
            userDTO = new UserDTO();
            userDTO.setRole(user.getRole());
            userDTO.setLogin(user.getLogin());
            userDTO.setPassword(user.getPassword());
        }
        return userDTO;
    }
}
