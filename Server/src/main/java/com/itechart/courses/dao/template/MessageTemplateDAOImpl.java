package com.itechart.courses.dao.template;

import com.itechart.courses.entity.MessageTemplate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MessageTemplateDAOImpl implements MessageTemplateDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<MessageTemplate> getAllTemplates() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(MessageTemplate.class);
        List<MessageTemplate> templates = criteria.list();
        return templates;
    }
}
