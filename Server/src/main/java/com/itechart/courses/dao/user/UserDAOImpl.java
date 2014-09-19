package com.itechart.courses.dao.user;

import com.itechart.courses.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer createUser(User user){
        Integer id = null;
        Session session = null;
        if (user == null){
            throw new NullPointerException("user is null");
        }
        session = sessionFactory.getCurrentSession();
        id = (Integer) session.save(user);
        return id;
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public void deleteUser(int id){
        String hql = "update User set isDelete = :isDelete where id = :id";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setBoolean("isDelete", true);
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @SuppressWarnings("JpaQlInspection")
    @Override
    public User readUser(String login) {
        User user = null;
        Query query = sessionFactory.getCurrentSession().createQuery("from User where login = :login");
        query.setString("login", login);
        List<User> temp = query.list();
        if (temp.size() != 0){
            user = temp.get(0);
        }
        return user;
    }

    @Override
    public User readUser(int id){
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);
        return user;
    }
    
    @Override
    public User readUser(String login, String password){
        User user = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where login = :login AND password = :password");
        query.setString("login", login);
        query.setString("password", password);
        List<User> temp = query.list();
        if (temp.size() != 0){
            user = temp.get(0);
        }
        return user;
    }

    @Override
    @SuppressWarnings("JpaQlInspection")
    public List<User> readAllUsers() {
        List<User> users = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where isDelete = :isDelete");
        query.setBoolean("isDelete", false);
        users = query.list();
        return users;
    }

    @Override
    public void updateUser(User user){
        Session session = null;
        if (user == null){
            throw new NullPointerException("user is null");
        }
        session = sessionFactory.getCurrentSession();
        session.update(user);
    }
}
