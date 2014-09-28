package com.itechart.courses.controller;

import com.itechart.courses.dto.*;
import com.itechart.courses.exception.DatabaseException;
import com.itechart.courses.exception.IllegalInputDataException;
import com.itechart.courses.exception.NotAuthorizedException;
import com.itechart.courses.service.contact.ContactService;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/OrderFlowers/contact")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @RequestMapping(method = RequestMethod.GET, value = "/contactList")
    public @ResponseBody
    PageableContactDTO getContactList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords){
        logger.info("User viewed all contacts");
        int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
        PageableContactDTO result = null;
        try {
            result = contactService.readContact(firstRecordNumber, pageRecords);
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return result;

    }

    @RequestMapping(method = RequestMethod.POST, value = "/contactSearch")
    public @ResponseBody PageableContactDTO contactSearch(@RequestBody ContactSearchDTO contactSearchDTO,
                                                          @RequestParam("currentPage") int currentPage,
                                                          @RequestParam("pageRecords") int pageRecords){
        logger.info("User searched contacts");
        PageableContactDTO searchResult = null;
        try {
            int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
            searchResult = contactService.searchContact(contactSearchDTO, firstRecordNumber, pageRecords);
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return searchResult;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contactCorrect")
    public @ResponseBody
    ContactDTO getContact(@RequestParam("id") String id ){
        logger.info("User viewed the contact");
        ContactDTO contactDTO = null;
        try {
            contactDTO = contactService.readContact(Integer.parseInt(id));
        }
        catch (NumberFormatException e){
            throw new IllegalInputDataException();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return contactDTO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveContactCorrect")
    public @ResponseBody void saveContactCorrect(@RequestBody ContactDTO contactDTO) throws IOException {
        logger.info("User updated the contact");
        try {
            contactService.updateContact(contactDTO);
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveContactCreate")
    public @ResponseBody void saveContactCreate(@RequestBody ContactDTO contactDTO) throws IOException{
        logger.info("User created new contact");
        try {
            contactService.createContact(contactDTO);
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/contactDelete")
    public @ResponseBody boolean contactDelete(@RequestBody CheckDTO contactId) throws IOException{
        boolean delete = true;
        logger.info("User deleted contacts");
        try {
            for(int i: contactId.getCheckId()){
                if(!contactService.deleteContact(i)){
                    delete = false;
                }
            }
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return delete;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/contactListForUser")
    public @ResponseBody
    List<ContactDTO> getContactListForUser(@RequestParam("id") String idUser ){
        logger.info("User viewed all contacts");
        List<ContactDTO> result = null;
        try {
            result = contactService.readContactForUser(Integer.parseInt(idUser));
        }
        catch (NumberFormatException e){
            throw new IllegalInputDataException();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getPerson")
    public @ResponseBody List<PersonDTO> getPerson(@RequestParam("term") String term) {
        List<PersonDTO> listPersonDTO = new ArrayList<PersonDTO>();
        ContactSearchDTO contactSearchDTO = new ContactSearchDTO();
        contactSearchDTO.setSurname(term);
        try {
            if (!term.trim().isEmpty()) {
                listPersonDTO = contactService.searchContact(contactSearchDTO);
            }
        }
        catch (IllegalArgumentException e){
            throw new IllegalInputDataException();
        }
        catch (NullPointerException e){
            throw new IllegalInputDataException();
        }
        catch (AccessDeniedException e){
            throw new NotAuthorizedException();
        }
        catch (HibernateException e){
            throw new DatabaseException();
        }
        catch (CannotCreateTransactionException e){
            throw new DatabaseException();
        }
        return listPersonDTO;
    }

    private int firstRecordNumber(int currentPage, int count){
        int firstRecordNumber = (currentPage - 1) * count;
        return firstRecordNumber >= 0 ? firstRecordNumber : 0;
    }

}
