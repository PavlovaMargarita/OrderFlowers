package com.itechart.courses.service.email;

import com.itechart.courses.dto.EmailDTO;
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
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("sasha25121993@mail.ru");
        simpleMailMessage.setSubject(emailDTO.getTopic());
        simpleMailMessage.setText(emailDTO.getText());
        for (String email : emailDTO.getEmails()) {
            simpleMailMessage.setTo(email);
            mailSender.send(simpleMailMessage);
        }
    }
}
