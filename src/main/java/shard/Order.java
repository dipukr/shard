package shard;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@ToString
@Table(name = "orders")
public class Order {

	@Id
	@Column(name = "order_id")
	private UUID orderId = UUID.randomUUID();

	@Column(name = "customer_id", nullable = false)
	private Long customerId;

	@Column(name = "order_date", nullable = false)
	private LocalDateTime orderDate;

	@Column(name = "amount", nullable = false)
	private Double amount;

	@Column(name = "status")
	private String status;
	
	public Order() {}
	
	public Order(Long customerId, LocalDateTime orderDate, Double amount, String status) {
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.amount = amount;
		this.status = status;
	}
}
