package com.itechart.courses.dao.template;

import com.itechart.courses.entity.MessageTemplate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageTemplateDAOImpl implements MessageTemplateDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public MessageTemplateDAOImpl(){
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    public List<MessageTemplate> getAllTemplates() {
        Session session = null;
        List<MessageTemplate> templates = null;
        try {
            session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(MessageTemplate.class);
            templates = criteria.list();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return templates;
    }
}
