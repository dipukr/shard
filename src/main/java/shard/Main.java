package shard;

import java.time.LocalDateTime;

public class Main {
	public static void main(String[] args) throws Exception {
		ShardManager shardManager = new ShardManager();
		OrderService orderService = new OrderService(shardManager);

		Order o1 = new Order(1L, LocalDateTime.now(), 199.99, "NEW");
		Order o2 = new Order(2L, LocalDateTime.now(), 299.50, "NEW");
		Order o3 = new Order(3L, LocalDateTime.now(), 9.99, "NEW");
		Order o4 = new Order(4L, LocalDateTime.now(), 49.99, "NEW");

		orderService.createOrder(o1);
		orderService.createOrder(o2);
		orderService.createOrder(o3);
		orderService.createOrder(o4);

		System.out.println("Orders for customer 2:");
		orderService.getOrdersByCustomerId(2L).forEach(System.out::println);

		shardManager.close();
	}
}
