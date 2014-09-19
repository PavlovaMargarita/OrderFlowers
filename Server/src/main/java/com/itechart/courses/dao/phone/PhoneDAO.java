package com.itechart.courses.dao.phone;

import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Phone;

import java.util.List;

public interface PhoneDAO {
    public Integer createPhone(Phone phone);  //return id(Integer) new phone
    public boolean deletePhone(int id); //if the phone is deleted, the method returns true, otherwise false
    public Phone readPhone(int id); //if the phone is not found, returns null
    public void updatePhone(Phone phone);
    public List readAllPhones(Contact contact);

}
