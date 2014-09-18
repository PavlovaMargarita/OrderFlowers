package com.itechart.courses.service.user;

import com.itechart.courses.dto.UserDTO;
import com.itechart.courses.enums.Roles;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
/**
 * Created by Margarita on 05.09.2014.
 */
public interface UserService {
    @Secured(Roles.ADMIN)
    public UserDTO readUser(int id);

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
}
