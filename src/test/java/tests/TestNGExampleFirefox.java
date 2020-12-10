package tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import page_objects.StackoverflowPage;

public class TestNGExampleFirefox {

	public WebDriver driver;

	@Test
	public void openChromeDriver() {
		StackoverflowPage page = PageFactory.initElements(driver, StackoverflowPage.class);
		page.find();
	}

	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver.exe");
		driver = new FirefoxDriver();

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(4, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
		try {
			System.out.println("Driver closed properly");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
