import com.itechart.cources.bl.dao.contact.ContactDAOImpl;
import com.itechart.cources.bl.dao.order.OrderDAOImpl;
import com.itechart.cources.bl.dao.orderHistory.OrderHistoryDAOImpl;
import com.itechart.cources.bl.dao.user.UserDAOImpl;
import com.itechart.cources.bl.enums.OrderStatusEnum;
import com.itechart.cources.bl.enums.RoleEnum;
import com.itechart.cources.entity.Contact;
import com.itechart.cources.entity.Order;
import com.itechart.cources.entity.OrderHistory;
import com.itechart.cources.entity.User;
import junit.framework.Assert;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class OrderHistoryTest {
    public void testReadCreteOrderHistory(){
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

        OrderHistory orderHistory1 = new OrderHistory();
        orderHistory1.setComment("test1");
        orderHistory1.setStatus(OrderStatusEnum.ADOPTED);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 6);
        calendar.set(Calendar.DATE, 7);
        calendar.set(Calendar.YEAR, 2010);
        Date date1 = new Date(calendar.getTime().getTime());
        orderHistory1.setChangeDate(date1);
        orderHistory1.setOrder(order);
        orderHistory1.setUser(user);

        OrderHistory orderHistory2 = new OrderHistory();
        orderHistory2.setComment("test2");
        orderHistory2.setStatus(OrderStatusEnum.ADOPTED);
        calendar.set(Calendar.MONTH, 10);
        calendar.set(Calendar.DATE, 15);
        calendar.set(Calendar.YEAR, 2011);
        Date date2 = new Date(calendar.getTime().getTime());
        orderHistory2.setChangeDate(date2);
        orderHistory2.setOrder(order);
        orderHistory2.setUser(user);

        ContactDAOImpl.getInstance().createContact(contact);
        UserDAOImpl.getInstance().createUser(user);
        OrderDAOImpl.getInstance().createOrder(order);
        OrderHistoryDAOImpl.getInstance().createOrderHistory(orderHistory1);
        OrderHistoryDAOImpl.getInstance().createOrderHistory(orderHistory2);
        try{
            List<OrderHistory> orderHistoryList  = OrderHistoryDAOImpl.getInstance().readOrderHistory(order);
            Assert.assertEquals("Order History status is not equals", orderHistory1.getStatus(), orderHistoryList.get(0).getStatus());
            Assert.assertEquals("Order History status is not equals", orderHistory1.getComment(), orderHistoryList.get(0).getComment());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr1 = dateFormat.format(orderHistory1.getChangeDate());
            Assert.assertEquals("Order History change date is not equals", dateStr1, orderHistoryList.get(0).getChangeDate().toString());

            Assert.assertEquals("Order History status is not equals", orderHistory2.getStatus(), orderHistoryList.get(1).getStatus());
            Assert.assertEquals("Order History status is not equals", orderHistory2.getComment(), orderHistoryList.get(1).getComment());
            String dateStr2 = dateFormat.format(orderHistory2.getChangeDate());
            Assert.assertEquals("Order History change date is not equals", dateStr2, orderHistoryList.get(1).getChangeDate().toString());

        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

    public void testUpdateOrderHistory(){
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

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setComment("test1");
        orderHistory.setStatus(OrderStatusEnum.ADOPTED);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 6);
        calendar.set(Calendar.DATE, 7);
        calendar.set(Calendar.YEAR, 2010);
        Date date1 = new Date(calendar.getTime().getTime());
        orderHistory.setChangeDate(date1);
        orderHistory.setOrder(order);
        orderHistory.setUser(user);
        ContactDAOImpl.getInstance().createContact(contact);
        UserDAOImpl.getInstance().createUser(user);
        OrderDAOImpl.getInstance().createOrder(order);
        OrderHistoryDAOImpl.getInstance().createOrderHistory(orderHistory);
        orderHistory.setStatus(OrderStatusEnum.NEW);
        OrderHistoryDAOImpl.getInstance().updateOrderHistory(orderHistory);
        Assert.assertEquals("Order History status is not equals", orderHistory.getStatus(), OrderDAOImpl.getInstance().readOrder(orderHistory.getId()).getStatus());

    }
}

