package com.testing.tests.web;

import com.testing.config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class BaseWebTest {
    protected WebDriver driver;
    protected Duration timeout;

    @BeforeClass
    public void setUpWeb() {
        String browser = Config.getOrDefault("web.browser", "chrome");
        boolean headless = Config.getBoolean("web.headless", false);
        timeout = Duration.ofSeconds(Long.parseLong(Config.getOrDefault("web.timeoutSeconds", "12")));

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions fx = new FirefoxOptions();
                if (headless) {
                    fx.addArguments("-headless");
                }
                driver = new FirefoxDriver(fx);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edge = new EdgeOptions();
                // Selenium 3 EdgeOptions doesn't support addArguments reliably
                driver = new EdgeDriver(edge);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chrome = new ChromeOptions();
                if (headless) {
                    chrome.addArguments("--headless");
                }
                chrome.addArguments("--window-size=1400,900");
                driver = new ChromeDriver(chrome);
                break;
        }
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownWeb() {
        if (driver != null) {
            driver.quit();
        }
    }
}
