package com.itechart.courses.service.contact;

import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.entity.Contact;

import java.util.List;

public interface ContactService {
    public ContactDTO readContact(int id);
    public void createContact(Contact contact);
    public void updateContact(Contact contact);
    public void deleteContact(int id);
    public List readContact();
    public List searchContact();
}
