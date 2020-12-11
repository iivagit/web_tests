package selenium.threads;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

// docs: https://www.inviul.com/multithreading-selenium/
public class MultiThreadingExample extends Thread {
	private WebDriver driver;
	private String Url;
	private String browsertype;

	public MultiThreadingExample(String name, String browsertype) {
		super(name);
		this.browsertype = browsertype;
	}

	@Override
	public void run() {
		System.out.println("Thread Started" + Thread.currentThread().getName());
		String threadname = Thread.currentThread().getName();
		System.out.println(threadname);
		try {
			Thread.sleep(1000);
			setUp(this.browsertype);
			testGoogleSearch();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tearDown();
		}
		System.out.println("Thread END " + Thread.currentThread().getName());
	}

	// main method to create the thread and run multiple threads
	public static void main(String[] args) {
		Thread t1 = new MultiThreadingExample("Thread Chrome", "Chrome");
		Thread t2 = new MultiThreadingExample("Thread Firefox", "Firefox");
		System.out.println("Starting MyThreads");
		t1.start();
		t2.start();
		System.out.println("Thread has been started");
	}

	// set up the method to initialize driver object
	public void setUp(String browsertype) throws Exception {
		if (browsertype.contains("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browsertype.contains("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		Url = "https://www.google.com/";

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(Url);
	}

	// test scripts
	public void testGoogleSearch() throws Exception {
		String actualtitle = driver.getTitle();
		String expectedTitle = "Google";
		Assert.assertEquals(actualtitle, expectedTitle, "The expected title matched");
		System.out.println("Test Results -  Actual:" + actualtitle + "  Expected:" + expectedTitle);
	}

	// tear down function to close browser
	public void tearDown() {
		driver.quit();
	}
}
