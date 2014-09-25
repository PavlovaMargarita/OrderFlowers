package com.itechart.courses.dao.user;

import com.itechart.courses.entity.User;
import com.itechart.courses.enums.RoleEnum;

import java.util.List;

public interface UserDAO {
    public Integer createUser(User user);  //return id(Integer) new user
    public void deleteUser(int id);
    public User readUser(int id); //if the user is not found, returns null
    public User readUser(String login, String password);
    public User readUser(String login);
    public void updateUser(User user);
    public List<User> readAllUsers(); //if users not found, return null;
    public List<User> readAllUsers(int first, int count);
    public List<User> readAllUsers(RoleEnum role);
    public int getTotalUserCount();
}
