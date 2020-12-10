package selenium.page_objects;

import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Page Object pattern
public class StackoverflowPage extends AbstractPage {

	// *********Page Variables*********
	private String baseURL = "https://stackoverflow.com/questions?pagesize=10";

	// *********Constructor********
	public StackoverflowPage(WebDriver driver) {
		super(driver);

		try {
			driver.get(baseURL);
		} catch (TimeoutException te) {
			System.out.println("TimeoutException occured");
		} catch (WebDriverException e) {
			System.out.println("WebDriverException occured");
		}

	}

	// *********Web Elements*********

//	XPath is the direct way to find the element
//	A single slash (/) is used for creating XPaths with absolute paths beginning from the root node.
//	Double slash is used for creating relative XPath to start selection from anywhere within the root node

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

	}

	public void find() {
		find(0);
	}
}
