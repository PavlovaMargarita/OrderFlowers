package com.itechart.courses.dto;

import com.itechart.courses.enums.RoleEnum;

public class UserDTO {

    private RoleEnum role;
    private String roleRussian;
    private String login;
    private String password;
    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private int idContact;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public String getRoleRussian() {
        return roleRussian;
    }

    public void setRoleRussian(String roleRussian) {
        this.roleRussian = roleRussian;
    }
}
