package com.itechart.courses.service.contact;

import com.itechart.courses.dto.ContactDTO;

import java.util.List;

public interface ContactService {
    public ContactDTO readContact(int id);
    public void createContact(ContactDTO contact);
    public void updateContact(ContactDTO contact);
    public void deleteContact(int id);
    public List readContact();
    public List searchContact();
    public List readContactForUser(int idUser);
}
