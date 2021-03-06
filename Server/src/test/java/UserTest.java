import com.itechart.courses.dao.contact.ContactDAO;
import com.itechart.courses.dao.user.UserDAO;
import com.itechart.courses.enums.RoleEnum;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.User;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/application-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback = true)
public class UserTest {

    @Autowired
    UserDAO userDAO;

    @Autowired
    ContactDAO contactDAO;

    @Test
    @Transactional
    public void testReadCreateUser(){
        Contact contact1 = new Contact();
        contact1.setSurname("Иванов");
        contact1.setName("Иван");
        contact1.setPatronymic("Иванович");
        Contact contact2 = new Contact();
        contact2.setSurname("Петров");
        contact2.setName("Петр");
        User user1 = new User();
        user1.setContact(contact1);
        user1.setLogin("test1");
        user1.setPassword("test1");
        user1.setRole(RoleEnum.ROLE_SERVICE_DELIVERY_MANAGER);
        User user2 = new User();
        user2.setContact(contact2);
        user2.setLogin("test2");
        user2.setPassword("test2");
        user2.setRole(RoleEnum.ROLE_SUPERVISOR);
        contactDAO.createContact(contact1);
        contactDAO.createContact(contact2);
        userDAO.createUser(user1);
        userDAO.createUser(user2);
        try {
            List<User> userList = userDAO.readAllUsers();
            Assert.assertEquals("Login is not equals", user1.getLogin(), userList.get(0).getLogin());
            Assert.assertEquals("Password is not equals", user1.getPassword(), userList.get(0).getPassword());
            Assert.assertEquals("Role is not equals", user1.getRole(), userList.get(0).getRole());
            Assert.assertEquals("Login is not equals", user2.getLogin(), userList.get(1).getLogin());
            Assert.assertEquals("Password is not equals", user2.getPassword(), userList.get(1).getPassword());
            Assert.assertEquals("Role is not equals", user2.getRole(), userList.get(1).getRole());
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    /*@Test
    public void testDeleteUser(){
        Contact contact = new Contact();
        contact.setSurname("Иванов");
        contact.setName("Иван");
        contact.setPatronymic("Иванович");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 13);
        calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.YEAR, 2013);
        Date dateOfBirth1 = new Date(calendar.getTime().getTime());
        contact.setDateOfBirth(dateOfBirth1);
        contact.setHome(2);
        contactDAO.createContact(contact);
        User user = new User();
        user.setContact(contact);
        user.setLogin("test1");
        user.setPassword("test1");
        user.setRole(RoleEnum.ROLE_SERVICE_DELIVERY_MANAGER);
        userDAO.createUser(user);
        userDAO.deleteUser(user.getId());
        User r = userDAO.readUser(user.getId());
        Assert.assertEquals("User is not deleted", new Boolean(true), r.getIsDelete());
    }*/

    @Test
    @Transactional
    public void testUpdateContact(){
        Contact contact = new Contact();
        contact.setSurname("Иванов");
        contact.setName("Иван");
        contact.setPatronymic("Иванович");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 13);
        calendar.set(Calendar.MONTH, 9);
        calendar.set(Calendar.YEAR, 2013);
        Date dateOfBirth1 = new Date(calendar.getTime().getTime());
        contact.setDateOfBirth(dateOfBirth1);
        contact.setHome(2);
        contactDAO.createContact(contact);
        User user = new User();
        user.setContact(contact);
        user.setLogin("test1");
        user.setPassword("test1");
        user.setRole(RoleEnum.ROLE_SERVICE_DELIVERY_MANAGER);
        userDAO.createUser(user);
        user.setRole(RoleEnum.ROLE_RECEIVING_ORDERS_MANAGER);
        userDAO.updateUser(user);
        Assert.assertEquals("User role is not equals", user.getRole(), userDAO.readAllUsers().get(0).getRole());
    }
}
