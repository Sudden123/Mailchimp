package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class StepDefinitions {

	private WebDriver driver;

	// Method that creates random emails
	private String RandomEmail() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		StringBuilder email = new StringBuilder();
		Random rnd = new Random();
		while (email.length() < 10) {
			int index = (int) (rnd.nextFloat() * letters.length());
			email.append(letters.charAt(index));
		}
		String RndEmail = email.toString();
		return RndEmail;

	}

	// Method that creates random usernames
	private String RandomUser() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		StringBuilder user = new StringBuilder();
		Random rnd = new Random();
		while (user.length() < 10) {
			int index = (int) (rnd.nextFloat() * letters.length());
			user.append(letters.charAt(index));
		}
		String RndUser = user.toString();
		return RndUser;

	}

	// Method that creates random usernames with 101 letters or numbers (I now random not necissary but i like it)
	private String RandomUser100() {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		StringBuilder user101 = new StringBuilder();
		Random rnd = new Random();
		while (user101.length() < 102) {
			int index = (int) (rnd.nextFloat() * letters.length());
			user101.append(letters.charAt(index));
		}
		String RndUser = user101.toString();
		return RndUser;

	}

	// Creates explicit wait method that wait until button is clickable
	private void click(By by) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
		driver.findElement(by).click();
	}

	// Creates explicit wait method that wait until textbox is found
	private void sendKeys(By by, String keys) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(by));
		driver.findElement(by).sendKeys(keys);
	}

	@Given("I have entered Mailchimp site")
	public void i_have_entered_mailchimp_site() {
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\chromedriver.exe");

		driver = new ChromeDriver(); // Starts chrome
		driver.manage().window().maximize();
		driver.get("https://login.mailchimp.com/signup/"); // Goes to site
		
		// Scrolls down so that this code works on smaller screens
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)");
	}

	@Given("I have typed in {string}")
	public void i_have_typed_in(String email) {

		// Checks if correct email is run then types a random email
		if (email.equals("CorrectEmail")) {
			sendKeys(By.id("email"), (RandomEmail() + "@gmail.com"));

		} else if (email.equals("NoEmail")) { // If no email is run, types no symbols in textbox
			sendKeys(By.id("email"), "");
		}
	}

	@Given("I have also typed in {string}")
	public void i_have_also_typed_in(String username) {

		// Checks if correct username is run then types a random username
		if (username.equals("CorrectUsername")) {
			sendKeys(By.id("new_username"), RandomUser());

		} else if (username.equals("100+UserName")) { // If username with 100+ symbols is run, then random username with
														// 101 symbols is written
			sendKeys(By.id("new_username"), RandomUser100());

		} else if (username.equals("UserAlreadyExist")) { // If UserAlreadyExist is run then types Sunken_80_SE
			sendKeys(By.id("new_username"), "Sunken_80_SE");
		}

	}

	@Given("I have as well typed in {string}")
	public void i_have_also_typed_inn(String password) {
		// Types in predetermined password
		sendKeys(By.id("new_password"), password);
	}

	@Then("I press sign up and verify {string} of account")
	public void i_press_sign_up_and_verify_of_account(String message) {
		// Clicks Sign up button
		click(By.id("create-account"));

		String expected = "";
		String actual = "";

		// Different asserts that check if testcase have been succesfull
		if (message.equals("Check your email")) {
			expected = message;
			actual = driver.findElement(By.xpath("//*[@id=\"signup-content\"]/div/div/div/h1"))
					.getAttribute("textContent");
			driver.quit();

		} else if (message.equals("Please enter a value")) {
			expected = message;
			actual = driver.findElement(By.className("invalid-error")).getAttribute("textContent");
			driver.quit();

		} else if (message.equals("Enter a value less than 100 characters long")) {
			expected = message;
			actual = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"))
					.getAttribute("textContent");
			driver.quit();

		} else if (message
				.equals("Another user with this username already exists. Maybe it's your evil twin. Spooky.")) {
			expected = message;
			actual = driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"))
					.getAttribute("textContent");
			driver.quit();
		}
		assertEquals(expected, actual);
	}
}
