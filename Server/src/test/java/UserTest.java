import com.itechart.cources.bl.dao.contact.ContactDAOImpl;
import com.itechart.cources.bl.dao.user.UserDAOImpl;
import com.itechart.cources.bl.enums.RoleEnum;
import com.itechart.cources.entity.Contact;
import com.itechart.cources.entity.User;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class UserTest {
    @Test
    @BeforeClass
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
        user1.setRole(RoleEnum.SERVICE_DELIVERY_MANAGER);
        User user2 = new User();
        user2.setContact(contact2);
        user2.setLogin("test2");
        user2.setPassword("test2");
        user2.setRole(RoleEnum.SUPERVISOR);

        ContactDAOImpl.getInstance().createContact(contact1);
        ContactDAOImpl.getInstance().createContact(contact2);
        UserDAOImpl.getInstance().createUser(user1);
        UserDAOImpl.getInstance().createUser(user2);
        try {
            List<User> userList = UserDAOImpl.getInstance().readAllUsers();
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

    @Test
    @BeforeClass
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
        ContactDAOImpl.getInstance().createContact(contact);

        User user = new User();
        user.setContact(contact);
        user.setLogin("test1");
        user.setPassword("test1");
        user.setRole(RoleEnum.SERVICE_DELIVERY_MANAGER);
        UserDAOImpl.getInstance().createUser(user);
        UserDAOImpl.getInstance().deleteUser(user.getId());
        Assert.assertEquals("User is not deleted", new Boolean(true), UserDAOImpl.getInstance().readUser(user.getId()).getIsDelete());
    }

    @Test
    @BeforeClass
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
        ContactDAOImpl.getInstance().createContact(contact);

        User user = new User();
        user.setContact(contact);
        user.setLogin("test1");
        user.setPassword("test1");
        user.setRole(RoleEnum.SERVICE_DELIVERY_MANAGER);
        UserDAOImpl.getInstance().createUser(user);
        user.setRole(RoleEnum.RECEIVING_ORDERS_MANAGER);
        UserDAOImpl.getInstance().updateUser(user);
        Assert.assertEquals("User role is not equals", user.getRole(), UserDAOImpl.getInstance().readAllUsers().get(0).getRole());


    }
}
