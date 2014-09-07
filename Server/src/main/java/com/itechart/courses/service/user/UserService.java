package com.itechart.courses.service.user;

import com.itechart.courses.dto.UserDTO;
import com.itechart.courses.entity.User;
import java.util.*;
/**
 * Created by Margarita on 05.09.2014.
 */
public interface UserService {
    public UserDTO readUser(int id);
    public void createUser(User user);
    public void updateUser(User user);
    public void deleteUser(int id);
    public List readUser();
}
