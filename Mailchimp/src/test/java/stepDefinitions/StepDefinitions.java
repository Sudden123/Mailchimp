package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

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

	// Method that creates random usernames with 101 letters or numbers
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

	@Given("i have entered Mailchimp site")
	public void i_have_entered_mailchimp_site() {
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\chromedriver.exe");

		driver = new ChromeDriver(); // Starta chrome
		driver.manage().window().maximize();
		driver.get("https://login.mailchimp.com/signup/");
	}

	@Given("I have typed in {string}")
	public void i_have_typed_in(String email) {

		if (email.equals("CorrectEmail")) {
			driver.findElement(By.id("email")).sendKeys(RandomEmail() + "@gmail.com");

		} else if (email.equals("NoEmail")) {
			driver.findElement(By.id("email")).sendKeys("");
		}
	}

	@Given("I have also typed in {string}")
	public void i_have_also_typed_in(String username) {

		if (username.equals("CorrectUsername")) {
			driver.findElement(By.id("new_username")).sendKeys(RandomUser());

		} else if (username.equals("100+UserName")) {
			driver.findElement(By.id("new_username")).sendKeys(RandomUser100());

		} else if (username.equals("UserAlreadyExist")) {
			driver.findElement(By.id("new_username")).sendKeys("Sunken_80_SE");

		}

	}

	@Given("I have as well typed in {string}")
	public void i_have_also_typed_inn(String password) {
		driver.findElement(By.id("new_password")).sendKeys(password);
	}

	@When("I press sign up an account is made")
	public void i_press_sign_up_an_account_is_made() {
		driver.findElement(By.id("create-account")).click();
	}

	@Then("I verify {string} of account")
	public void i_verify_of_account(String message) {

		if (message.equals("Check your email")) {
			driver.findElement(By.xpath("//*[@id=\"signup-content\"]/div/div/div/h1"))
					.getAttribute("textContent");
			assertEquals(message, driver.findElement(By.xpath("//*[@id=\"signup-content\"]/div/div/div/h1"))
					.getAttribute("textContent"));
			driver.quit();

		} else if (message.equals("Please enter a value")) {
			driver.findElement(By.className("invalid-error")).getAttribute("textContent");
			assertEquals(message, driver.findElement(By.className("invalid-error")).getAttribute("textContent"));
			driver.quit();

		} else if (message.equals("Enter a value less than 100 characters long")) {
			driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"))
					.getAttribute("textContent");
			assertEquals(message, driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"))
					.getAttribute("textContent"));
			driver.quit();

		} else if (message
				.equals("Another user with this username already exists. Maybe it's your evil twin. Spooky.")) {
			driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"))
					.getAttribute("textContent");
			assertEquals(message, driver.findElement(By.xpath("//*[@id=\"signup-form\"]/fieldset/div[2]/div/span"))
					.getAttribute("textContent"));
			driver.quit();
		}
	}

}
