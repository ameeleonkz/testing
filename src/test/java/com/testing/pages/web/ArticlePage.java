package com.testing.pages.web;

import com.testing.utils.WebElementFinder;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ArticlePage {
    private final WebElementFinder finder;

    public ArticlePage(WebDriver driver, WebElementFinder finder) {
        this.finder = finder;
    }

    public boolean hasTitle() {
        List<By> title = Arrays.asList(
            By.cssSelector("h1"),
            By.cssSelector(".story__title"),
            By.cssSelector(".post__title")
        );
        try {
            return finder.visible(title).isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }
}
