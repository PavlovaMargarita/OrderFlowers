package com.itechart.courses.service.contact;

import com.itechart.courses.dao.contact.ContactDAO;
import com.itechart.courses.dao.user.UserDAO;
import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.dto.ContactSearchDTO;
import com.itechart.courses.dto.PhoneDTO;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Phone;
import com.itechart.courses.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired(required = true)
    ContactDAO contactDAO;

    @Autowired(required = true)
    UserDAO userDAO;

    @Override
    public ContactDTO readContact(int id) {
        ContactDTO contactDTO = null;
        Contact contact = contactDAO.readContact(id);
        if (contact != null) {
            contactDTO = contactToContactDTO(contact);
        }
        return contactDTO;
    }

    @Override
    public void createContact(ContactDTO contactDTO) {
        contactDAO.createContact(contactDTOToContact(contactDTO));
    }

    @Override
    public void updateContact(ContactDTO contactDTO) {
        contactDAO.updateContact(contactDTOToContact(contactDTO));
    }

    @Override
    public boolean deleteContact(int id) {
        List<User> users = userDAO.readAllUsers();
        boolean delete = true;
        for(User user: users){
            if(user.getContact().getId() == id){
                delete = false;
                return false;
            }
        }
        contactDAO.deleteContact(id);
        return true;
    }

    @Override
    public List readContact() {
        List contactDTOList = new ArrayList<ContactDTO>();
        List<Contact> contactList = contactDAO.readAllContacts();
        for (Contact contact : contactList) {
            contactDTOList.add(contactToContactDTO(contact));
        }
        return contactDTOList;
    }

    @Override
    public List searchContact(ContactSearchDTO parameters) {
        List contactDTOList = new ArrayList<ContactDTO>();
        List<Contact> contactList = contactDAO.searchContact(parameters);
        for (Contact contact : contactList){
            contactDTOList.add(contactToContactDTO(contact));
        }
        return contactDTOList;
    }

    @Override
    public List readContactForUser(int idUser) {
        List<Contact> contacts = contactDAO.readAllContacts();
        List<User> users = userDAO.readAllUsers();
        for (User user : users) {
            if (contacts.contains(user.getContact()) && user.getId() != idUser ) {
                contacts.remove(user.getContact());
            }
        }
        List contactDTOList = new ArrayList<ContactDTO>();
        for (Contact contact : contacts) {
            contactDTOList.add(contactToContactDTO(contact));
        }
        return contactDTOList;
    }


    public ContactDTO contactToContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setSurname(contact.getSurname());
        contactDTO.setName(contact.getName());
        contactDTO.setPatronymic(contact.getPatronymic());
        contactDTO.setDateOfBirth(contact.getDateOfBirth());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setCity(contact.getCity());
        contactDTO.setStreet(contact.getStreet());
        contactDTO.setHome(contact.getHome());
        contactDTO.setFlat(contact.getFlat());
//        List phoneDTOList = new ArrayList();
//        for(Phone phone: contact.getPhones()){
//            phoneDTOList.add(phoneToPhoneDTO(phone));
//        }
//        contactDTO.setPhones(phoneDTOList);
        return contactDTO;
    }

    private PhoneDTO phoneToPhoneDTO(Phone phone) {
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setId(phone.getId());
        phoneDTO.setCountryCode(phone.getCountryCode());
        phoneDTO.setOperatorCode(phone.getOperatorCode());
        phoneDTO.setPhoneNumber(phone.getPhoneNumber());
        phoneDTO.setPhoneType(phone.getPhoneType());
        phoneDTO.setComment(phone.getComment());
        return phoneDTO;
    }

    private Contact contactDTOToContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setSurname(contactDTO.getSurname());
        contact.setPatronymic(contactDTO.getPatronymic());
        contact.setName(contactDTO.getName());
        contact.setDateOfBirth(contactDTO.getDateOfBirth());
        contact.setEmail(contactDTO.getEmail());
        contact.setCity(contactDTO.getCity());
        contact.setStreet(contactDTO.getStreet());
        contact.setHome(contactDTO.getHome());
        contact.setFlat(contactDTO.getFlat());
        return contact;
    }
}
