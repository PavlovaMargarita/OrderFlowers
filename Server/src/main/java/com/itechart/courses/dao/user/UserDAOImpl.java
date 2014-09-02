package com.itechart.courses.dao.user;

import com.itechart.courses.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Margarita on 14.08.2014.
 */

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer createUser(User user){
        Integer id = null;
        Session session = null;
        Transaction transaction = null;
        if (user == null){
            throw new NullPointerException("user is null");
        }
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(user);
            transaction.commit();
        }
        finally {
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return id;
    }

    @SuppressWarnings("JpaQlInspection")
    public boolean deleteUser(int id){
        boolean result = false;
        Session session = null;
        Transaction transaction = null;
        User user = null;
        String hql = "update User set isDelete = :isDelete where id = :id";
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setBoolean("isDelete", true);
            query.setInteger("id", id);
            query.executeUpdate();
            transaction.commit();
        }
        finally {
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result;
    }

    public User readUser(int id){
        User user = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            user = (User) session.get(User.class, id);
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }
    
    
    public User readUser(String login, String password){
        User user = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from User where login = :login AND password = :password");
            query.setString("login", login);
            query.setString("password", password);
            List<User> temp = query.list();
            if (temp.size() != 0){
                user = temp.get(0);
            }
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    @SuppressWarnings("JpaQlInspection")
    public List<User> readAllUsers() {
        List<User> users = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from User where isDelete = :isDelete");
            query.setBoolean("isDelete", false);
            users = query.list();
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return users;
    }

    public void updateUser(User user){
        Session session = null;
        Transaction transaction = null;
        if (user == null){
            throw new NullPointerException("user is null");
        }
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
        finally {
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
