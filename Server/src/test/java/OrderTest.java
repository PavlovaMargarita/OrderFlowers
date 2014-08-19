import bl.dao.contact.ContactDAOImpl;
import bl.dao.order.OrderDAOImpl;
import bl.dao.user.UserDAOImpl;
import bl.enums.OrderStatusEnum;
import bl.enums.RoleEnum;
import entity.Contact;
import entity.Order;
import entity.User;
import junit.framework.Assert;

import java.util.*;

/**
 * Created by Margarita on 19.08.2014.
 */
public class OrderTest {
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

        ContactDAOImpl.getInstance().createContact(contact);
        UserDAOImpl.getInstance().createUser(user);
        OrderDAOImpl.getInstance().createOrder(order1);
        OrderDAOImpl.getInstance().createOrder(order2);
        try{
            List<Order> orderList = OrderDAOImpl.getInstance().readAllOrder(contact);
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

        ContactDAOImpl.getInstance().createContact(contact);
        UserDAOImpl.getInstance().createUser(user);
        OrderDAOImpl.getInstance().createOrder(order);

        order.setSum(2600);

        OrderDAOImpl.getInstance().updateOrder(order);
        Assert.assertEquals("Order sum is not equals", order.getSum(), OrderDAOImpl.getInstance().readOrder(order.getId()).getSum());

    }
}
