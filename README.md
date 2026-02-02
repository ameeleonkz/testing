# testing_final

Automated UI tests for:
- https://pikabu.ru/ (Selenium + TestNG)
- Wikipedia Android app (Appium + TestNG)

Stack
- Java 11+
- Maven
- Selenium WebDriver
- TestNG
- Appium Java Client
- WebDriverManager

## Structure
- src/test/java/com/testing/pages/web — Page Objects for the website
- src/test/java/com/testing/tests/web — web tests
- src/test/java/com/testing/pages/mobile — Page Objects for Android
- src/test/java/com/testing/tests/mobile — mobile tests
- src/test/resources/config.properties — configuration
- testng.xml, testng-web.xml, testng-mobile.xml — test suites

## Configuration
File src/test/resources/config.properties:
- web.baseUrl — base URL for the site
- web.browser — chrome | firefox | edge
- web.headless — true/false
- appium.serverUrl — Appium Server URL
- appium.app — path to APK (optional)
- appium.appPackage, appium.appActivity — installed app parameters

## Run web tests
1) Install JDK 11+ and Maven.
2) Run:

mvn -Pweb test

## Run mobile tests
1) Install Android SDK and start an emulator (or connect a device).
2) Install and start Appium Server.
3) Install the Wikipedia app on the emulator/device
   (or set the APK path in appium.app).
4) Run:

mvn -Pmobile test

## Run all tests

mvn test

## Stability notes
- Multiple locator strategies are used to reduce flakiness.
- If UI changes, update locators in the Page Objects.

## Scenarios
Web (Pikabu):
- Open search panel
- Search and verify results
- Verify main feed is visible
- Open a story and check the title

Mobile (Wikipedia):
- Verify main screen (search box)
- Search and verify results
- Search multiple queries
- Open article and check title
- Scroll inside article
