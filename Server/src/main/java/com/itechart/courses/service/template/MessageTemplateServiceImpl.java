package com.itechart.courses.service.template;

import com.itechart.courses.dao.template.MessageTemplateDAO;
import com.itechart.courses.dto.MessageTemplateDTO;
import com.itechart.courses.entity.MessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 18.09.2014.
 */
@Service
public class MessageTemplateServiceImpl implements MessageTemplateService {

    @Autowired
    private MessageTemplateDAO messageTemplateDAO;

    @Override
    public List<MessageTemplateDTO> showTemplate() {
        List<MessageTemplateDTO> messageTemplateDTOList = new ArrayList<MessageTemplateDTO>();
        List<MessageTemplate> messageTemplateList = messageTemplateDAO.getAllTemplates();
        for (MessageTemplate mt : messageTemplateList) {
            messageTemplateDTOList.add(messageTemplateToMessageTemplateDTO(mt));
        }
        return messageTemplateDTOList;
    }

    private MessageTemplateDTO messageTemplateToMessageTemplateDTO(MessageTemplate messageTemplate) {
        MessageTemplateDTO messageTemplateDTO = new MessageTemplateDTO();
        messageTemplateDTO.setId(messageTemplate.getId());
        messageTemplateDTO.setName(messageTemplate.getName());
        messageTemplateDTO.setTemplate(messageTemplate.getTemplate());
        return messageTemplateDTO;
    }
}
