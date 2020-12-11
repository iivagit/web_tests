package pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
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

	public ObjectPool(int size) {
		this.size = size;
		this.shutdown = false;

		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");

		objects = new LinkedBlockingQueue<ChromeDriver>();

		for (int i = 0; i < this.size; i++) {
			objects.add(createNew());
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

	public ChromeDriver createNew() {
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(100, 100));
		driver.manage().window().setPosition(new Point(-2000, 0));
		return driver;
	}

	public int size() {
		return objects.size();
	}
}
