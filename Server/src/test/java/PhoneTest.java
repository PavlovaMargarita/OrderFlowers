import com.itechart.courses.bl.dao.contact.ContactDAOImpl;
import com.itechart.courses.bl.dao.phone.PhoneDAOImpl;
import com.itechart.courses.bl.enums.PhoneTypeEnum;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Phone;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class PhoneTest {
    @Test
    @BeforeClass
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

        ContactDAOImpl.getInstance().createContact(contact);
        PhoneDAOImpl.getInstance().createPhone(phone1);
        PhoneDAOImpl.getInstance().createPhone(phone2);
        try {
            List<Phone> phoneList = PhoneDAOImpl.getInstance().readAllPhones(contact);
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
    @BeforeClass
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
        ContactDAOImpl.getInstance().createContact(contact);
        PhoneDAOImpl.getInstance().createPhone(phone);
        PhoneDAOImpl.getInstance().deletePhone(phone.getId());
        Assert.assertEquals("Phone is not deleted", null, PhoneDAOImpl.getInstance().readPhone(phone.getId()));
    }

    @Test
    @BeforeClass
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
        ContactDAOImpl.getInstance().createContact(contact);
        PhoneDAOImpl.getInstance().createPhone(phone);
        phone.setCountryCode((short) 123);
        PhoneDAOImpl.getInstance().updatePhone(phone);

        List<Phone> phoneList = PhoneDAOImpl.getInstance().readAllPhones(contact);
        Assert.assertEquals("Country code is not equals", phone.getCountryCode(), phoneList.get(0).getCountryCode());


    }
}
