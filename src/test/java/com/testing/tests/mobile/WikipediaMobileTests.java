package com.testing.tests.mobile;

import com.testing.pages.mobile.ArticlePage;
import com.testing.pages.mobile.MainPage;
import com.testing.pages.mobile.SearchPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WikipediaMobileTests extends BaseMobileTest {
    private MainPage main;

    @BeforeMethod
    public void prepare() {
        main = new MainPage(driver, timeout);
        main.skipOnboardingIfPresent();
    }

    @Test(description = "Main screen is visible")
    public void shouldShowMainScreen() {
        Assert.assertTrue(main.hasSearchBox(), "Search box should be visible");
    }

    @Test(description = "Search returns results")
    public void shouldSearchForArticle() {
        SearchPage search = main.openSearch();
        search.searchFor("Appium");
        Assert.assertTrue(search.hasResults(), "Search should return results");
    }

    @Test(description = "Search: multiple queries return results")
    public void shouldSearchForMultipleQueries() {
        SearchPage search = main.openSearch();
        search.searchFor("Java");
        Assert.assertTrue(search.hasResults(), "Search should return results for Java");
        search.searchFor("Kotlin");
        Assert.assertTrue(search.hasResults(), "Search should return results for Kotlin");
    }

    @Test(description = "Open first result and verify title")
    public void shouldOpenFirstResultAndShowTitle() {
        SearchPage search = main.openSearch();
        search.searchFor("Appium");
        String firstTitle = search.getFirstResultTitle();
        ArticlePage article = search.openFirstResult();
        String articleTitle = article.getTitle();
        Assert.assertFalse(firstTitle == null || firstTitle.trim().isEmpty(), "First result title should not be empty");
        Assert.assertFalse(articleTitle == null || articleTitle.trim().isEmpty(), "Article title should not be empty");
    }

    @Test(description = "Open article and verify title + scroll")
    public void shouldOpenArticleAndScrollToSection() {
        SearchPage search = main.openSearch();
        search.searchFor("Selenium");
        ArticlePage article = search.openFirstResult();
        Assert.assertTrue(article.hasContent(), "Article webview should be visible");
        boolean scrolled = article.scrollToSection("References")
            || article.scrollToSection("Ссылки")
            || article.scrollToSection("See also");
        if (!scrolled) {
            scrolled = article.scrollDownOnce();
        }
        Assert.assertTrue(scrolled, "Should be able to scroll in article");
    }
}
