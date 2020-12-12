package selenium.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

// SOLID
// Open Closed principle
// "Software entities should be open for extension, but closed for modification" 

// SOLID
// Interface Segregation Principle
// "Clients should not be forced to depend upon interfaces that they do not use."

public class ObjectPool implements Pool<ChromeDriver>, ObjectFactory<ChromeDriver> {

	private int size;

	private boolean shutdown;
	private BlockingQueue<ChromeDriver> objects;

	// tread with return value
	public class MyTask implements Callable<ChromeDriver> {
		@Override
		public ChromeDriver call() throws Exception {
			// Here your implementation
			return new ChromeDriver();
		}
	}

	public ObjectPool(int size) {
		this.size = size;
		this.shutdown = false;

		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");

		objects = new LinkedBlockingQueue<ChromeDriver>();

		List<ChromeDriver> results = createList();

		for (ChromeDriver item : results) {
			objects.add(item);
		}

	}

	/**
	 * get an object from the pool
	 */

	public ChromeDriver get() {
		ChromeDriver t = null;
		if (!shutdown) {

			try {
				t = objects.take();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return t;
	}

	/**
	 * Shuts down the pool. Should release all resources.
	 */
	public void shutdown() {

		for (WebDriver driver : objects) {
			driver.close();
		}

		objects.clear();
	}

	public int size() {
		return objects.size();
	}

	@Override
	public List<ChromeDriver> createList() {
		List<ChromeDriver> results = new ArrayList<>();

		ExecutorService executorService = Executors.newFixedThreadPool(this.size);

		Callable<ChromeDriver> callableTask = () -> {
			TimeUnit.MILLISECONDS.sleep(300);
			return new ChromeDriver();
		};

		List<Callable<ChromeDriver>> callableTasks = new ArrayList<>();
		callableTasks.add(callableTask);
		callableTasks.add(callableTask);
		callableTasks.add(callableTask);
		try {
			List<Future<ChromeDriver>> futures = executorService.invokeAll(callableTasks);

			results = futures.stream().map(future -> {
				try {
					return future.get();
				} catch (InterruptedException | ExecutionException e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return results;
	}
}
