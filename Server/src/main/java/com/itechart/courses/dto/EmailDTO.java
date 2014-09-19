package com.itechart.courses.dto;

import java.util.ArrayList;
import java.util.List;

public class EmailDTO {

    private List<String> emails;
    private String text;
    private String topic;

    public EmailDTO() {
        emails = new ArrayList<String>();
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
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
