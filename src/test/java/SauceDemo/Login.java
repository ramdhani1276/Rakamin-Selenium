package SauceDemo;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Login {
    @Test
    public void success_login_case(){
        WebDriver driver;
        String baseUrl = "https://saucedemo.com/";

        WebDriverManager.chromedriver().setup();

        //apply chrome driver setup
        //Membuka halaman login
        driver = new ChromeDriver();
        driver.manage().window().maximize();//
        driver.get(baseUrl);
        String loginPageAssert = driver.findElement(By.xpath("//div[contains(text(),'Swag Labs')]")).getText();
        Assert.assertEquals(loginPageAssert, "Swag Labs");
        //input email
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        //input password
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        //click login
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        //Assert halaman produk di dashboard page
        String halamanProduk = driver.findElement(By.xpath("//span[contains(text(),'Products')]")).getText();
        Assert.assertEquals(halamanProduk, "Products");

        //quit
        driver.close();
    }

    @Test
    public void failed_login_Case(){
        WebDriver driver;
        String baseUrl = "https://saucedemo.com/";

        WebDriverManager.chromedriver().setup();

        //apply chrome driver setup
        //Membuka halaman login
        driver = new ChromeDriver();
        driver.manage().window().maximize();//
        //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.get(baseUrl);
        String loginPageAssert = driver.findElement(By.xpath("//div[contains(text(),'Swag Labs')]")).getText();
        Assert.assertEquals(loginPageAssert, "Swag Labs");
        //input email
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        //input password yang salah
        driver.findElement(By.id("password")).sendKeys("secret");
        //click login
        driver.findElement(By.xpath("//*[@id=\"login-button\"]")).click();

        String errorLogin = driver.findElement(By.xpath("//h3[contains(text(),'Epic sadface: Username and password do not match any user in this service')]")).getText();
        Assert.assertEquals(errorLogin, "Epic sadface: Username and password do not match any user in this service");

        driver.close();
    }
}
