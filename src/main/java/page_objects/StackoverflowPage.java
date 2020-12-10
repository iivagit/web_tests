package page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StackoverflowPage {

	WebDriver driver;
	@FindBy(how = How.XPATH, using = ".//a[@class=\"question-hyperlink\"]")
	WebElement fieldSearch;

	public StackoverflowPage(WebDriver driver) {
		this.driver = driver;
		driver.get("https://stackoverflow.com/questions?pagesize=10");
	}

	public void find() {

		final WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOf(fieldSearch)).click();

	}
}
