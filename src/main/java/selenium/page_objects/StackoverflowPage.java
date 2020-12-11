package selenium.page_objects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// SOLID
// Liskov Substitution Principle
// The principle defines that objects of a superclass shall be replaceable 
// with objects of its subclasses without breaking the application

// Page Object pattern
public class StackoverflowPage extends AbstractPage {

	// *********Page Variables*********
	private String baseURL;

	// *********Constructor********
	public StackoverflowPage(WebDriver driver) {
		super(driver);

		// load properties
		// Create Properties class object to read properties file
		Properties obj = new Properties();

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

		baseURL = obj.getProperty("stackoverflow.path");
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

	}

	// *********Web Elements*********

	// refresh the current web page there by reloading all the web elements
	// XPath is the direct way to find the element
	// A single slash (/) is used for creating XPaths with absolute paths beginning
	// from the root node.
	// Double slash is used for creating relative XPath to start selection from
	// anywhere within the root node

	@FindBy(how = How.XPATH, using = ".//a[@class=\"question-hyperlink\"]")
	private List<WebElement> fieldSearch;

	// *********Page Methods*********
	public void find(int number) {
		final WebDriverWait wait = new WebDriverWait(driver, 5);

		wait.until(ExpectedConditions.visibilityOf(fieldSearch.get(number))).click();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
		}

		WebElement link = driver.findElement(By.linkText("About"));
		System.out.println("link =  " + link.getText());
	}

	public void find() {
		find(0);
	}
}
