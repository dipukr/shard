package shard;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class OrderDAO {

	private EntityManager em;

	public OrderDAO(EntityManager em) {
		this.em = em;
	}

	public void save(Order order) {
		em.getTransaction().begin();
		em.persist(order);
		em.getTransaction().commit();
	}

	public Order findById(UUID orderId) {
		return em.find(Order.class, orderId);
	}

	public List<Order> findByCustomerId(Long customerId) {
		TypedQuery<Order> q = 
				em.createQuery("SELECT o FROM Order o WHERE o.customerId = :cid", Order.class);
		q.setParameter("cid", customerId);
		return q.getResultList();
	}

	public List<Order> findAll() {
		TypedQuery<Order> q = em.createQuery("SELECT o FROM Order o", Order.class);
		return q.getResultList();
	}
}