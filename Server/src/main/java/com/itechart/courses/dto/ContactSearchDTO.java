package com.itechart.courses.dto;

import java.sql.Date;

/**
 * Created by User on 10.09.14.
 */
public class ContactSearchDTO {

    private String surname;
    private String name;
    private String patronymic;
    private Date lowerDateOfBirth;
    private Date upperDateOfBirth;
    private String city;
    private String street;
    private Integer home;
    private Integer flat;

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

    public Date getLowerDateOfBirth() {
        return lowerDateOfBirth;
    }

    public void setLowerDateOfBirth(Date lowerDateOfBirth) {
        this.lowerDateOfBirth = lowerDateOfBirth;
    }

    public Date getUpperDateOfBirth() {
        return upperDateOfBirth;
    }

    public void setUpperDateOfBirth(Date upperDateOfBirth) {
        this.upperDateOfBirth = upperDateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    public Integer getFlat() {
        return flat;
    }

    public void setFlat(Integer flat) {
        this.flat = flat;
    }
}
