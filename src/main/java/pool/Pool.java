package pool;

public interface Pool<T> {

	/*
	 * @return one of the pooled objects.
	 */
	T get();

	/**
	 * Shuts down the pool. Should release all resources.
	 */
	void shutdown();
}
