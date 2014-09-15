import com.itechart.courses.dao.contact.ContactDAO;
import com.itechart.courses.dao.contact.ContactDAOImpl;
import com.itechart.courses.dao.phone.PhoneDAO;
import com.itechart.courses.dao.phone.PhoneDAOImpl;
import com.itechart.courses.enums.PhoneTypeEnum;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Phone;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/application-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback = true)
public class PhoneTest {

    @Autowired
    ContactDAO contactDAO;

    @Autowired
    PhoneDAO phoneDAO;

    @Test
    @Transactional
    public void testReadCreatePhone(){
        Contact contact = new Contact();
        contact.setSurname("Иванов");
        contact.setName("Иван");
        Phone phone1 = new Phone();
        phone1.setOwner(contact);
        phone1.setCountryCode((short)375);
        phone1.setOperatorCode((short)33);
        phone1.setPhoneNumber(7654321);
        phone1.setPhoneType(PhoneTypeEnum.MOBILE);
        Phone phone2 = new Phone();
        phone2.setOwner(contact);
        phone2.setCountryCode((short) 375);
        phone2.setOperatorCode((short) 29);
        phone2.setPhoneNumber(1234567);
        phone2.setPhoneType(PhoneTypeEnum.MOBILE);
        contactDAO.createContact(contact);
        phoneDAO.createPhone(phone1);
        phoneDAO.createPhone(phone2);
        try {
            List<Phone> phoneList = phoneDAO.readAllPhones(contact);
            Assert.assertEquals("Country code is not equals", phone1.getCountryCode(), phoneList.get(0).getCountryCode());
            Assert.assertEquals("Operator code is not equals", phone1.getOperatorCode(), phoneList.get(0).getOperatorCode());
            Assert.assertEquals("Phone number is not equals", phone1.getPhoneNumber(), phoneList.get(0).getPhoneNumber());
            Assert.assertEquals("Phone type is not equals", phone1.getPhoneType(), phoneList.get(0).getPhoneType());
            Assert.assertEquals("Phone owner is not equals", phone1.getOwner().getId(), phoneList.get(0).getOwner().getId());
            Assert.assertEquals("Country code is not equals", phone2.getCountryCode(), phoneList.get(1).getCountryCode());
            Assert.assertEquals("Operator code is not equals", phone2.getOperatorCode(), phoneList.get(1).getOperatorCode());
            Assert.assertEquals("Phone number is not equals", phone2.getPhoneNumber(), phoneList.get(1).getPhoneNumber());
            Assert.assertEquals("Phone type is not equals", phone2.getPhoneType(), phoneList.get(1).getPhoneType());
            Assert.assertEquals("Phone owner is not equals", phone2.getOwner().getId(), phoneList.get(1).getOwner().getId());
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    public void testDeletePhone(){
        Contact contact = new Contact();
        contact.setSurname("Иванов");
        contact.setName("Иван");
        Phone phone = new Phone();
        phone.setOwner(contact);
        phone.setCountryCode((short) 375);
        phone.setOperatorCode((short) 33);
        phone.setPhoneNumber(7654321);
        phone.setPhoneType(PhoneTypeEnum.MOBILE);
        contactDAO.createContact(contact);
        phoneDAO.createPhone(phone);
        phoneDAO.deletePhone(phone.getId());
        Assert.assertEquals("Phone is not deleted", null, phoneDAO.readPhone(phone.getId()));
    }

    @Test
    @Transactional
    public void testUpdateContact(){
        Contact contact = new Contact();
        contact.setSurname("Иванов");
        contact.setName("Иван");
        Phone phone = new Phone();
        phone.setOwner(contact);
        phone.setCountryCode((short) 375);
        phone.setOperatorCode((short) 33);
        phone.setPhoneNumber(7654321);
        phone.setPhoneType(PhoneTypeEnum.MOBILE);
        contactDAO.createContact(contact);
        phoneDAO.createPhone(phone);
        phone.setCountryCode((short) 123);
        phoneDAO.updatePhone(phone);
        List<Phone> phoneList = phoneDAO.readAllPhones(contact);
        Assert.assertEquals("Country code is not equals", phone.getCountryCode(), phoneList.get(0).getCountryCode());
    }
}
