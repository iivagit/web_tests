package selenium.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

// Page Object pattern
public class GooglePage {

	private final WebDriver driver;
	private final String strSearch;
	private final boolean luckySearch;
	PageProperty pageProperty;

	@FindBy(how = How.NAME, using = "q")
	private WebElement fieldSearch;

//	@FindBy(how = How.XPATH, using = "//input[@class=\"RNmpXc\"]")
//	@FindBy(how = How.XPATH, using = "//input[contains(@name='btnI')]")
	@FindBy(how = How.XPATH, using = "//*[@id=\"tsf\"]/div[2]/div[1]/div[3]/center/input[2]")
	private WebElement fieldLucky;

	public GooglePage(GooglePageBuilder builder) {
		this.driver = builder.driver;
		this.strSearch = builder.strSearch;
		this.luckySearch = builder.luckySearch;

		PageFactory.initElements(driver, this);
		driver.get("https://www.google.com/");

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
			;
			title = driver.getTitle();
		}

		public PageProperty assertURL() {

//			There are two types of Assert:
//			- Hard Assert
//			- Soft Assert

			Assert.assertEquals(url, "https://www.google.com/", "check URL");
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
		return this;
	}
}
