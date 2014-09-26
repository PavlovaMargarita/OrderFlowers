package com.itechart.courses.service.email;

import com.itechart.courses.dto.ContactDTO;
import com.itechart.courses.dto.EmailDTO;
import org.antlr.stringtemplate.StringTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendEmail(EmailDTO emailDTO) {
        StringTemplate st = new StringTemplate(emailDTO.getText());
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("sasha25121993@mail.ru");
        simpleMailMessage.setSubject(emailDTO.getTopic());
        for (ContactDTO contact : emailDTO.getContacts()) {
            st.setAttribute("email", contact.getEmail());
            st.setAttribute("name", contact.getName());
            st.setAttribute("surname", contact.getSurname());
            st.setAttribute("patronymic", contact.getPatronymic());
            st.setAttribute("dateOfBirth", contact.getDateOfBirth());
            st.setAttribute("city", contact.getCity());
            st.setAttribute("street", contact.getStreet());
            st.setAttribute("home", contact.getHome());
            st.setAttribute("flat", contact.getFlat());
            simpleMailMessage.setText(st.toString());
            simpleMailMessage.setTo(contact.getEmail());
            mailSender.send(simpleMailMessage);
            st.reset();
        }
    }
}
