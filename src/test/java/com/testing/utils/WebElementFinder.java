package com.testing.utils;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElementFinder {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public WebElementFinder(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout.getSeconds());
    }

    public WebElement visible(By... locators) {
        return visible(Arrays.asList(locators));
    }

    public WebElement clickable(By... locators) {
        return clickable(Arrays.asList(locators));
    }

    public WebElement present(By... locators) {
        return present(Arrays.asList(locators));
    }

    public WebElement visible(List<By> locators) {
        return firstMatching(locators, true, false);
    }

    public WebElement clickable(List<By> locators) {
        return firstMatching(locators, true, true);
    }

    public WebElement present(List<By> locators) {
        return firstMatching(locators, false, false);
    }

    private WebElement firstMatching(List<By> locators, boolean requireVisible, boolean requireClickable) {
        RuntimeException lastError = null;
        for (By locator : locators) {
            try {
                if (requireClickable) {
                    return wait.until(ExpectedConditions.elementToBeClickable(locator));
                }
                if (requireVisible) {
                    return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                }
                return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            } catch (RuntimeException e) {
                lastError = e;
            }
        }
        if (lastError != null) {
            throw lastError;
        }
        throw new NoSuchElementException("No matching locator found");
    }
}
