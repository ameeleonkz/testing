package com.testing.tests.mobile;

import com.testing.config.Config;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public abstract class BaseMobileTest {
    protected AndroidDriver driver;
    protected Duration timeout;

    @BeforeClass
    public void setUpMobile() throws MalformedURLException {
        timeout = Duration.ofSeconds(Long.parseLong(Config.getOrDefault("appium.timeoutSeconds", "12")));

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", Config.get("appium.platformName"));
        caps.setCapability("deviceName", Config.get("appium.deviceName"));
        caps.setCapability("automationName", Config.get("appium.automationName"));
        caps.setCapability("newCommandTimeout", Long.parseLong(Config.getOrDefault("appium.newCommandTimeout", "120")));
        caps.setCapability("noReset", Boolean.parseBoolean(Config.getOrDefault("appium.noReset", "true")));

        String appPath = Config.getOrDefault("appium.app", "").trim();
        if (!appPath.isEmpty()) {
            caps.setCapability("app", appPath);
        }
        caps.setCapability("appPackage", Config.get("appium.appPackage"));
        caps.setCapability("appActivity", Config.get("appium.appActivity"));

        driver = new AndroidDriver(new URL(Config.get("appium.serverUrl")), caps);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownMobile() {
        if (driver != null) {
            driver.quit();
        }
    }
}
