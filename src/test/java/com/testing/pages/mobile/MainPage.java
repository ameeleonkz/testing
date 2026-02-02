package com.testing.pages.mobile;

import com.testing.utils.MobileElementFinder;
import io.appium.java_client.MobileBy;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.appium.java_client.android.AndroidDriver;

public class MainPage {
    private final AndroidDriver driver;
    private final MobileElementFinder finder;

    public MainPage(AndroidDriver driver, Duration timeout) {
        this.driver = driver;
        this.finder = new MobileElementFinder(driver, timeout);
    }

    public MainPage skipOnboardingIfPresent() {
        List<By> skipButtons = Arrays.asList(
            By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
            MobileBy.AccessibilityId("Skip"),
            By.xpath("//*[@text='Skip' or @text='Пропустить']")
        );
        for (By locator : skipButtons) {
            try {
                WebElement el = driver.findElement(locator);
                if (el.isDisplayed()) {
                    el.click();
                    break;
                }
            } catch (RuntimeException ignored) {
            }
        }
        return this;
    }

    public boolean hasSearchBox() {
        List<By> search = Arrays.asList(
            By.id("org.wikipedia:id/search_container"),
            By.id("org.wikipedia.alpha:id/search_container"),
            MobileBy.AccessibilityId("Search Wikipedia"),
            By.xpath("//*[@text='Search Wikipedia' or @text='Поиск в Wikipedia']")
        );
        try {
            return finder.visible(search).isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public SearchPage openSearch() {
        List<By> search = Arrays.asList(
            By.id("org.wikipedia:id/search_container"),
            By.id("org.wikipedia.alpha:id/search_container"),
            MobileBy.AccessibilityId("Search Wikipedia"),
            By.xpath("//*[@text='Search Wikipedia' or @text='Поиск в Wikipedia']")
        );
        finder.clickable(search).click();
        return new SearchPage(driver, finder);
    }
}
