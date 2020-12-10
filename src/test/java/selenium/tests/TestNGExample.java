package selenium.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import selenium.page_objects.StackoverflowPage;

public class TestNGExample {

	public WebDriver driver;

	// data provider pattern
	@DataProvider(name = "data-provider-link-number")
	public Object[][] createDriver() {
		return new Object[][] { { Integer.valueOf(2) }, };
	}

	@Test(dataProvider = "data-provider-link-number")
	public void openChromeDriver(int number) {
		StackoverflowPage page = PageFactory.initElements(driver, StackoverflowPage.class);
		page.find(number);
	}

	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
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
