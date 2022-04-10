package com.example.shopspringboot.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "`order`")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "user_id")
	private int userId;

	@Column(name = "order_status_id")
	private OrderStatus status;

	@Column(name = "order_status_details")
	private String statusDetails;

	@CreationTimestamp
	@Column(name = "order_date", nullable = false, insertable = false)
	private Timestamp date;

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
	private List<OrderProduct> products;

	public Order() {
		status = OrderStatus.ACCEPTED;
	}

	public Order(int orderId, int userId, OrderStatus status, String statusDetails, Timestamp date,
			List<OrderProduct> products) {
		this.orderId = orderId;
		this.userId = userId;
		this.status = status;
		this.statusDetails = statusDetails;
		this.date = date;
		this.products = products;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getStatusDetails() {
		return statusDetails;
	}

	public void setStatusDetails(String statusDetails) {
		this.statusDetails = statusDetails;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public List<OrderProduct> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderId=" + orderId +
				", userId=" + userId +
				", status=" + status +
				", statusDetails='" + statusDetails + '\'' +
				", date=" + date +
				", products=" + products +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Order order = (Order) o;
		return orderId == order.orderId && userId == order.userId && status == order.status &&
				Objects.equals(statusDetails, order.statusDetails) &&
				date.equals(order.date) && Objects.equals(products, order.products);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderId, userId, status, statusDetails, date, products);
	}
}