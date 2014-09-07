import com.itechart.courses.dao.contact.ContactDAO;
import com.itechart.courses.dao.contact.ContactDAOImpl;
import com.itechart.courses.entity.Contact;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ContactTest {

    @Autowired
    ContactDAO contactDAO;

    @Test
    @BeforeClass
    public void testReadCreateContact(){
        Contact contact1 = new Contact();
        contact1.setSurname("Иванов");
        contact1.setName("Иван");
        contact1.setPatronymic("Иванович");
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DATE, 13);
        calendar1.set(Calendar.MONTH, 9);
        calendar1.set(Calendar.YEAR, 2013);
        Date dateOfBirth1 = new Date(calendar1.getTime().getTime());
        contact1.setDateOfBirth(dateOfBirth1);
        contact1.setHome(2);
        Contact contact2 = new Contact();
        contact2.setSurname("Петров");
        contact2.setName("Петр");
        contact2.setPatronymic("Петрович");
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.MONTH, 6);
        calendar2.set(Calendar.DATE, 7);
        calendar2.set(Calendar.YEAR, 2010);
        Date dateOfBirth2 = new Date(calendar1.getTime().getTime());
        contact2.setDateOfBirth(dateOfBirth2);
        contact2.setHome(17);
        contactDAO.createContact(contact1);
        contactDAO.createContact(contact2);
        try {
            List<Contact> contactList = contactDAO.readAllContacts();
            Assert.assertEquals("Surname is not equals", contact1.getSurname(), contactList.get(0).getSurname());
            Assert.assertEquals("Name is not equals", contact1.getName(), contactList.get(0).getName());
            Assert.assertEquals("Patronymic is not equals", contact1.getPatronymic(), contactList.get(0).getPatronymic());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date1 = dateFormat.format(contact1.getDateOfBirth());
            Assert.assertEquals("Date of birth is not equals", date1, contactList.get(0).getDateOfBirth().toString());
            Assert.assertEquals("Home of birth is not equals", contact1.getHome(), contactList.get(0).getHome());

            Assert.assertEquals("Surname is not equals", contact2.getSurname(), contactList.get(1).getSurname());
            Assert.assertEquals("Name is not equals", contact2.getName(), contactList.get(1).getName());
            Assert.assertEquals("Patronymic is not equals", contact2.getPatronymic(), contactList.get(1).getPatronymic());
            String date2 = dateFormat.format(contact2.getDateOfBirth());
            Assert.assertEquals("Date of birth is not equals", date1, contactList.get(1).getDateOfBirth().toString());
            Assert.assertEquals("Home of birth is not equals", contact2.getHome(), contactList.get(1).getHome());
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    @Test
    @BeforeClass
    public void testDeleteContact(){
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
        contactDAO.deleteContact(contact.getId());
        Assert.assertEquals("Contact is not deleted", new Boolean(true), contactDAO.readContact(contact.getId()).getIsDelete());
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
        Date dateOfBirth = new Date(calendar.getTime().getTime());
        contact.setDateOfBirth(dateOfBirth);
        contact.setHome(2);
        contactDAO.createContact(contact);
        contact.setSurname("Петров");
        calendar.set(Calendar.DATE, 13);
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.YEAR, 2013);
        dateOfBirth = new Date(calendar.getTime().getTime());
        contact.setDateOfBirth(dateOfBirth);
        contactDAO.updateContact(contact);
        List<Contact> contactList = contactDAO.readAllContacts();
        Assert.assertEquals("Surname is not equals", contact.getSurname(), contactList.get(0).getSurname());
        Assert.assertEquals("Name is not equals", contact.getName(), contactList.get(0).getName());
        Assert.assertEquals("Patronymic is not equals", contact.getPatronymic(), contactList.get(0).getPatronymic());
        //---не придумала, как по-другому сравнить
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(contact.getDateOfBirth());
        Assert.assertEquals("Date of birth is not equals", date, contactList.get(0).getDateOfBirth().toString());
        Assert.assertEquals("Home of birth is not equals", contact.getHome(), contactList.get(0).getHome());


    }


}
