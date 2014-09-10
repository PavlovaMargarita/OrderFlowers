package com.itechart.courses.dao.user;

import com.itechart.courses.entity.User;

import java.util.List;

/**
 * Created by Margarita on 14.08.2014.
 */
public interface UserDAO {
    public Integer createUser(User user);  //return id(Integer) new user
    public boolean deleteUser(int id); //if the user is deleted, the method returns true, otherwise false
    public User readUser(int id); //if the user is not found, returns null
    public User readUser(String login, String password);
    public User readUser(String login);
    public void updateUser(User user);
    public List<User> readAllUsers(); //if users not found, return null;
}
