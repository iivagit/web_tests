package selenium.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Page Object pattern
public class StackoverflowPage extends AbstractPage {

	@FindBy(how = How.XPATH, using = ".//a[@class=\"question-hyperlink\"]")
	@CacheLookup
	private WebElement fieldSearch;

	public StackoverflowPage(WebDriver driver) {
		super(driver);
	}

	public void find() {
		final WebDriverWait wait = new WebDriverWait(driver, 5);

		driver.get("https://stackoverflow.com/questions?pagesize=10");
		wait.until(ExpectedConditions.visibilityOf(fieldSearch)).click();

	}
}
