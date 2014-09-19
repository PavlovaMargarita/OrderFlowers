package com.itechart.courses.timer;

import com.itechart.courses.dao.contact.ContactDAO;
import com.itechart.courses.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

public class SendEmailByTimer{

    @Autowired
    private MailSender mailSender;

    @Autowired
    private ContactDAO contactDAO;

    @Scheduled(fixedRate = 86400000)
    public void send() {
        Date date = new Date();
        List<Contact> contacts = contactDAO.searchContactByDateOfBirth(date.getMonth() + 1, date.getDate());
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("sasha25121993@mail.ru");
        simpleMailMessage.setSubject("Поздравление");
        simpleMailMessage.setText("с Днём Рождения!");
        for (Contact contact : contacts) {
            if (contact.getEmail() != null) {
                simpleMailMessage.setTo(contact.getEmail());
                mailSender.send(simpleMailMessage);
            }
        }
    }
}

