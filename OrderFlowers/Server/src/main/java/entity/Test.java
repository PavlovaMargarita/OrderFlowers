package entity;

import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Test {
	public static void main(String[] args) {
		java.util.Date date = new java.util.Date();
        OrderHistory oh = new OrderHistory();
        oh.setUserId(1);
        oh.setStatusId((short)3);
        oh.setOrderId(8);
        oh.setChangeDate(new Date(date.getTime()));
        oh.setComment("It's test!");
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(oh);
        session.getTransaction().commit();
    }
}
