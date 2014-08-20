package bl.dao.user;

import entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Created by Margarita on 14.08.2014.
 */
public class UserDAOImpl implements UserDAO {
    private static UserDAOImpl ourInstance = new UserDAOImpl();
    private SessionFactory factory;

    public static UserDAOImpl getInstance() {
        return ourInstance;
    }

    private UserDAOImpl() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public Integer createUser(User user){
        Integer id = null;
        Session session = null;
        Transaction transaction = null;
        if (user == null){
            throw new NullPointerException("user is null");
        }
        try {
            session = factory.openSession();
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
            session = factory.openSession();
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
            session = factory.openSession();
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
            session = factory.openSession();
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

    public List<User> readAllUsers() {
        List<User> users = null;
        Session session = null;

        try {
            session = factory.openSession();
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
            session = factory.openSession();
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
