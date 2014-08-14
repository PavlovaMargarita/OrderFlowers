package entity;

import bl.enums.OrderStatusEnum;
import org.hibernate.type.*;

import javax.persistence.*;
import javax.persistence.EnumType;
import java.sql.Date;

@Entity
@Table(name = "order_history")
@Access(value = AccessType.FIELD)
public class OrderHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "user_id", nullable = false)
	private Integer userId;

    @Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatusEnum status;
	
	@Column(name = "order_id", nullable = false)
	private Integer orderId;
	
	@Column(name = "change_date", nullable = false)
    @Temporal(TemporalType.DATE)
	private Date changeDate;

    @Column(nullable = true, length = 255)
	private String comment;

    public OrderHistory(){

    }

	@Access(value = AccessType.PROPERTY)
	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatusId(OrderStatusEnum status) {
        this.status = status;
    }

    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderHistory that = (OrderHistory) o;

        if (changeDate != null ? !changeDate.equals(that.changeDate) : that.changeDate != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (status != that.status) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (changeDate != null ? changeDate.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
