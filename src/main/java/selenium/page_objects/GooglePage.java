package selenium.page_objects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

// Page Object pattern

public class GooglePage extends LoadableComponent<GooglePage> {

	// *********Page Variables*********
	private String baseURL;
	private final WebDriver driver;
	private final String strSearch;
	private final boolean luckySearch;
	PageProperty pageProperty;

	// Create Properties class object to read properties file
	public Properties obj = new Properties();

	// *********Constructor*********
	public GooglePage(GooglePageBuilder builder) {
		this.driver = builder.driver;
		this.strSearch = builder.strSearch;
		this.luckySearch = builder.luckySearch;

		PageFactory.initElements(driver, this);

		// load properties
		// Create FileInputStream object and Load file
		FileInputStream objfile;
		try {
			objfile = new FileInputStream(System.getProperty("user.dir") + "\\Object_Repo.properties");
			obj.load(objfile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		baseURL = obj.getProperty("google.path");
		System.out.println("Property class loaded baseURL = " + baseURL);

		// The Recovery Scenario In Selenium WebDriver
		try {
			driver.get(baseURL);
		} catch (TimeoutException te) {
			System.out.println("TimeoutException occured");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException occured");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		pageProperty = new PageProperty(this.driver);
	}

	public static class GooglePageBuilder {
		private WebDriver driver;
		private String strSearch;
		private boolean luckySearch;

		public GooglePageBuilder driver(WebDriver driver) {
			this.driver = driver;
			return this;
		}

		public GooglePageBuilder strSearch(String strSearch) {
			this.strSearch = strSearch;
			return this;
		}

		public GooglePageBuilder luckySearch(boolean luckySearch) {
			this.luckySearch = luckySearch;
			return this;
		}

		public GooglePage build() {
			return new GooglePage(this);
		}
	}

	// *********Override LoadableComponent Methods*********
	// We need to go to the page at load method
	@Override
	protected void load() {
		this.driver.get(baseURL);
	}

	// We need to check that the page has been loaded.
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(driver.getCurrentUrl().contains(baseURL), "GooglePage is not loaded!");
	}

	// *********Web Elements*********
	@FindBy(how = How.NAME, using = "q")
	private WebElement fieldSearch;

	// @FindBy(how = How.XPATH, using = "//input[@class=\"RNmpXc\"]")
//	 @FindBy(how = How.XPATH, using = "//input[contains(@name='btnI')]")
	@FindBy(how = How.XPATH, using = "//*[@id=\"tsf\"]/div[2]/div[1]/div[3]/center/input[2]")
	private WebElement fieldLucky;

	// *********Page Methods*********
	public GooglePage clear() {
		final WebDriverWait wait = new WebDriverWait(driver, 5);

		wait.until(ExpectedConditions.visibilityOf(fieldSearch)).clear();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
		}

		return this;
	}

	public GooglePage find() {
		final WebDriverWait wait = new WebDriverWait(driver, 5);

		wait.until(ExpectedConditions.visibilityOf(fieldSearch)).sendKeys(strSearch);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
		}

		return this;
	}

	public GooglePage lucky() {
		if (luckySearch) {
			final WebDriverWait wait = new WebDriverWait(driver, 5);

			wait.until(ExpectedConditions.visibilityOf(fieldLucky)).click();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
		}

		return this;
	}

	// Value Object pattern

	public class PageProperty {
		String url;
		String title;

		public PageProperty(WebDriver driver) {
			super();
			url = driver.getCurrentUrl();
			title = driver.getTitle();
		}

		public PageProperty assertURL() {

			// There are two types of Assert:
			// - Hard Assert
			// - Soft Assert

			Assert.assertEquals(url, baseURL, "check URL");
			Assert.assertTrue(url.contains("google.com"));
			Assert.assertFalse(url.contains("mail.ru"));
			Assert.assertNotNull(url);

			// "verify" will not stop the test case execution if the test case fail
			SoftAssert softAssert = new SoftAssert();
			softAssert.assertNotNull(url, "URL check failed");

			// If there is any exception and you want to throw it then you need to use
			// assertAll() method as a last statemen
			softAssert.assertAll();
			return this;
		}

		public PageProperty assertTitle() {

			Assert.assertEquals(title, "Google");
			return this;
		}

		public String getUrl() {
			return url;
		}

		public String getTitle() {
			return title;
		}

	}

	// assert object pattern
	public GooglePage verifyPage() {
		pageProperty.assertURL().assertTitle();
		System.out.println("Page has url = " + pageProperty.getUrl() + " and title = " + pageProperty.getTitle());
		System.out.println("Page has windoe handler = " + GooglePage.this.driver.getWindowHandle());

		String luckyXpath = GooglePage.this.obj.getProperty("lucky.xpath");
		System.out.println("Lucky button has width = "
				+ GooglePage.this.driver.findElement(By.xpath(luckyXpath)).getSize().getWidth());
		System.out.println(
				"Lucky button has enability = " + GooglePage.this.driver.findElement(By.xpath(luckyXpath)).isEnabled());

		try {
			GooglePage.this.driver.switchTo().window(GooglePage.this.driver.getWindowHandle());
		} catch (NoSuchWindowException e) {
			System.out.println("NoSuchWindowException occured");
		}

		return this;
	}

	// steps pattern

	public class TestSteps {
		private WebDriver driver;

		public TestSteps(WebDriver driver) {
			super();
			this.driver = driver;
		}

		public void pressImFeelingLucky() {
			final WebDriverWait wait = new WebDriverWait(driver, 5);
			Actions action = new Actions(driver);

			wait.until(ExpectedConditions.visibilityOf(GooglePage.this.fieldLucky)).click();

			// to refresh the current web page there by reloading all the web elements
			driver.navigate().refresh();
			driver.getCurrentUrl();
			driver.navigate().to(driver.getCurrentUrl());

			WebElement element = driver.findElement(By.name("q"));
			action.moveToElement(element).perform();
			action.doubleClick(element).perform();

			try {
				element = driver.findElement(By.xpath(GooglePage.this.obj.getProperty("lucky.xpath")));
				action.moveToElement(element).perform();
			} catch (NoSuchElementException te) {
				System.out.println("NoSuchElementException occured");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
			}
		}
	}

	public void doSomeSteps() {
		TestSteps testSteps = new TestSteps(driver);
		testSteps.pressImFeelingLucky();
	}

}
