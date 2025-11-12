package shard;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ShardManager {

	public static final int SHARD_COUNT = 4;
	private Map<Integer, EntityManagerFactory> emfMap = new HashMap<>();

	public ShardManager() {
		emfMap.put(0, Persistence.createEntityManagerFactory("shard0"));
		emfMap.put(1, Persistence.createEntityManagerFactory("shard1"));
		emfMap.put(2, Persistence.createEntityManagerFactory("shard2"));
		emfMap.put(3, Persistence.createEntityManagerFactory("shard3"));
	}

	public int getShardForCustomer(Long customerId) {
		if (customerId == null)
			throw new IllegalArgumentException("customerId cannot be null");
		return (int) (customerId % SHARD_COUNT);
	}

	public EntityManagerFactory getEmfForShard(int shardId) {
		EntityManagerFactory emf = emfMap.get(shardId);
		if (emf == null)
			throw new IllegalArgumentException("No EMF for shard " + shardId);
		return emf;
	}

	public void close() {
		emfMap.values().forEach(EntityManagerFactory::close);
	}
}
