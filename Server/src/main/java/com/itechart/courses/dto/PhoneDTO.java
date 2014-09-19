package com.itechart.courses.dto;

public class PhoneDTO {

    private Integer id;
    private Short countryCode;
    private Short operatorCode;
    private Integer phoneNumber;
    private String phoneType;
    private String comment;
    private String command;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Short countryCode) {
        this.countryCode = countryCode;
    }

    public Short getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Short operatorCode) {
        this.operatorCode = operatorCode;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
