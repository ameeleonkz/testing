package com.testing.pages.web;

import com.testing.config.Config;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
    private final String baseUrl = Config.get("web.baseUrl");

    public HomePage(WebDriver driver, Duration timeout) {
        super(driver, timeout);
    }

    public HomePage open() {
        driver.get(baseUrl);
        return this;
    }

    public HomePage acceptCookiesIfPresent() {
        clickIfPresent(
            By.cssSelector("button#onetrust-accept-btn-handler"),
            By.cssSelector("button[aria-label*='????????']"),
            By.cssSelector("button[aria-label*='Accept']"),
            By.xpath("//button[contains(., '????????????') or contains(., '????????') or contains(., 'Accept')]"),
            By.cssSelector(".cookie__button"),
            By.xpath("//button[contains(., '??????') or contains(., '????') or contains(., 'Accept')]")
        );
        return this;
    }

    public HomePage openSection(String hrefPart) {
        List<By> locators = Arrays.asList(
            By.cssSelector("header a[href*='" + hrefPart + "']"),
            By.cssSelector("nav a[href*='" + hrefPart + "']"),
            By.cssSelector("a[href*='" + hrefPart + "']")
        );
        WebElement link = finder.clickable(locators);
        link.click();
        return this;
    }

    public SearchPage openSearch() {
        List<By> locators = Arrays.asList(
            By.cssSelector(".header-right-menu__search"),
            By.cssSelector(".header-right-menu__item.header-right-menu__search"),
            By.cssSelector("button[aria-label*='?????']"),
            By.cssSelector("button[aria-label*='Search']"),
            By.cssSelector("a[href*='/search']")
        );
        WebElement open = finder.clickable(locators);
        open.click();
        return new SearchPage(driver, finder);
    }

    public boolean hasTopStories() {
        List<By> blocks = Arrays.asList(
            By.cssSelector(".stories-feed"),
            By.cssSelector(".stories-feed__container"),
            By.cssSelector(".story"),
            By.cssSelector(".story__title")
        );
        try {
            return finder.present(blocks).isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public ArticlePage openFirstArticle() {
        List<By> links = Arrays.asList(
            By.cssSelector(".story__title-link"),
            By.cssSelector(".story a[href*='pikabu.ru/story']"),
            By.cssSelector(".story a[href*='/story/']"),
            By.cssSelector("a[href*='pikabu.ru']")
        );
        WebElement link = finder.clickable(links);
        link.click();
        return new ArticlePage(driver, finder);
    }
}
