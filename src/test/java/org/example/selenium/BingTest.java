package org.example.selenium;

import static org.junit.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class BingTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUpDriver() {
        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() throws Exception {
        driver.get("https://www.bing.com");
        WebElement element = driver.findElement(By.id("sb_form_q"));
        element.sendKeys("java");
        WebElement form = driver.findElement(By.cssSelector("form.sb_form"));
        form.submit();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }

    @Test
    public void checkSearchWorks() {
        WebElement element = driver.findElement(By.partialLinkText("Oracle"));
        assertNotNull(element);
    }

    @Test
    public void checkSearchResults() {
        WebElement firstResult = driver.findElement(By.cssSelector("li.b_algo > h2 > a"));
        firstResult.click();
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("https://www.java.com"));
    }

    @Test
    public void checkThirdResult() {
        WebElement thirdResult = driver.findElement(By.cssSelector("ol li:nth-of-type(3) > h2 > a"));
        System.out.println(thirdResult);
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("document.querySelector('li:nth-of-type(3) > h2 > a').click()");
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("wikipedia.org/wiki/Java"));
    }
}
