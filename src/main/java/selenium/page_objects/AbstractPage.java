package selenium.page_objects;

import org.openqa.selenium.WebDriver;

public class AbstractPage {
	protected WebDriver driver;

	protected AbstractPage(WebDriver driver) {
		this.driver = driver;
	}
}
