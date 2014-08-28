package com.itechart.courses.bl.service.authorization;

import com.itechart.courses.bl.dao.user.UserDAO;
import com.itechart.courses.bl.dao.user.UserDAOImpl;
import com.itechart.courses.bl.dto.UserDTO;
import com.itechart.courses.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorizationImpl implements Authorization {
    @Override
    public UserDTO execute(String login, String password) {
        UserDTO userDTO = null;
        User user = UserDAOImpl.getInstance().readUser(login, password);
        if (user != null){
            userDTO = new UserDTO();
            userDTO.setRole(user.getRole());
            userDTO.setLogin(user.getLogin());
            userDTO.setPassword(user.getPassword());
        }
        return userDTO;
    }
}
