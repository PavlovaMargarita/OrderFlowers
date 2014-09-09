package com.itechart.courses.service.user;

import com.itechart.courses.dto.UserDTO;

import java.util.List;
/**
 * Created by Margarita on 05.09.2014.
 */
public interface UserService {
    public UserDTO readUser(int id);
    public void createUser(UserDTO userDTO);
    public void updateUser(UserDTO userDTO);
    public void deleteUser(int id);
    public List readUser();
}
