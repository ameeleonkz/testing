package com.testing.pages.mobile;

import com.testing.utils.MobileElementFinder;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePage {
    private final AndroidDriver driver;
    private final MobileElementFinder finder;

    public ArticlePage(AndroidDriver driver, MobileElementFinder finder) {
        this.driver = driver;
        this.finder = finder;
    }

    public String getTitle() {
        List<By> title = Arrays.asList(
            By.id("org.wikipedia:id/view_page_title_text"),
            By.id("org.wikipedia:id/page_header_title"),
            By.id("org.wikipedia.alpha:id/view_page_title_text"),
            By.id("org.wikipedia.alpha:id/page_header_title"),
            MobileBy.AccessibilityId("Page title")
        );
        WebElement el = finder.visible(title);
        return el.getText();
    }

    public boolean hasContent() {
        try {
            return !driver.findElements(By.id("org.wikipedia.alpha:id/page_web_view")).isEmpty()
                || !driver.findElements(By.id("org.wikipedia:id/page_web_view")).isEmpty();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean scrollDownOnce() {
        try {
            int height = driver.manage().window().getSize().height;
            int width = driver.manage().window().getSize().width;
            int startX = width / 2;
            int startY = (int) (height * 0.75);
            int endY = (int) (height * 0.25);

            new TouchAction(driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(400)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean scrollToSection(String sectionText) {
        try {
            String ui = "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\"" + sectionText + "\"))";
            driver.findElement(MobileBy.AndroidUIAutomator(ui));
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
