package com.testing.utils;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MobileElementFinder {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public MobileElementFinder(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout.getSeconds());
    }

    public WebElement visible(By... locators) {
        return visible(Arrays.asList(locators));
    }

    public WebElement visible(List<By> locators) {
        RuntimeException last = null;
        for (By locator : locators) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            } catch (RuntimeException e) {
                last = e;
            }
        }
        if (last != null) {
            throw last;
        }
        throw new RuntimeException("No visible element found");
    }

    public WebElement clickable(By... locators) {
        return clickable(Arrays.asList(locators));
    }

    public WebElement clickable(List<By> locators) {
        RuntimeException last = null;
        for (By locator : locators) {
            try {
                return wait.until(ExpectedConditions.elementToBeClickable(locator));
            } catch (RuntimeException e) {
                last = e;
            }
        }
        if (last != null) {
            throw last;
        }
        throw new RuntimeException("No clickable element found");
    }
}
