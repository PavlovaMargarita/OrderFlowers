package com.itechart.courses.service.contact;

import com.itechart.courses.dao.contact.ContactDAO;
import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.dto.PhoneDTO;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired(required = true)
    ContactDAO contactDAO;

    @Override
    public ContactDTO readContact(int id) {
        ContactDTO contactDTO = null;
        Contact contact = contactDAO.readContact(id);
        if(contact != null){
            contactDTO = contactToContactDTO(contact);
        }
        return contactDTO;
    }

    @Override
    public void createContact(Contact contact) {
        contactDAO.createContact(contact);
    }

    @Override
    public void updateContact(Contact contact) {
        contactDAO.updateContact(contact);
    }

    @Override
    public void deleteContact(int id) {
        contactDAO.deleteContact(id);
    }

    @Override
    public List readContact() {
        List contactDTOList = new ArrayList <ContactDTO> ();
        List<Contact> contactList = contactDAO.readAllContacts();
        for(Contact contact: contactList){
            contactDTOList.add(contactToContactDTO(contact));
        }
        return contactDTOList;
    }

    @Override
    public List searchContact() {
        return null;
    }



    public ContactDTO contactToContactDTO(Contact contact){
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
    private PhoneDTO phoneToPhoneDTO(Phone phone){
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setId(phone.getId());
        phoneDTO.setCountryCode(phone.getCountryCode());
        phoneDTO.setOperatorCode(phone.getOperatorCode());
        phoneDTO.setPhoneNumber(phone.getPhoneNumber());
        phoneDTO.setPhoneType(phone.getPhoneType());
        phoneDTO.setComment(phone.getComment());
        return phoneDTO;
    }
}
