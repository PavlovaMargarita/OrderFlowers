package entity;

import bl.enums.OrderStatusEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_flowers")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Contact customer;

    @Column(name = "order_description", nullable = false)
    private String orderDescription;

    @Column(nullable = false)
    private Integer sum;

    @ManyToOne
    @JoinColumn(name = "order_handler_manager_id", nullable = false)
    private User handlerManager;

    @ManyToOne
    @JoinColumn(name = "receive_manager_id", nullable = false)
    private User receiveManager;

    @ManyToOne
    @JoinColumn(name = "delivery_manager_id", nullable = false)
    private User deliveryManager;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Contact recipient;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderHistory> orderHistory;

    public Order(){
        orderHistory = new ArrayList<OrderHistory>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public List<OrderHistory> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<OrderHistory> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public User getHandlerManager() {
        return handlerManager;
    }

    public void setHandlerManager(User handlerManager) {
        this.handlerManager = handlerManager;
    }

    public User getDeliveryManager() {
        return deliveryManager;
    }

    public void setDeliveryManager(User deliveryManager) {
        this.deliveryManager = deliveryManager;
    }

    public Contact getRecipient() {
        return recipient;
    }

    public void setRecipient(Contact recipient) {
        this.recipient = recipient;
    }

    public User getReceiveManager() {

        return receiveManager;
    }

    public void setReceiveManager(User receiveManager) {
        this.receiveManager = receiveManager;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public Contact getCustomer() {
        return customer;
    }

    public void setCustomer(Contact customer) {
        this.customer = customer;
    }
}
