package com.testing.pages.web;

import com.testing.utils.WebElementFinder;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage {
    private final WebDriver driver;
    private final WebElementFinder finder;

    public SearchPage(WebDriver driver, WebElementFinder finder) {
        this.driver = driver;
        this.finder = finder;
    }

    private List<By> searchInputLocators() {
        return Arrays.asList(
            By.cssSelector("input[type='search']"),
            By.cssSelector("input[placeholder*='??????']"),
            By.cssSelector("input[placeholder*='??????']")
        );
    }

    public boolean hasSearchInput() {
        try {
            return finder.visible(searchInputLocators()).isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public SearchPage searchFor(String query) {
        WebElement input = finder.visible(searchInputLocators());
        input.clear();
        input.sendKeys(query);

        List<By> submit = Arrays.asList(
            By.xpath("//button[contains(., '?????')]"),
            By.cssSelector(".Btn__btn_primary"),
            By.cssSelector("button[aria-label*='?????']"),
            By.cssSelector("button[aria-label*='Search']")
        );

        try {
            finder.clickable(submit).click();
        } catch (RuntimeException e) {
            input.sendKeys(Keys.ENTER);
        }

        return this;
    }

    public boolean hasResults() {
        List<By> results = Arrays.asList(
            By.cssSelector(".stories-search .story"),
            By.cssSelector(".stories-search__feed .story"),
            By.cssSelector(".stories-feed .story"),
            By.cssSelector(".story")
        );
        try {
            finder.present(results);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean hasNoResultsMessage() {
        try {
            return driver.findElement(
                By.xpath("//*[contains(., '?????? ?? ???????') or contains(., '?????? ?? ???????') or contains(., 'No results')]")
            ).isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }
}
