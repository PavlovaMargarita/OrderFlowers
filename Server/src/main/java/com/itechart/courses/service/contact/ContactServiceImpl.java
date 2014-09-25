package com.itechart.courses.service.contact;

import com.itechart.courses.dao.contact.ContactDAO;
import com.itechart.courses.dao.order.OrderDAO;
import com.itechart.courses.dao.phone.PhoneDAO;
import com.itechart.courses.dao.user.UserDAO;
import com.itechart.courses.dto.*;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Order;
import com.itechart.courses.entity.Phone;
import com.itechart.courses.entity.User;
import com.itechart.courses.enums.PhoneTypeEnum;
import com.itechart.courses.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired(required = true)
    private ContactDAO contactDAO;

    @Autowired(required = true)
    private UserDAO userDAO;

    @Autowired(required = true)
    PhoneDAO phoneDAO;

    @Autowired(required = true)
    OrderDAO orderDAO;

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
        if(Validation.validateContact(contactDTO)) {
            Contact contact = contactDTOToContact(contactDTO);
            int idContact = contactDAO.createContact(contact);
            contact = contactDAO.readContact(idContact);
            updatePhone(contact, contactDTO.getPhones());
        }
    }

    @Override
    public void updateContact(ContactDTO contactDTO) {
        if(Validation.validateContact(contactDTO)) {
            Contact contact = contactDTOToContact(contactDTO);
            contactDAO.updateContact(contact);
            updatePhone(contact, contactDTO.getPhones());
        }
    }

    private void updatePhone(Contact contact, List<PhoneDTO> phones){
        for(PhoneDTO phoneDTO: phones){
            Phone phone = phoneDTOToPhone(contact, phoneDTO);
            if(phoneDTO.getCommand().equals("add")){
                phoneDAO.createPhone(phone);
            }
            if(phoneDTO.getCommand().equals("update")){
                phoneDAO.updatePhone(phone);
            }
            if(phoneDTO.getCommand().equals("delete")){
                phoneDAO.deletePhone(phone.getId());
            }
        }
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
    public PageableContactDTO readContact(int first, int count) {
        List contactDTOList = new ArrayList<ContactDTO>();
        List<Contact> contactList = contactDAO.readContacts(first, count);
        int totalCount = contactDAO.getContactCount();
        for (Contact contact : contactList) {
            contactDTOList.add(contactToContactDTO(contact));
        }
        PageableContactDTO result = new PageableContactDTO(contactDTOList, totalCount);
        return result;
    }


    @Override
    public List<PersonDTO> searchContact(ContactSearchDTO parameters) {
        List personDTOList = new ArrayList<ContactDTO>();
        List<Contact> contactList = contactDAO.searchContact(parameters);
        for (Contact contact : contactList){
            personDTOList.add(contactToPersonDTO(contact));
        }
        return personDTOList;
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

    public static PersonDTO contactToPersonDTO(Contact contact){
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(contact.getId());
        personDTO.setSurname(contact.getSurname());
        personDTO.setName(contact.getName());
        personDTO.setPatronymic(contact.getPatronymic());
        personDTO.setCity(contact.getCity());
        personDTO.setStreet(contact.getStreet());
        personDTO.setHome(contact.getHome());
        personDTO.setFlat(contact.getFlat());
        return personDTO;
    }

    private ContactDTO contactToContactDTO(Contact contact) {
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
        List phoneDTOList = new ArrayList();
        List<Phone> phoneList = phoneDAO.readAllPhones(contact);
        for(Phone phone: phoneList){
            phoneDTOList.add(phoneToPhoneDTO(phone));
        }
        contactDTO.setPhones(phoneDTOList);
        List orderDTOList = new ArrayList();
        List<Order> orderList = orderDAO.readAllOrder(contact);
        for(Order order: orderList){
            orderDTOList.add(orderTOOrderDTO(order));
        }
        contactDTO.setOrders(orderDTOList);
        return contactDTO;
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


    private PhoneDTO phoneToPhoneDTO(Phone phone) {
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setId(phone.getId());
        phoneDTO.setCountryCode(phone.getCountryCode());
        phoneDTO.setOperatorCode(phone.getOperatorCode());
        phoneDTO.setPhoneNumber(phone.getPhoneNumber());
        if(phone.getPhoneType().equals(PhoneTypeEnum.HOME)) {
            phoneDTO.setPhoneType("Домашний");
        }
        if(phone.getPhoneType().equals(PhoneTypeEnum.MOBILE)) {
            phoneDTO.setPhoneType("Мобильный");
        }
        phoneDTO.setComment(phone.getComment());
        return phoneDTO;
    }

    private Phone phoneDTOToPhone(Contact contact, PhoneDTO phoneDTO){
        Phone phone = new Phone();
        phone.setId(phoneDTO.getId());
        phone.setCountryCode(phoneDTO.getCountryCode());
        phone.setOperatorCode(phoneDTO.getOperatorCode());
        phone.setPhoneNumber(phoneDTO.getPhoneNumber());
        if(phoneDTO.getPhoneType().equals(PhoneTypeEnum.HOME.toString())) {
            phone.setPhoneType(PhoneTypeEnum.HOME);
        }
        if(phoneDTO.getPhoneType().equals(PhoneTypeEnum.MOBILE.toString())) {
            phone.setPhoneType(PhoneTypeEnum.MOBILE);
        }
        phone.setComment(phoneDTO.getComment());
        phone.setOwner(contact);
        return phone;
    }

    private TableOrderDTO orderTOOrderDTO(Order order){
        TableOrderDTO tableOrderDTO = new TableOrderDTO();
        tableOrderDTO.setDate(order.getDate());
        tableOrderDTO.setOrderDescription(order.getOrderDescription());
        tableOrderDTO.setSum(order.getSum());
        return tableOrderDTO;
    }
}
