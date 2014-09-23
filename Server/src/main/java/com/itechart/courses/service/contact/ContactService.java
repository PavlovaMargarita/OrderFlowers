package com.itechart.courses.service.contact;

import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.dto.ContactSearchDTO;
import com.itechart.courses.dto.PageableContactDTO;
import com.itechart.courses.dto.PersonDTO;
import com.itechart.courses.enums.Roles;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface ContactService {
    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN})
    public ContactDTO readContact(int id);

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN})
    public void createContact(ContactDTO contact);

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN})
    public void updateContact(ContactDTO contact);

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN})
    public boolean deleteContact(int id);

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN})
    public PageableContactDTO readContact(int first, int count);

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN})
    public List<PersonDTO> searchContact(ContactSearchDTO parameters);

    @Secured({Roles.SUPERVISOR, Roles.RECEIVING_ORDERS_MANAGER, Roles.ADMIN})
    public List readContactForUser(int idUser);
}
