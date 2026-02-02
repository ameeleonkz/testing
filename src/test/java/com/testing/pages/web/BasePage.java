package com.testing.pages.web;

import com.testing.utils.WebElementFinder;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebElementFinder finder;

    protected BasePage(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.finder = new WebElementFinder(driver, timeout);
    }

    protected void clickIfPresent(By... locators) {
        for (By locator : locators) {
            try {
                WebElement el = driver.findElement(locator);
                if (el.isDisplayed()) {
                    el.click();
                    return;
                }
            } catch (RuntimeException ignored) {
            }
        }
    }
}
