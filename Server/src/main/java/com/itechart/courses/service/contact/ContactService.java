package com.itechart.courses.service.contact;

import com.itechart.courses.entity.Contact;

import java.util.List;

public interface ContactService {
    public Contact getContact(int id);
    public void createUser(Contact contact);
    public List readContact();
    public List searchContact();
}
