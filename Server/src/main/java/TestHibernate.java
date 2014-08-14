import entity.Contact;
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

//        phone.setOwner(contact);
//Hello
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        Contact contact = (Contact)session.get(Contact.class, 1);
        session.delete(contact);
//        session.update(contact);
//        session.save(contact);
//        session.save(phone);
        session.getTransaction().commit();
    }
}
