package com.testing.pages.web;

import com.testing.utils.WebElementFinder;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SectionPage {
    private final WebElementFinder finder;

    public SectionPage(WebDriver driver, Duration timeout) {
        this.finder = new WebElementFinder(driver, timeout);
    }

    public boolean hasHeader() {
        List<By> headers = Arrays.asList(
            By.cssSelector("h1"),
            By.cssSelector(".main__title"),
            By.cssSelector(".layout__title")
        );
        try {
            return finder.visible(headers).isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }
}
