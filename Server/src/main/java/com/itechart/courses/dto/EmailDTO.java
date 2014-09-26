package com.itechart.courses.dto;

import java.util.ArrayList;
import java.util.List;

public class EmailDTO {

    private List<ContactDTO> contacts;
    private String text;
    private String topic;

    public EmailDTO() {
        contacts = new ArrayList<ContactDTO>();
    }

    public List<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
