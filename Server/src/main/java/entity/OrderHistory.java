package entity;

import javax.persistence.*;
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
	
	@Column(name = "status_id", nullable = false)
	private Short statusId;
	
	@Column(name = "order_id", nullable = false)
	private Integer orderId;
	
	@Column(name = "change_date", nullable = false)
	private Date changeDate;
	
	@Column
	private String comment;

	@Access(value = AccessType.PROPERTY)
	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public Short getStatusId() {
		return statusId;
	}

	public void setStatusId(Short statusId) {
		this.statusId = statusId;
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
}
