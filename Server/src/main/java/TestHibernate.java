import entity.Phone;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TestHibernate {
    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.setCountryCode((short)375);
        phone.setOperatorCode((short)29);
        phone.setPhoneNumber(1234567);
        phone.setPhoneType("home");
        phone.setComment("test");
        phone.setContactId(1);

        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(phone);

        session.getTransaction().commit();
    }
}
