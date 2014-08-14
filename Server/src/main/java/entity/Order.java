package entity;

import javax.persistence.*;

@Entity
@Table(name = "order")
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

    @Column(name = "order_handler_manager_id", nullable = false)
    private Integer orderHandlerManagerId;

    @Column(name = "receive_manager_id", nullable = false)
    private Integer receiveManagerId;

    @Column(name = "delivery_manager_id", nullable = false)
    private Integer deliveryManagerId;

    @Column(name = "recipient_id",nullable = false)
    private Integer recipientId;


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

    public Integer getOrderHandlerManagerId() {
        return orderHandlerManagerId;
    }

    public void setOrderHandlerManagerId(Integer orderHandlerManagerId) {
        this.orderHandlerManagerId = orderHandlerManagerId;
    }

    public Integer getReceiveManagerId() {
        return receiveManagerId;
    }

    public void setReceiveManagerId(Integer receiveManagerId) {
        this.receiveManagerId = receiveManagerId;
    }

    public Integer getDeliveryManagerId() {
        return deliveryManagerId;
    }

    public void setDeliveryManagerId(Integer deliveryManagerId) {
        this.deliveryManagerId = deliveryManagerId;
    }

    public Integer getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Integer recipientId) {
        this.recipientId = recipientId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (customerId != null ? !customerId.equals(order.customerId) : order.customerId != null) return false;
        if (deliveryManagerId != null ? !deliveryManagerId.equals(order.deliveryManagerId) : order.deliveryManagerId != null)
            return false;
        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (orderDescription != null ? !orderDescription.equals(order.orderDescription) : order.orderDescription != null)
            return false;
        if (orderHandlerManagerId != null ? !orderHandlerManagerId.equals(order.orderHandlerManagerId) : order.orderHandlerManagerId != null)
            return false;
        if (receiveManagerId != null ? !receiveManagerId.equals(order.receiveManagerId) : order.receiveManagerId != null)
            return false;
        if (recipientId != null ? !recipientId.equals(order.recipientId) : order.recipientId != null) return false;
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
        result = 31 * result + (orderHandlerManagerId != null ? orderHandlerManagerId.hashCode() : 0);
        result = 31 * result + (receiveManagerId != null ? receiveManagerId.hashCode() : 0);
        result = 31 * result + (deliveryManagerId != null ? deliveryManagerId.hashCode() : 0);
        result = 31 * result + (recipientId != null ? recipientId.hashCode() : 0);
        return result;
    }
}
