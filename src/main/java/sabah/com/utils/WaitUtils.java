package sabah.com.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sabah.com.config.ConfigReader;

import java.time.Duration;

public class WaitUtils {

    public static void waitForElementToBeVisible(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("Element is visible: " + locator);
        } catch (TimeoutException e) {
            System.err.println("Element not visible within timeout: " + locator);
            throw e;
        }
    }

    public static void waitForElementToBeClickable(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            System.out.println("Element is clickable: " + locator);
        } catch (TimeoutException e) {
            System.err.println("Element not clickable within timeout: " + locator);
            throw e;
        }
    }

    public static void waitForElementToBePresent(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            System.out.println("Element is present: " + locator);
        } catch (TimeoutException e) {
            System.err.println("Element not present within timeout: " + locator);
            throw e;
        }
    }

    public static void waitForPageToLoad(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
            System.out.println("Page loaded successfully");
        } catch (TimeoutException e) {
            System.err.println("Page did not load within timeout");
            throw e;
        }
    }

    public static void waitForUrlToContain(WebDriver driver, String partialUrl) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
            wait.until(ExpectedConditions.urlContains(partialUrl));
            System.out.println("URL contains: " + partialUrl);
        } catch (TimeoutException e) {
            System.err.println("URL does not contain expected text within timeout: " + partialUrl);
            throw e;
        }
    }

    public static void waitForTitleToContain(WebDriver driver, String partialTitle) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getExplicitWait()));
            wait.until(ExpectedConditions.titleContains(partialTitle));
            System.out.println("Title contains: " + partialTitle);
        } catch (TimeoutException e) {
            System.err.println("Title does not contain expected text within timeout: " + partialTitle);
            throw e;
        }
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            System.out.println("Slept for " + seconds + " seconds");
        } catch (InterruptedException e) {
            System.err.println("Sleep interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
