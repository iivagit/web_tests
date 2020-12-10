package selenium.page_objects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Page Object pattern
public class StackoverflowPage extends AbstractPage {

	@FindBy(how = How.XPATH, using = ".//a[@class=\"question-hyperlink\"]")
	private List<WebElement> fieldSearch;

	public StackoverflowPage(WebDriver driver) {
		super(driver);
	}

	public void find(int number) {
		final WebDriverWait wait = new WebDriverWait(driver, 5);

		driver.get("https://stackoverflow.com/questions?pagesize=10");
		wait.until(ExpectedConditions.visibilityOf(fieldSearch.get(number))).click();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
		}
	}

	public void find() {
		find(0);
	}
}
