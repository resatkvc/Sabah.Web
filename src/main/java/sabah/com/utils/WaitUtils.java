package sabah.com.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.config.ConfigReader;
import java.time.Duration;

/**
 * Bekleme işlemleri için yardımcı sınıf
 * Selenium WebDriver'ın bekleme metodlarını kolaylaştırır
 */
public class WaitUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);
    
    // Varsayılan bekleme süreleri
    private static final int DEFAULT_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_DEFAULT, 10);
    private static final int SHORT_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_SHORT, 5);
    private static final int LONG_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_LONG, 30);
    
    /**
     * Private constructor - Utility sınıfı
     */
    private WaitUtils() {
        // Utility sınıfı olduğu için private constructor
    }
    
    /**
     * Elementin görünür olmasını bekle
     * @param driver WebDriver nesnesi
     * @param locator Element locator'ı
     */
    public static void waitForElementVisible(WebDriver driver, By locator) {
        waitForElementVisible(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Elementin görünür olmasını bekle - özel timeout ile
     * @param driver WebDriver nesnesi
     * @param locator Element locator'ı
     * @param timeout Bekleme süresi (saniye)
     */
    public static void waitForElementVisible(WebDriver driver, By locator, int timeout) {
        try {
            logger.debug("Element görünürlüğü bekleniyor: {} ({}s)", locator, timeout);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.debug("Element görünür oldu: {}", locator);
        } catch (Exception e) {
            logger.error("Element görünürlüğü beklenirken hata: {} - {}", locator, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Elementin kaybolmasını bekle
     * @param driver WebDriver nesnesi
     * @param locator Element locator'ı
     */
    public static void waitForElementHidden(WebDriver driver, By locator) {
        waitForElementHidden(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Elementin kaybolmasını bekle - özel timeout ile
     * @param driver WebDriver nesnesi
     * @param locator Element locator'ı
     * @param timeout Bekleme süresi (saniye)
     */
    public static void waitForElementHidden(WebDriver driver, By locator, int timeout) {
        try {
            logger.debug("Element kaybolması bekleniyor: {} ({}s)", locator, timeout);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            logger.debug("Element kayboldu: {}", locator);
        } catch (Exception e) {
            logger.error("Element kaybolması beklenirken hata: {} - {}", locator, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Elementin tıklanabilir olmasını bekle
     * @param driver WebDriver nesnesi
     * @param locator Element locator'ı
     */
    public static void waitForElementClickable(WebDriver driver, By locator) {
        waitForElementClickable(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Elementin tıklanabilir olmasını bekle - özel timeout ile
     * @param driver WebDriver nesnesi
     * @param locator Element locator'ı
     * @param timeout Bekleme süresi (saniye)
     */
    public static void waitForElementClickable(WebDriver driver, By locator, int timeout) {
        try {
            logger.debug("Element tıklanabilirliği bekleniyor: {} ({}s)", locator, timeout);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            logger.debug("Element tıklanabilir oldu: {}", locator);
        } catch (Exception e) {
            logger.error("Element tıklanabilirliği beklenirken hata: {} - {}", locator, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Sayfanın yüklenmesini bekle
     * @param driver WebDriver nesnesi
     */
    public static void waitForPageLoad(WebDriver driver) {
        waitForPageLoad(driver, DEFAULT_TIMEOUT);
    }
    
    /**
     * Sayfanın yüklenmesini bekle - özel timeout ile
     * @param driver WebDriver nesnesi
     * @param timeout Bekleme süresi (saniye)
     */
    public static void waitForPageLoad(WebDriver driver, int timeout) {
        try {
            logger.debug("Sayfa yükleme bekleniyor ({}s)", timeout);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
            logger.debug("Sayfa yüklendi");
        } catch (Exception e) {
            logger.error("Sayfa yükleme beklenirken hata: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * JavaScript'in tamamlanmasını bekle
     * @param driver WebDriver nesnesi
     */
    public static void waitForJavaScriptLoad(WebDriver driver) {
        waitForJavaScriptLoad(driver, DEFAULT_TIMEOUT);
    }
    
    /**
     * JavaScript'in tamamlanmasını bekle - özel timeout ile
     * @param driver WebDriver nesnesi
     * @param timeout Bekleme süresi (saniye)
     */
    public static void waitForJavaScriptLoad(WebDriver driver, int timeout) {
        try {
            logger.debug("JavaScript yükleme bekleniyor ({}s)", timeout);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return jQuery.active == 0"));
            logger.debug("JavaScript yüklendi");
        } catch (Exception e) {
            logger.debug("jQuery kontrolü başarısız, alternatif kontrol kullanılıyor: {}", e.getMessage());
            // jQuery yoksa alternatif kontrol
            try {
                Thread.sleep(1000);
                logger.debug("Alternatif JavaScript bekleme tamamlandı");
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                logger.error("JavaScript bekleme kesildi");
            }
        }
    }
    
    /**
     * Belirli bir süre bekle
     * @param seconds Saniye cinsinden bekleme süresi
     */
    public static void waitForSeconds(int seconds) {
        try {
            logger.debug("{} saniye bekleniyor...", seconds);
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            logger.error("Bekleme hatası", e);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Belirli bir süre bekle - milisaniye
     * @param milliseconds Milisaniye cinsinden bekleme süresi
     */
    public static void waitForMilliseconds(int milliseconds) {
        try {
            logger.debug("{} milisaniye bekleniyor...", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Bekleme hatası", e);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Elementin var olmasını bekle
     * @param driver WebDriver nesnesi
     * @param locator Element locator'ı
     */
    public static void waitForElementPresent(WebDriver driver, By locator) {
        waitForElementPresent(driver, locator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Elementin var olmasını bekle - özel timeout ile
     * @param driver WebDriver nesnesi
     * @param locator Element locator'ı
     * @param timeout Bekleme süresi (saniye)
     */
    public static void waitForElementPresent(WebDriver driver, By locator, int timeout) {
        try {
            logger.debug("Element varlığı bekleniyor: {} ({}s)", locator, timeout);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.debug("Element bulundu: {}", locator);
        } catch (Exception e) {
            logger.error("Element varlığı beklenirken hata: {} - {}", locator, e.getMessage());
            throw e;
        }
    }
    
    /**
     * URL'nin değişmesini bekle
     * @param driver WebDriver nesnesi
     * @param expectedUrl Beklenen URL
     */
    public static void waitForUrlChange(WebDriver driver, String expectedUrl) {
        waitForUrlChange(driver, expectedUrl, DEFAULT_TIMEOUT);
    }
    
    /**
     * URL'nin değişmesini bekle - özel timeout ile
     * @param driver WebDriver nesnesi
     * @param expectedUrl Beklenen URL
     * @param timeout Bekleme süresi (saniye)
     */
    public static void waitForUrlChange(WebDriver driver, String expectedUrl, int timeout) {
        try {
            logger.debug("URL değişimi bekleniyor: {} ({}s)", expectedUrl, timeout);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.urlContains(expectedUrl));
            logger.debug("URL değişti: {}", expectedUrl);
        } catch (Exception e) {
            logger.error("URL değişimi beklenirken hata: {} - {}", expectedUrl, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Sayfa başlığının değişmesini bekle
     * @param driver WebDriver nesnesi
     * @param expectedTitle Beklenen başlık
     */
    public static void waitForTitleChange(WebDriver driver, String expectedTitle) {
        waitForTitleChange(driver, expectedTitle, DEFAULT_TIMEOUT);
    }
    
    /**
     * Sayfa başlığının değişmesini bekle - özel timeout ile
     * @param driver WebDriver nesnesi
     * @param expectedTitle Beklenen başlık
     * @param timeout Bekleme süresi (saniye)
     */
    public static void waitForTitleChange(WebDriver driver, String expectedTitle, int timeout) {
        try {
            logger.debug("Başlık değişimi bekleniyor: {} ({}s)", expectedTitle, timeout);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.titleContains(expectedTitle));
            logger.debug("Başlık değişti: {}", expectedTitle);
        } catch (Exception e) {
            logger.error("Başlık değişimi beklenirken hata: {} - {}", expectedTitle, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Alert'in görünmesini bekle
     * @param driver WebDriver nesnesi
     */
    public static void waitForAlert(WebDriver driver) {
        waitForAlert(driver, DEFAULT_TIMEOUT);
    }
    
    /**
     * Alert'in görünmesini bekle - özel timeout ile
     * @param driver WebDriver nesnesi
     * @param timeout Bekleme süresi (saniye)
     */
    public static void waitForAlert(WebDriver driver, int timeout) {
        try {
            logger.debug("Alert bekleniyor ({}s)", timeout);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.alertIsPresent());
            logger.debug("Alert göründü");
        } catch (Exception e) {
            logger.error("Alert beklenirken hata: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Frame'in yüklenmesini bekle
     * @param driver WebDriver nesnesi
     * @param frameLocator Frame locator'ı
     */
    public static void waitForFrame(WebDriver driver, By frameLocator) {
        waitForFrame(driver, frameLocator, DEFAULT_TIMEOUT);
    }
    
    /**
     * Frame'in yüklenmesini bekle - özel timeout ile
     * @param driver WebDriver nesnesi
     * @param frameLocator Frame locator'ı
     * @param timeout Bekleme süresi (saniye)
     */
    public static void waitForFrame(WebDriver driver, By frameLocator, int timeout) {
        try {
            logger.debug("Frame bekleniyor: {} ({}s)", frameLocator, timeout);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
            logger.debug("Frame yüklendi: {}", frameLocator);
        } catch (Exception e) {
            logger.error("Frame beklenirken hata: {} - {}", frameLocator, e.getMessage());
            throw e;
        }
    }
}
