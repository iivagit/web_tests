package selenium.tests;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import selenium.page_objects.StackoverflowPage;

public class TestNGExample {

	public WebDriver driver;
	EventFiringWebDriver driver_e;

	// data provider pattern
	@DataProvider(name = "data-provider-link-number")
	public Object[][] createDriver() {
		return new Object[][] { { Integer.valueOf(2) }, };
	}

	@Test(dataProvider = "data-provider-link-number", priority = 0)
	public void openChromeDriver(int number) {

		StackoverflowPage page = PageFactory.initElements(driver_e, StackoverflowPage.class);
		page.find(number);
	}

	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
		System.setProperty("webdriver.chrome.logfile", "C:\\Selenium\\chromedriver.log");

		// By default ChromeDriver logs only warnings/errors to stderr.
		// When debugging issues, it is helpful to enable more verbose logging
		System.setProperty("webdriver.chrome.verboseLogging", "true");

		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

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
		// driver.close() is used to close the current browser
		// driver.quit() is used to close all the browser instances.

		driver.close();
		try {
			System.out.println("Driver closed properly");
		} catch (Exception e) {
			System.out.println(e);
		}

		Reporter.log("Driver Closed After Testing", true);

	}

}
