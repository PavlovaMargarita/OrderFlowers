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

    @Column(name = "recipient_id", nullable = false)
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
}
