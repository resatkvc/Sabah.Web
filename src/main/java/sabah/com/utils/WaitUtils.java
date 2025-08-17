package sabah.com.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.config.ConfigReader;

/**
 * Bekleme işlemleri için yardımcı sınıf
 * Playwright'ın bekleme metodlarını kolaylaştırır
 */
public class WaitUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);
    
    // Varsayılan bekleme süreleri
    private static final int DEFAULT_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_DEFAULT, 10000);
    private static final int SHORT_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_SHORT, 5000);
    private static final int LONG_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_LONG, 30000);
    
    /**
     * Private constructor - Utility sınıfı
     */
    private WaitUtils() {
        // Utility sınıfı olduğu için private constructor
    }
    
    /**
     * Elementin görünür olmasını bekle
     * @param page Playwright page nesnesi
     * @param selector Element selector'ı
     */
    public static void waitForElementVisible(Page page, String selector) {
        waitForElementVisible(page, selector, DEFAULT_TIMEOUT);
    }
    
    /**
     * Elementin görünür olmasını bekle - özel timeout ile
     * @param page Playwright page nesnesi
     * @param selector Element selector'ı
     * @param timeout Bekleme süresi (ms)
     */
    public static void waitForElementVisible(Page page, String selector, int timeout) {
        try {
            logger.debug("Element görünürlüğü bekleniyor: {} ({}ms)", selector, timeout);
            page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeout));
            logger.debug("Element görünür oldu: {}", selector);
        } catch (Exception e) {
            logger.error("Element görünürlüğü beklenirken hata: {} - {}", selector, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Elementin kaybolmasını bekle
     * @param page Playwright page nesnesi
     * @param selector Element selector'ı
     */
    public static void waitForElementHidden(Page page, String selector) {
        waitForElementHidden(page, selector, DEFAULT_TIMEOUT);
    }
    
    /**
     * Elementin kaybolmasını bekle - özel timeout ile
     * @param page Playwright page nesnesi
     * @param selector Element selector'ı
     * @param timeout Bekleme süresi (ms)
     */
    public static void waitForElementHidden(Page page, String selector, int timeout) {
        try {
            logger.debug("Element kaybolması bekleniyor: {} ({}ms)", selector, timeout);
            page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(timeout));
            logger.debug("Element kayboldu: {}", selector);
        } catch (Exception e) {
            logger.error("Element kaybolması beklenirken hata: {} - {}", selector, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Elementin tıklanabilir olmasını bekle
     * @param page Playwright page nesnesi
     * @param selector Element selector'ı
     */
    public static void waitForElementClickable(Page page, String selector) {
        waitForElementClickable(page, selector, DEFAULT_TIMEOUT);
    }
    
    /**
     * Elementin tıklanabilir olmasını bekle - özel timeout ile
     * @param page Playwright page nesnesi
     * @param selector Element selector'ı
     * @param timeout Bekleme süresi (ms)
     */
    public static void waitForElementClickable(Page page, String selector, int timeout) {
        try {
            logger.debug("Element tıklanabilirlik bekleniyor: {} ({}ms)", selector, timeout);
            page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeout));
            logger.debug("Element tıklanabilir oldu: {}", selector);
        } catch (Exception e) {
            logger.error("Element tıklanabilirlik beklenirken hata: {} - {}", selector, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Network isteğinin tamamlanmasını bekle
     * @param page Playwright page nesnesi
     * @param urlPattern URL pattern'i
     */
    public static void waitForNetworkIdle(Page page, String urlPattern) {
        waitForNetworkIdle(page, urlPattern, DEFAULT_TIMEOUT);
    }
    
    /**
     * Network isteğinin tamamlanmasını bekle - özel timeout ile
     * @param page Playwright page nesnesi
     * @param urlPattern URL pattern'i
     * @param timeout Bekleme süresi (ms)
     */
    public static void waitForNetworkIdle(Page page, String urlPattern, int timeout) {
        try {
            logger.debug("Network idle bekleniyor: {} ({}ms)", urlPattern, timeout);
            // Basit bir bekleme - network idle için
            page.waitForTimeout(timeout);
            logger.debug("Network idle tamamlandı: {}", urlPattern);
        } catch (Exception e) {
            logger.error("Network idle beklenirken hata: {} - {}", urlPattern, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Sayfa yüklenmesini bekle
     * @param page Playwright page nesnesi
     */
    public static void waitForPageLoad(Page page) {
        try {
            logger.debug("Sayfa yüklenmesi bekleniyor...");
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
            logger.debug("Sayfa yüklendi");
        } catch (Exception e) {
            logger.error("Sayfa yüklenmesi beklenirken hata: {}", e.getMessage());
            throw e;
        }
    }
    
    /**
     * Sayfa yüklenmesini bekle - özel state ile
     * @param page Playwright page nesnesi
     * @param loadState Yükleme durumu
     */
    public static void waitForPageLoad(Page page, LoadState loadState) {
        try {
            logger.debug("Sayfa yüklenmesi bekleniyor: {}", loadState);
            page.waitForLoadState(loadState);
            logger.debug("Sayfa yüklendi: {}", loadState);
        } catch (Exception e) {
            logger.error("Sayfa yüklenmesi beklenirken hata: {} - {}", loadState, e.getMessage());
            throw e;
        }
    }
    
    /**
     * Belirli bir süre bekle
     * @param milliseconds Bekleme süresi (ms)
     */
    public static void sleep(int milliseconds) {
        try {
            logger.debug("Bekleniyor: {}ms", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.error("Bekleme hatası", e);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Elementin var olmasını bekle
     * @param page Playwright page nesnesi
     * @param selector Element selector'ı
     */
    public static void waitForElementExists(Page page, String selector) {
        waitForElementExists(page, selector, DEFAULT_TIMEOUT);
    }
    
    /**
     * Elementin var olmasını bekle - özel timeout ile
     * @param page Playwright page nesnesi
     * @param selector Element selector'ı
     * @param timeout Bekleme süresi (ms)
     */
    public static void waitForElementExists(Page page, String selector, int timeout) {
        try {
            logger.debug("Element varlığı bekleniyor: {} ({}ms)", selector, timeout);
            page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setTimeout(timeout));
            logger.debug("Element bulundu: {}", selector);
        } catch (Exception e) {
            logger.error("Element varlığı beklenirken hata: {} - {}", selector, e.getMessage());
            throw e;
        }
    }
    
    /**
     * URL değişimini bekle
     * @param page Playwright page nesnesi
     * @param expectedUrl Beklenen URL pattern'i
     */
    public static void waitForUrlChange(Page page, String expectedUrl) {
        waitForUrlChange(page, expectedUrl, DEFAULT_TIMEOUT);
    }
    
    /**
     * URL değişimini bekle - özel timeout ile
     * @param page Playwright page nesnesi
     * @param expectedUrl Beklenen URL pattern'i
     * @param timeout Bekleme süresi (ms)
     */
    public static void waitForUrlChange(Page page, String expectedUrl, int timeout) {
        try {
            logger.debug("URL değişimi bekleniyor: {} ({}ms)", expectedUrl, timeout);
            page.waitForURL(url -> url.contains(expectedUrl), 
                new Page.WaitForURLOptions().setTimeout(timeout));
            logger.debug("URL değişti: {}", expectedUrl);
        } catch (Exception e) {
            logger.error("URL değişimi beklenirken hata: {} - {}", expectedUrl, e.getMessage());
            throw e;
        }
    }
    
    /**
     * JavaScript koşulunun sağlanmasını bekle
     * @param page Playwright page nesnesi
     * @param script JavaScript kodu
     */
    public static void waitForJavaScriptCondition(Page page, String script) {
        waitForJavaScriptCondition(page, script, DEFAULT_TIMEOUT);
    }
    
    /**
     * JavaScript koşulunun sağlanmasını bekle - özel timeout ile
     * @param page Playwright page nesnesi
     * @param script JavaScript kodu
     * @param timeout Bekleme süresi (ms)
     */
    public static void waitForJavaScriptCondition(Page page, String script, int timeout) {
        try {
            logger.debug("JavaScript koşulu bekleniyor: {} ({}ms)", script, timeout);
            page.waitForFunction(script, new Page.WaitForFunctionOptions().setTimeout(timeout));
            logger.debug("JavaScript koşulu sağlandı: {}", script);
        } catch (Exception e) {
            logger.error("JavaScript koşulu beklenirken hata: {} - {}", script, e.getMessage());
            throw e;
        }
    }
}
