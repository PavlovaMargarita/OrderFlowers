package com.itechart.courses.service.user;

import com.itechart.courses.dto.PersonDTO;
import com.itechart.courses.dto.UserDTO;
import com.itechart.courses.enums.RoleEnum;
import com.itechart.courses.enums.Roles;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface UserService {

    @Secured(Roles.ADMIN)
    public UserDTO readUser(int id);

    public com.itechart.courses.entity.User readUser(String login);

    @Secured(Roles.ADMIN)
    public void createUser(UserDTO userDTO);

    @Secured(Roles.ADMIN)
    public void updateUser(UserDTO userDTO);

    @Secured(Roles.ADMIN)
    public void deleteUser(int id);

    @Secured(Roles.ADMIN)
    public List readUser();

    @Secured(Roles.ADMIN)
    public List readLogin();

    public List<PersonDTO> getUsersByRole(RoleEnum role);
}
