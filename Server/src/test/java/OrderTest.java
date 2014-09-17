import com.itechart.courses.dao.contact.ContactDAO;
import com.itechart.courses.dao.contact.ContactDAOImpl;
import com.itechart.courses.dao.order.OrderDAO;
import com.itechart.courses.dao.order.OrderDAOImpl;
import com.itechart.courses.dao.user.UserDAO;
import com.itechart.courses.dao.user.UserDAOImpl;
import com.itechart.courses.enums.OrderStatusEnum;
import com.itechart.courses.enums.RoleEnum;
import com.itechart.courses.entity.Contact;
import com.itechart.courses.entity.Order;
import com.itechart.courses.entity.User;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by Margarita on 19.08.2014.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/application-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback = true)
public class OrderTest {

    @Autowired
    ContactDAO contactDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    OrderDAO orderDAO;

    @Test
    @Transactional
    public void testReadCreateOrder(){
        Contact contact = new Contact();
        contact.setSurname("Иванов");
        contact.setName("Иван");
        contact.setPatronymic("Иванович");
        User user = new User();
        user.setContact(contact);
        user.setLogin("test1");
        user.setPassword("test1");
        user.setRole(RoleEnum.SERVICE_DELIVERY_MANAGER);
        Order order1 = new Order();
        order1.setReceiveManager(user);
        order1.setHandlerManager(user);
        order1.setDeliveryManager(user);
        order1.setSum(150);
        order1.setStatus(OrderStatusEnum.ADOPTED);
        order1.setCustomer(contact);
        order1.setRecipient(contact);
        order1.setOrderDescription("test1");
        Order order2 = new Order();
        order2.setReceiveManager(user);
        order2.setHandlerManager(user);
        order2.setDeliveryManager(user);
        order2.setSum(100);
        order2.setStatus(OrderStatusEnum.ADOPTED);
        order2.setCustomer(contact);
        order2.setRecipient(contact);
        order2.setOrderDescription("test2");
        contactDAO.createContact(contact);
        userDAO.createUser(user);
        orderDAO.createOrder(order1);
        orderDAO.createOrder(order2);
        try{
            List<Order> orderList = orderDAO.readAllOrder(contact);
            Assert.assertEquals("Order description is not equals", order1.getOrderDescription(), orderList.get(0).getOrderDescription());
            Assert.assertEquals("Order status is not equals", order1.getStatus(), orderList.get(0).getStatus());
            Assert.assertEquals("Order sum is not equals", order1.getSum(), orderList.get(0).getSum());
            Assert.assertEquals("Order description is not equals", order2.getOrderDescription(), orderList.get(1).getOrderDescription());
            Assert.assertEquals("Order status is not equals", order2.getStatus(), orderList.get(1).getStatus());
            Assert.assertEquals("Order sum is not equals", order2.getSum(), orderList.get(1).getSum());
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    @Test
    @Transactional
    public void testUpdateOrder(){
        Contact contact = new Contact();
        contact.setSurname("Иванов");
        contact.setName("Иван");
        contact.setPatronymic("Иванович");
        User user = new User();
        user.setContact(contact);
        user.setLogin("test1");
        user.setPassword("test1");
        user.setRole(RoleEnum.SERVICE_DELIVERY_MANAGER);
        Order order = new Order();
        order.setReceiveManager(user);
        order.setHandlerManager(user);
        order.setDeliveryManager(user);
        order.setSum(150);
        order.setStatus(OrderStatusEnum.ADOPTED);
        order.setCustomer(contact);
        order.setRecipient(contact);
        order.setOrderDescription("test1");
        contactDAO.createContact(contact);
        userDAO.createUser(user);
        orderDAO.createOrder(order);
        order.setSum(2600);
        orderDAO.updateOrder(order);
        Assert.assertEquals("Order sum is not equals", order.getSum(), orderDAO.readOrder(order.getId()).getSum());
    }
}
