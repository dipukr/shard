package shard;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class OrderService {

	private ShardManager shardManager;

	public OrderService(ShardManager shardManager) {
		this.shardManager = shardManager;
	}

	public void createOrder(Order order) {
		int shardId = shardManager.getShardForCustomer(order.getCustomerId());
		EntityManagerFactory emf = shardManager.getEmfForShard(shardId);
		EntityManager em = emf.createEntityManager();
		try {
			OrderDAO orderDAO = new OrderDAO(em);
			if (order.getOrderDate() == null)
				order.setOrderDate(LocalDateTime.now());
			orderDAO.save(order);
			System.out.println("Order written to shard " + shardId);
		} finally {
			em.close();
		}
	}

	public List<Order> getOrdersByCustomerId(Long customerId) {
		int shardId = shardManager.getShardForCustomer(customerId);
		EntityManagerFactory emf = shardManager.getEmfForShard(shardId);
		EntityManager em = emf.createEntityManager();
		try {
			OrderDAO orderDAO = new OrderDAO(em);
			return orderDAO.findByCustomerId(customerId);
		} finally {
			em.close();
		}
	}

	public Optional<Order> findByOrderIdAcrossShards(UUID orderId) {
		for (int s = 0; s < ShardManager.SHARD_COUNT; s++) {
			EntityManager em = shardManager.getEmfForShard(s).createEntityManager();
			try {
				OrderDAO orderDAO = new OrderDAO(em);
				Optional<Order> opt = orderDAO.findById(orderId);
				if (opt.isPresent()) return opt;
			} finally {
				em.close();
			}
		}
		return Optional.empty();
	}
}