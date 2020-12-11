package pool;

// SOLID
// S - The single responsibility principle
// "A class should have only one reason to change."
// Every module or class should have responsibility over a single part of the functionality

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
