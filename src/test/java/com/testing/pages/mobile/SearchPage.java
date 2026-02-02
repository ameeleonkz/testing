package com.testing.pages.mobile;

import com.testing.utils.MobileElementFinder;
import io.appium.java_client.MobileBy;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.appium.java_client.android.AndroidDriver;

public class SearchPage {
    private final AndroidDriver driver;
    private final MobileElementFinder finder;
    private String lastQuery = "";

    public SearchPage(AndroidDriver driver, MobileElementFinder finder) {
        this.driver = driver;
        this.finder = finder;
    }

    public SearchPage searchFor(String text) {
        List<By> inputs = Arrays.asList(
            By.id("org.wikipedia:id/search_src_text"),
            By.id("org.wikipedia.alpha:id/search_src_text"),
            By.id("org.wikipedia.alpha:id/search_query"),
            MobileBy.AccessibilityId("Search"),
            By.xpath("//android.widget.EditText")
        );
        lastQuery = text == null ? "" : text.trim().toLowerCase();
        WebElement input = finder.visible(inputs);
        input.clear();
        input.sendKeys(text + "\n");
        return this;
    }

    public String getFirstResultTitle() {
        String queryXpath = lastQuery.isEmpty()
            ? "//android.widget.TextView[@text]"
            : "//android.widget.TextView[@text and contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + lastQuery + "')]";

        List<By> titles = Arrays.asList(
            By.id("org.wikipedia:id/page_list_item_title"),
            By.id("org.wikipedia.alpha:id/page_list_item_title"),
            By.xpath(queryXpath),
            By.xpath("(//androidx.recyclerview.widget.RecyclerView//android.widget.TextView)[1]")
        );
        return finder.visible(titles).getText();
    }

    public ArticlePage openFirstResult() {
        if (!driver.findElements(By.id("org.wikipedia.alpha:id/page_web_view")).isEmpty()
            || !driver.findElements(By.id("org.wikipedia:id/page_web_view")).isEmpty()) {
            return new ArticlePage(driver, finder);
        }

        String queryXpath = lastQuery.isEmpty()
            ? "//android.widget.TextView[@text]"
            : "//android.widget.TextView[@text and contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + lastQuery + "')]";

        List<By> results = Arrays.asList(
            By.id("org.wikipedia:id/page_list_item_title"),
            By.id("org.wikipedia.alpha:id/page_list_item_title"),
            By.id("org.wikipedia.alpha:id/page_list_item_container"),
            By.id("org.wikipedia.alpha:id/search_results_list"),
            By.xpath("//*[@resource-id='org.wikipedia.alpha:id/page_list_item_title' or @resource-id='org.wikipedia:id/page_list_item_title']"),
            By.xpath("//androidx.recyclerview.widget.RecyclerView//android.widget.TextView"),
            // Compose search results in alpha build (no resource-id on titles)
            By.xpath(queryXpath),
            // Clickable container in Compose list
            By.xpath("(//android.view.View[@clickable='true' and .//android.widget.TextView])[1]")
        );
        WebElement target = finder.clickable(results);
        target.click();
        if (!driver.findElements(By.id("org.wikipedia.alpha:id/page_web_view")).isEmpty()
            || !driver.findElements(By.id("org.wikipedia:id/page_web_view")).isEmpty()) {
            return new ArticlePage(driver, finder);
        }
        // Fallback: click the parent view if the text itself isn't clickable
        try {
            WebElement parent = target.findElement(By.xpath(".."));
            if (parent.isEnabled()) {
                parent.click();
            }
        } catch (RuntimeException ignored) {
        }
        return new ArticlePage(driver, finder);
    }

    public boolean hasResults() {
        String queryXpath = lastQuery.isEmpty()
            ? "//android.widget.TextView[@text]"
            : "//android.widget.TextView[@text and contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + lastQuery + "')]";

        List<By> results = Arrays.asList(
            By.id("org.wikipedia:id/page_list_item_container"),
            By.id("org.wikipedia:id/page_list_item_title"),
            By.id("org.wikipedia.alpha:id/page_list_item_container"),
            By.id("org.wikipedia.alpha:id/page_list_item_title"),
            By.id("org.wikipedia.alpha:id/search_results_list"),
            By.xpath(queryXpath),
            By.xpath("(//android.view.View[@clickable='true' and .//android.widget.TextView])[1]")
        );
        try {
            return finder.visible(results).isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }
}
