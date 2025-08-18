package sabah.com.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.config.ConfigReader;
import io.qameta.allure.Step;
import java.time.Duration;

/**
 * Tüm page sınıflarının extend edeceği temel sınıf
 * Ortak metodlar ve değişkenler burada tanımlanır
 */
public abstract class BasePage {
    
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    
    // Selenium nesneleri
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    
    // Bekleme süreleri
    protected final int DEFAULT_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_DEFAULT);
    protected final int SHORT_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_SHORT);
    protected final int LONG_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_LONG);
    
    /**
     * BasePage constructor
     * @param driver WebDriver nesnesi
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.actions = new Actions(driver);
        logger.debug("BasePage oluşturuldu: {}", this.getClass().getSimpleName());
    }
    
    /**
     * Elemente tıklama
     * @param locator Element locator'ı
     */
    @Step("'{0}' elementine tıkla")
    protected void click(By locator) {
        logger.debug("Elemente tıklanıyor: {}", locator);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    
    /**
     * Elemente tıklama - selector ile
     * @param selector CSS selector veya XPath
     */
    @Step("'{0}' selector'üne tıkla")
    protected void click(String selector) {
        logger.debug("Selector'e tıklanıyor: {}", selector);
        By locator = By.cssSelector(selector);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    
    /**
     * Elemente metin yazma
     * @param locator Element locator'ı
     * @param text Yazılacak metin
     */
    @Step("'{0}' elementine '{1}' yaz")
    protected void type(By locator, String text) {
        logger.debug("Elemente metin yazılıyor: {} -> {}", locator, text);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Elementin görünür olmasını bekle
     * @param locator Element locator'ı
     */
    @Step("'{0}' elementinin görünür olmasını bekle")
    protected void waitForVisible(By locator) {
        logger.debug("Elementin görünür olması bekleniyor: {}", locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Elementin görünür olmasını bekle - özel timeout ile
     * @param locator Element locator'ı
     * @param timeout Bekleme süresi (saniye)
     */
    protected void waitForVisible(By locator, int timeout) {
        logger.debug("Elementin görünür olması bekleniyor ({}s): {}", timeout, locator);
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Elementin kaybolmasını bekle
     * @param locator Element locator'ı
     */
    @Step("'{0}' elementinin kaybolmasını bekle")
    protected void waitForHidden(By locator) {
        logger.debug("Elementin kaybolması bekleniyor: {}", locator);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    /**
     * Element üzerine hover yapma
     * @param locator Element locator'ı
     */
    @Step("'{0}' elementi üzerine hover yap")
    protected void hover(By locator) {
        logger.debug("Element üzerine hover yapılıyor: {}", locator);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        actions.moveToElement(element).perform();
    }
    
    /**
     * Elementin görünür olup olmadığını kontrol et
     * @param locator Element locator'ı
     * @return Element görünür mü?
     */
    protected boolean isVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            logger.debug("Element görünürlük kontrolü başarısız: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Elementin etkin (enabled) olup olmadığını kontrol et
     * @param locator Element locator'ı
     * @return Element etkin mi?
     */
    protected boolean isEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (Exception e) {
            logger.debug("Element etkinlik kontrolü başarısız: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Element metnini al
     * @param locator Element locator'ı
     * @return Element metni
     */
    protected String getText(By locator) {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        logger.debug("Element metni alındı: {} -> {}", locator, text);
        return text;
    }
    
    /**
     * Element attribute değerini al
     * @param locator Element locator'ı
     * @param attributeName Attribute adı
     * @return Attribute değeri
     */
    protected String getAttribute(By locator, String attributeName) {
        String value = wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                .getAttribute(attributeName);
        logger.debug("Element attribute alındı: {} [{}] -> {}", locator, attributeName, value);
        return value;
    }
    
    /**
     * Sayfayı yenile
     */
    @Step("Sayfayı yenile")
    protected void refreshPage() {
        logger.info("Sayfa yenileniyor");
        driver.navigate().refresh();
    }
    
    /**
     * JavaScript ile scroll yapma
     * @param pixels Scroll edilecek pixel miktarı
     */
    @Step("{0} pixel scroll yap")
    protected void scrollByPixels(int pixels) {
        logger.debug("Scroll yapılıyor: {} pixel", pixels);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, " + pixels + ")");
    }
    
    /**
     * Elemente scroll yapma
     * @param locator Scroll yapılacak element
     */
    @Step("'{0}' elementine scroll yap")
    protected void scrollToElement(By locator) {
        logger.debug("Elemente scroll yapılıyor: {}", locator);
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * Elementin CSS class'ına sahip olup olmadığını kontrol et
     * @param locator Element locator'ı
     * @param className Kontrol edilecek class adı
     * @return Class var mı?
     */
    protected boolean hasClass(By locator, String className) {
        String classes = getAttribute(locator, "class");
        boolean hasClass = classes != null && classes.contains(className);
        logger.debug("Class kontrolü: {} -> {} ({})", locator, className, hasClass);
        return hasClass;
    }
    
    /**
     * Dropdown'dan değer seçme
     * @param locator Dropdown locator'ı
     * @param value Seçilecek değer
     */
    @Step("'{0}' dropdown'ından '{1}' seç")
    protected void selectByValue(By locator, String value) {
        logger.debug("Dropdown'dan değer seçiliyor: {} -> {}", locator, value);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(element);
        select.selectByValue(value);
    }
    
    /**
     * Bekle - milisaniye
     * @param milliseconds Bekleme süresi
     */
    protected void wait(int milliseconds) {
        try {
            logger.debug("Bekleniyor: {}ms", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Bekleme hatası", e);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Klavye tuşuna basma
     * @param key Basılacak tuş (örn: Keys.ESCAPE, Keys.ENTER)
     */
    @Step("'{0}' tuşuna bas")
    protected void pressKey(Keys key) {
        logger.debug("Tuşa basılıyor: {}", key);
        actions.sendKeys(key).perform();
    }
    
    /**
     * Element sayısını al
     * @param locator Element locator'ı
     * @return Element sayısı
     */
    protected int getElementCount(By locator) {
        int count = driver.findElements(locator).size();
        logger.debug("Element sayısı: {} -> {}", locator, count);
        return count;
    }
    
    /**
     * Belirtilen URL'e git
     * @param url Gidilecek URL
     */
    @Step("'{0}' adresine git")
    protected void navigateTo(String url) {
        logger.info("'{}' adresine gidiliyor", url);
        driver.navigate().to(url);
    }
    
    /**
     * Sayfa başlığını al
     * @return Sayfa başlığı
     */
    protected String getPageTitle() {
        String title = driver.getTitle();
        logger.debug("Sayfa başlığı: {}", title);
        return title;
    }
    
    /**
     * Mevcut URL'i al
     * @return Mevcut URL
     */
    protected String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.debug("Mevcut URL: {}", url);
        return url;
    }
    
    /**
     * Elementin var olup olmadığını kontrol et
     * @param locator Element locator'ı
     * @return Element var mı?
     */
    protected boolean elementExists(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (Exception e) {
            logger.debug("Element varlık kontrolü başarısız: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Elementin tıklanabilir olup olmadığını kontrol et
     * @param locator Element locator'ı
     * @return Element tıklanabilir mi?
     */
    protected boolean isClickable(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return element.isDisplayed() && element.isEnabled();
        } catch (Exception e) {
            logger.debug("Element tıklanabilirlik kontrolü başarısız: {}", e.getMessage());
            return false;
        }
    }
}
