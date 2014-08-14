package entity;

import bl.enums.OrderStatusEnum;
import org.hibernate.type.*;

import javax.persistence.*;
import javax.persistence.EnumType;
import java.util.Date;

@Entity
@Table(name = "order_history")
@Access(value = AccessType.FIELD)
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
	
	@Column(name = "change_date", nullable = false)
    @Temporal(TemporalType.DATE)
	private Date changeDate;

    @Column(nullable = true, length = 255)
	private String comment;


    public OrderHistory(){
    }

    @Access(value = AccessType.PROPERTY)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }
    
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderHistory that = (OrderHistory) o;

        if (changeDate != null ? !changeDate.equals(that.changeDate) : that.changeDate != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (status != that.status) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (changeDate != null ? changeDate.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
