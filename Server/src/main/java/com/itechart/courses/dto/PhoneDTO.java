package com.itechart.courses.dto;

import com.itechart.courses.enums.PhoneTypeEnum;
import com.itechart.courses.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class PhoneDTO {

    private Integer id;

    private Contact owner;

    private Short countryCode;

    private Short operatorCode;

    private Integer phoneNumber;

    private PhoneTypeEnum phoneType;

    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contact getOwner() {
        return owner;
    }

    public void setOwner(Contact owner) {
        this.owner = owner;
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

    public PhoneTypeEnum getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneTypeEnum phoneType) {
        this.phoneType = phoneType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
