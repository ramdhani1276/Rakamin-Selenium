package SauceDemo.Cucumber.StepDef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class login {
    String baseUrl = "https://www.saucedemo.com/";
    WebDriver driver;
    WebDriverWait wait;

    @Given("Halaman login Swag Labs")
    public void halaman_login_swag_labs() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-save-password-bubble"); // disable popup
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get(baseUrl);

        // Assertion
        String loginPageAssert = driver.findElement(By.className("login_logo")).getText();
        Assert.assertEquals("Swag Labs", loginPageAssert);
    }

    @When("Input username")
    public void input_username() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
    }

    @And("Input password")
    public void input_password() {
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
    }

    @And("Click login button")
    public void click_login_button() {
        driver.findElement(By.id("login-button")).click();
    }

    @Then("User on dashboard page")
    public void user_on_dashboard_page() {
        WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
        Assert.assertEquals("Products", productTitle.getText());
        driver.quit();
    }

    @And("Input invalid password")
    public void input_invalid_password() {
        driver.findElement(By.id("password")).sendKeys("secret");
    }

    @Then("User get error message")
    public void user_get_error_message() {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='error']")));
        Assert.assertTrue(errorMessage.getText().contains("Username and password do not match"));
        driver.quit();
    }

    @When("I input (.*) as email$")
    public void i_input_standard_user_as_email(String email) {
        driver.findElement(By.id("user-name")).sendKeys(email);
    }

    @And("I input (.*) as password$")
    public void i_input_failed_login_as_password(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }

    @Then("I verify (.*) login result$")
    public void i_verify_failed_login_result(String status) {
        if (status.equalsIgnoreCase("success")) {
            WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title")));
            Assert.assertEquals("Products", productTitle.getText());
        } else {
            WebElement errorText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
            Assert.assertTrue(errorText.getText().contains("Username and password do not match"));
        }
        driver.quit();
    }
}
