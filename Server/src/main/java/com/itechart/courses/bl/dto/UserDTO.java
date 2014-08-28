package com.itechart.courses.bl.dto;

import com.itechart.courses.bl.enums.RoleEnum;
import org.springframework.stereotype.Component;

/**
 * Created by Александр on 28.08.2014.
 */

@Component
public class UserDTO {

    private RoleEnum role;

    private String login;

    private String password;

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
