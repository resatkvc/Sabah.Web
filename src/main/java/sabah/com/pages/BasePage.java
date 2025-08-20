package sabah.com.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import sabah.com.utils.DriverManager;
import sabah.com.utils.WaitUtils;

public class BasePage {
    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void navigateTo(String url) {
        driver.get(url);
        WaitUtils.waitForPageToLoad(driver);
        System.out.println("Navigated to: " + url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void clickElement(By locator) {
        WaitUtils.waitForElementToBeClickable(driver, locator);
        driver.findElement(locator).click();
        System.out.println("Clicked element: " + locator);
    }

    public void sendKeysToElement(By locator, String text) {
        WaitUtils.waitForElementToBeVisible(driver, locator);
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
        System.out.println("Sent keys to element: " + locator + " with text: " + text);
    }

    public String getElementText(By locator) {
        WaitUtils.waitForElementToBeVisible(driver, locator);
        String text = driver.findElement(locator).getText();
        System.out.println("Got text from element: " + locator + " - " + text);
        return text;
    }

    public boolean isElementDisplayed(By locator) {
        try {
            WaitUtils.waitForElementToBeVisible(driver, locator);
            boolean isDisplayed = driver.findElement(locator).isDisplayed();
            System.out.println("Element is visible: " + locator + " - Displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("Element not visible within timeout: " + locator);
            return false;
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            WaitUtils.waitForElementToBePresent(driver, locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        System.out.println("Scrolled to element: " + locator);
    }

    public void refreshPage() {
        driver.navigate().refresh();
        WaitUtils.waitForPageToLoad(driver);
        System.out.println("Page refreshed");
    }

    public void goBack() {
        driver.navigate().back();
        WaitUtils.waitForPageToLoad(driver);
        System.out.println("Navigated back");
    }

    public void goForward() {
        driver.navigate().forward();
        WaitUtils.waitForPageToLoad(driver);
        System.out.println("Navigated forward");
    }
}
