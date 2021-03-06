package selenium.tests;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import selenium.page_objects.GooglePage;
import selenium.page_objects.GooglePage.GooglePageBuilder;
import selenium.page_objects.StackoverflowPage;
import selenium.pool.ObjectPool;

public class TestNGExamplePool {

	public WebDriver driver;
	EventFiringWebDriver driver_e;
	final ObjectPool pool = new ObjectPool(3);

//	Behavior Spacification example.
//	Feature: Poll verification 
//	In order to check web driver pool creation I want to open 3 different web pages as soon as possible
//	Scenario
//	Given: a web driver pool has been created
//	When: I get two web drivers from pool with different urls
//	Then: Two different web pages should be opened without significant delays.

	@Test(enabled = true)
	public void openBrowsers() {

		Optional<WebDriver> optional1 = Optional.ofNullable(pool.get());
		optional1.ifPresent(driver -> {
			driver.manage().window().maximize();
			GooglePage page = new GooglePageBuilder().driver(driver).build();
			page.get();
		});

		Optional<WebDriver> optional2 = Optional.ofNullable(pool.get());
		optional2.ifPresent(driver -> {
			driver.manage().window().maximize();
			PageFactory.initElements(driver, StackoverflowPage.class);
		});

		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
		}

		if (optional1.isPresent()) {
			optional1.get().close();
		}

		if (optional2.isPresent()) {
			optional2.get().close();
		}
	}

	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
		System.setProperty("webdriver.chrome.logfile", "C:\\Selenium\\chromedriver.log");

		// By default ChromeDriver logs only warnings/errors to stderr.
		// When debugging issues, it is helpful to enable more verbose logging
		System.setProperty("webdriver.chrome.verboseLogging", "true");

		// Object Pool/Flyweight pattern
		driver = Optional.of(pool.get()).orElseGet(() -> new ChromeDriver());

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		// a webpage needs to be loaded � the pageLoadTimeout limits the time that the
		// script allots for a web page to be displayed.
		// If the page does not load within the timeout the script will be stopped by a
		// TimeoutException.
		driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);

		// The implicit wait will tell the WebDriver to wait a certain amount of time
		// before it throws a "No Such Element Exception."
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		// decorator pattern
		driver_e = new EventFiringWebDriver(driver).register(new AbstractWebDriverEventListener() {

			@Override
			public void afterClickOn(WebElement element, WebDriver driver) {
				Reporter.log("Clicked successfull", true);
				System.out.println("Clicked successfull ");
			}

		});
	}

	@AfterClass
	public void afterClass() {
		try {
			driver.quit();
			System.out.println("Driver closed properly");
		} catch (Exception e) {
			System.out.println(e);
		}

		Reporter.log("Driver Closed After Testing", true);
		pool.shutdown();
	}

}
