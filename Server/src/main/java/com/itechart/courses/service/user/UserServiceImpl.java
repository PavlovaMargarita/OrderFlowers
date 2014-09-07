package com.itechart.courses.service.user;

import com.itechart.courses.dao.user.UserDAO;
import com.itechart.courses.dto.UserDTO;
import com.itechart.courses.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = true)
    UserDAO userDAO;

    @Override
    public UserDTO readUser(int id) {
        UserDTO userDTO = null;
        User user = userDAO.readUser(id);
        if(user != null){
            userDTO = userToUserDTO(user);
        }
        return userDTO;
    }

    @Override
    public void createUser(User user) {
        userDAO.createUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    @Override
    public List readUser() {
        List userDTOList = new ArrayList<UserDTO>();
        List<User> userList = userDAO.readAllUsers();
        for(User user: userList){
            userDTOList.add(userToUserDTO(user));
        }
        return userDTOList;
    }

    public UserDTO userToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword(user.getPassword());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        return userDTO;

    }
}
