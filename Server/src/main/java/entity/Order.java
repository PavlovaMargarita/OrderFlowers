package entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "order_flowers")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String status;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

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

    @OneToMany(mappedBy = "order")
    private List<OrderHistory> orderHistory;

    public Order(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (customerId != null ? !customerId.equals(order.customerId) : order.customerId != null) return false;
        if (deliveryManager != null ? !deliveryManager.equals(order.deliveryManager) : order.deliveryManager != null)
            return false;
        if (handlerManager != null ? !handlerManager.equals(order.handlerManager) : order.handlerManager != null)
            return false;
        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (orderDescription != null ? !orderDescription.equals(order.orderDescription) : order.orderDescription != null)
            return false;
        if (orderHistory != null ? !orderHistory.equals(order.orderHistory) : order.orderHistory != null) return false;
        if (receiveManager != null ? !receiveManager.equals(order.receiveManager) : order.receiveManager != null)
            return false;
        if (recipient != null ? !recipient.equals(order.recipient) : order.recipient != null) return false;
        if (status != null ? !status.equals(order.status) : order.status != null) return false;
        if (sum != null ? !sum.equals(order.sum) : order.sum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        result = 31 * result + (orderDescription != null ? orderDescription.hashCode() : 0);
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        result = 31 * result + (handlerManager != null ? handlerManager.hashCode() : 0);
        result = 31 * result + (receiveManager != null ? receiveManager.hashCode() : 0);
        result = 31 * result + (deliveryManager != null ? deliveryManager.hashCode() : 0);
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (orderHistory != null ? orderHistory.hashCode() : 0);
        return result;
    }
}
