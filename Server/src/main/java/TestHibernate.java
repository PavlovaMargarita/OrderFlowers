import bl.dao.contact.ContactDAOImpl;
import bl.dao.user.UserDAOImpl;
import bl.enums.RoleEnum;
import entity.Contact;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TestHibernate {
    public static void main(String[] args) {
//        Phone phone = new Phone();
//        phone.setCountryCode((short)375);
//        phone.setOperatorCode((short) 29);
//        phone.setPhoneNumber(1234567);
//        phone.setPhoneType(PhoneTypeEnum.HOME);
//        phone.setComment("test");

//        Contact contact = new Contact();
//        contact.setName("Margarita");
//        contact.setSurname("Pavlova");
//        ContactDAOImpl.getInstance().createContact(contact);

//        phone.setOwner(contact);
//Hello
//        User user = new User();
//        user.setId(1);
//        user.setLogin("login");
//        user.setPassword("pass");
//        user.setContact(contact);
//        user.setRole(RoleEnum.ADMIN);
//        user.setIsDelete(false);


        ContactDAOImpl.getInstance().deleteContact(1);

//        session.update(contact);
//        session.save(contact);
//        session.save(phone);

    }
}
