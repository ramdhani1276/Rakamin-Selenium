package SauceDemo;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

public class LoginDDT {
    @Test
    public void login_ddt() {
        String baseUrl = "https://www.saucedemo.com/";  // ✅ fixed missing "www"
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //options.setHeadless(false); //options you can change this to true if running in CI

        String csvPath = System.getProperty("user.dir") + "/src/test/data/test-data.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                if (nextLine.length < 3) {
                    System.out.println("Skipping invalid row: " + String.join(",", nextLine));
                    continue;
                }

                String username = nextLine[0].trim();
                String password = nextLine[1].trim();
                String expectedStatus = nextLine[2].trim().toLowerCase();

                WebDriver driver = new ChromeDriver(options);

                try {
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    driver.manage().window().maximize();
                    driver.get(baseUrl);

                    // Fill login form
                    driver.findElement(By.id("user-name")).sendKeys(username);
                    driver.findElement(By.id("password")).sendKeys(password);
                    driver.findElement(By.id("login-button")).click();

                    if (expectedStatus.equals("success")) {
                        // Check login success
                        String actual = driver.findElement(By.className("title")).getText();
                        Assert.assertEquals("Products", actual);
                        System.out.println("✅ Login success for: " + username);
                    } else {
                        // Check login failed
                        String errorText = driver.findElement(By.cssSelector("[data-test='error']")).getText();
                        Assert.assertTrue(errorText.contains("Username and password do not match"));
                        System.out.println("✅ Login failed as expected for: " + username);
                    }

                } catch (Exception e) {
                    System.err.println("❌ Test failed for: " + username);
                    e.printStackTrace();
                } finally {
                    driver.quit(); // ✅ use quit instead of close to end session properly
                }
            }

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
