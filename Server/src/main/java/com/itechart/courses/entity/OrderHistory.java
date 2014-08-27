package com.itechart.courses.entity;

import com.itechart.courses.bl.enums.OrderStatusEnum;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "order_history")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
	
	@Column(name = "change_date", nullable = false)
	private Date changeDate;

    @Column(nullable = true, length = 255)
	private String comment;


    public OrderHistory(){}

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

        if (!changeDate.equals(that.changeDate)) return false;
        if (!comment.equals(that.comment)) return false;
        if (!id.equals(that.id)) return false;
        if (!order.equals(that.order)) return false;
        if (status != that.status) return false;
        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + order.hashCode();
        result = 31 * result + changeDate.hashCode();
        result = 31 * result + comment.hashCode();
        return result;
    }
}
