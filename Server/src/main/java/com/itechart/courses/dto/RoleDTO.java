package com.itechart.courses.dto;

import com.itechart.courses.enums.RoleEnum;

/**
 * Created by Margarita on 18.09.2014.
 */
public class RoleDTO {
    private RoleEnum role;
    private String roleRussian;

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getRoleRussian() {
        return roleRussian;
    }

    public void setRoleRussian(String roleRussian) {
        this.roleRussian = roleRussian;
    }
}
