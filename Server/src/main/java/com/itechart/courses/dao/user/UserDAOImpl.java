package com.itechart.courses.dao.user;

import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.User;
import com.itechart.courses.enums.RoleEnum;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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
        Query query = sessionFactory.getCurrentSession().createQuery("from User where login = :login and is_delete = :delete");
        query.setString("login", login);
        query.setString("delete", "false");
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
        Query query = session.createQuery("from User where login = :login AND password = :password AND isDelete = :isDelete");
        query.setString("login", login);
        query.setString("password", password);
        query.setBoolean("isDelete", false);
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
    @SuppressWarnings("JpaQlInspection")
    public List<User> readAllUsers(int first, int count) {
        List<User> users = null;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where isDelete = :isDelete");
        query.setBoolean("isDelete", false);
        query.setFirstResult(first);
        query.setMaxResults(count);
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

    @Override
    public List<User> readAllUsers(RoleEnum role) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User where role = :role and isDelete = :isDelete");
        query.setString("role", role.toString());
        query.setBoolean("isDelete", false);
        return query.list();
    }

    @Override
    public int getTotalUserCount() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("isDelete", false));
        int totalCount = ((Number) criteria.uniqueResult()).intValue();
        return totalCount;
    }
}
