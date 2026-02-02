package com.testing.tests.web;

import com.testing.pages.web.ArticlePage;
import com.testing.pages.web.HomePage;
import com.testing.pages.web.SearchPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PikabuWebTests extends BaseWebTest {
    private HomePage home;

    @BeforeMethod
    public void openHome() {
        home = new HomePage(driver, timeout);
        home.open().acceptCookiesIfPresent();
    }

    @Test(description = "Search UI: search panel opens")
    public void shouldOpenSearchPanel() {
        SearchPage search = home.openSearch();
        Assert.assertTrue(search.hasSearchInput(), "Search input should be visible");
    }

    @Test(description = "Search: query returns results")
    public void shouldReturnSearchResults() {
        SearchPage search = home.openSearch();
        search.searchFor("???????");
        Assert.assertTrue(search.hasResults(), "Search should return results");
        Assert.assertFalse(search.hasNoResultsMessage(), "No-results message should not be shown");
    }

    @Test(description = "Main page: stories feed is visible")
    public void shouldShowStoriesOnHomePage() {
        Assert.assertTrue(home.hasTopStories(), "Stories feed should be visible");
    }

    @Test(description = "Story: opening from main page shows title")
    public void shouldOpenStoryAndShowTitle() {
        ArticlePage article = home.openFirstArticle();
        Assert.assertTrue(article.hasTitle(), "Story page should show title");
    }
}
