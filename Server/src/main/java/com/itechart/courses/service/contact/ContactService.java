package com.itechart.courses.service.contact;

import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.dto.ContactSearchDTO;

import java.util.List;

public interface ContactService {
    public ContactDTO readContact(int id);
    public void createContact(ContactDTO contact);
    public void updateContact(ContactDTO contact);
    public boolean deleteContact(int id);
    public List readContact();
    public List searchContact(ContactSearchDTO parameters);
    public List readContactForUser(int idUser);
}
