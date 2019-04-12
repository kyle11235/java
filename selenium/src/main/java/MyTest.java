
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class MyTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		// remove this when you import it to APM
		System.setProperty("webdriver.gecko.driver", "C:/Users/kylzhang/softwares/geckodriver.exe");

		driver = new FirefoxDriver();
		baseUrl = "http://129.152.145.164/test";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testMy() throws Exception {
		driver.get(baseUrl + "/");
		driver.findElement(By.name("username")).clear();
		driver.findElement(By.name("username")).sendKeys("username");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("password");

		Thread.sleep(1000);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		if (isAlertPresent()) {
			assertEquals("OK", closeAlertAndGetItsText());
		}

		Thread.sleep(1000);
		driver.findElement(By.cssSelector("input.button")).click();
		if (isAlertPresent()) {
			assertEquals("OK", closeAlertAndGetItsText());
		}
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
