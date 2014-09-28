package com.itechart.courses.controller;

import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.dto.EmailDTO;
import com.itechart.courses.dto.MessageTemplateDTO;
import com.itechart.courses.exception.DatabaseException;
import com.itechart.courses.exception.IllegalInputDataException;
import com.itechart.courses.exception.NotAuthorizedException;
import com.itechart.courses.service.contact.ContactService;
import com.itechart.courses.service.email.EmailService;
import com.itechart.courses.service.template.MessageTemplateService;
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
@RequestMapping("/OrderFlowers/mail")
public class MailController {

    private static final Logger logger = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageTemplateService messageTemplateService;

    @RequestMapping(method = RequestMethod.GET, value = "/showEmail")
    public @ResponseBody
    List<ContactDTO> showEmail(@RequestParam("checkId") ArrayList<Integer> contactId) throws IOException {
        List<ContactDTO> listContacts = new ArrayList<ContactDTO>();
        ContactDTO contact;
        try {
            for(int i: contactId){
                contact = contactService.readContact(i);
                if (contact.getEmail() != null)
                    listContacts.add(contact);
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
        return listContacts;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/showTemplate")
    public @ResponseBody List<MessageTemplateDTO> showTemplate() throws IOException{
        List<MessageTemplateDTO> result = null;
        try {
            result = messageTemplateService.showTemplate();
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

    @RequestMapping(method = RequestMethod.POST, value = "/sendEmail")
    public @ResponseBody void sendEmail(@RequestBody EmailDTO emailDTO) throws IOException{
        logger.info("User sent email");
        try {
            emailService.sendEmail(emailDTO);
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
    }
}
