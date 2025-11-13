package shard;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ShardManager {

	private Map<Integer, EntityManagerFactory> emfs = new HashMap<>();
	public static final int SHARD_COUNT = 4;

	public ShardManager() {
		emfs.put(0, Persistence.createEntityManagerFactory("shard0"));
		emfs.put(1, Persistence.createEntityManagerFactory("shard1"));
		emfs.put(2, Persistence.createEntityManagerFactory("shard2"));
		emfs.put(3, Persistence.createEntityManagerFactory("shard3"));
	}

	public int getShardForCustomer(Long customerId) {
		if (customerId == null)
			throw new IllegalArgumentException("customerId cannot be null");
		return (int) (customerId % SHARD_COUNT);
	}

	public EntityManagerFactory getEmfForShard(int shardId) {
		EntityManagerFactory emf =	emfs.get(shardId);
		if (emf == null)
			throw new IllegalArgumentException("No EMF for shard " + shardId);
		return emf;
	}

	public void close() {
		emfs.values().forEach(EntityManagerFactory::close);
	}
}
