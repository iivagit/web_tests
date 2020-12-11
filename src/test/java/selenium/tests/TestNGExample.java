package selenium.tests;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

import selenium.page_objects.GooglePage;
import selenium.page_objects.GooglePage.GooglePageBuilder;
import selenium.page_objects.StackoverflowPage;

public class TestNGExample {

	public WebDriver driver;
	EventFiringWebDriver driver_e;

	// data provider pattern
	@DataProvider(name = "data-provider-link-number")
	public Object[][] createDriver() {
		return new Object[][] { { Integer.valueOf(2) }, };
	}

	// The default value is zero for priority
	@Test(dataProvider = "data-provider-link-number", priority = 1, enabled = true)
	public void openStackoverflowPage(int number) {

		// SOLID
		// DIP (Dependency inversion principle)

		// Page Factory pattern
		// Page Factory initializes web elements that are defined in web page classes or
		// Page Objects
		StackoverflowPage page = PageFactory.initElements(driver_e, StackoverflowPage.class);
		page.find(number);
	}

	@Test(enabled = true)
	public void openGooglePage() {

		// Chain of invocations pattern
		// Builder pattern
		GooglePage page = new GooglePageBuilder().driver(driver).strSearch("1234").luckySearch(true).build();
		page.get().lucky().find().clear();

	}

	@Test(enabled = true)
	public void checkGooglePage() {

		// Loadable Component pattern
		GooglePage page = new GooglePageBuilder().driver(driver).build();

		// After the get() method is called, the component will be loaded and ready for
		// use.
		page.get().verifyPage();
	}

	@Test(enabled = true)
	public void doStepsGoogle() {

		// Steps pattern
		GooglePage page = new GooglePageBuilder().driver(driver).build();
		page.get();
		page.doSomeSteps();
	}

	@Test(enabled = true)
	public void checkJavaScript() {

		StackoverflowPage page = PageFactory.initElements(driver, StackoverflowPage.class);
		page.find();

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		if (jse instanceof WebDriver) {
			jse.executeScript("window.history.go(0)");
			jse.executeScript("window.history.go(-1)");
			jse.executeScript("window.history.forward(-1)");
		}
	}

	@Test(enabled = true)
	public void getScreenshot() {

		PageFactory.initElements(driver, StackoverflowPage.class);

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("C:\\Selenium\\screenshot1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		// a webpage needs to be loaded – the pageLoadTimeout limits the time that the
		// script allots for a web page to be displayed.
		// If the page does not load within the timeout the script will be stopped by a
		// TimeoutException.
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

		// The implicit wait will tell the WebDriver to wait a certain amount of time
		// before it throws a "No Such Element Exception."
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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
		// driver.close() is used to close the current browser
		// driver.quit() is used to close all the browser instances.

		driver.quit();
		try {
			System.out.println("Driver closed properly");
		} catch (Exception e) {
			System.out.println(e);
		}

		Reporter.log("Driver Closed After Testing", true);
	}

	// BeforeSuite / AfterSuite
	// BeforeTest / AfterTest
	// BeforeClass / AfterClass
	// BeforeMethod / AfterMethod

}
