package sabah.com.base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.config.ConfigReader;
import io.qameta.allure.Step;

/**
 * Tüm page sınıflarının extend edeceği temel sınıf
 * Ortak metodlar ve değişkenler burada tanımlanır
 */
public abstract class BasePage {
    
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    
    // Playwright nesneleri
    protected Page page;
    protected BrowserContext context;
    
    // Bekleme süreleri
    protected final int DEFAULT_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_DEFAULT);
    protected final int SHORT_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_SHORT);
    protected final int LONG_TIMEOUT = ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_LONG);
    
    /**
     * BasePage constructor
     * @param page Playwright page nesnesi
     */
    public BasePage(Page page) {
        this.page = page;
        this.context = page.context();
        logger.debug("BasePage oluşturuldu: {}", this.getClass().getSimpleName());
    }
    
    /**
     * Elemente tıklama
     * @param locator Element locator'ı
     */
    @Step("'{0}' elementine tıkla")
    protected void click(Locator locator) {
        logger.debug("Elemente tıklanıyor: {}", locator);
        locator.click();
    }
    
    /**
     * Elemente tıklama - selector ile
     * @param selector CSS selector veya XPath
     */
    @Step("'{0}' selector'üne tıkla")
    protected void click(String selector) {
        logger.debug("Selector'e tıklanıyor: {}", selector);
        page.click(selector);
    }
    
    /**
     * Elemente metin yazma
     * @param locator Element locator'ı
     * @param text Yazılacak metin
     */
    @Step("'{0}' elementine '{1}' yaz")
    protected void type(Locator locator, String text) {
        logger.debug("Elemente metin yazılıyor: {} -> {}", locator, text);
        locator.fill(text);
    }
    
    /**
     * Elementin görünür olmasını bekle
     * @param locator Element locator'ı
     */
    @Step("'{0}' elementinin görünür olmasını bekle")
    protected void waitForVisible(Locator locator) {
        logger.debug("Elementin görünür olması bekleniyor: {}", locator);
        try {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        } catch (Exception e) {
            logger.warn("VISIBLE state hatası, ATTACHED kullanılıyor: {}", e.getMessage());
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        }
    }
    
    /**
     * Elementin görünür olmasını bekle - özel timeout ile
     * @param locator Element locator'ı
     * @param timeout Bekleme süresi (ms)
     */
    protected void waitForVisible(Locator locator, int timeout) {
        logger.debug("Elementin görünür olması bekleniyor ({}ms): {}", timeout, locator);
        try {
            locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeout));
        } catch (Exception e) {
            logger.warn("VISIBLE state hatası, ATTACHED kullanılıyor: {}", e.getMessage());
            locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(timeout));
        }
    }
    
    /**
     * Elementin kaybolmasını bekle
     * @param locator Element locator'ı
     */
    @Step("'{0}' elementinin kaybolmasını bekle")
    protected void waitForHidden(Locator locator) {
        logger.debug("Elementin kaybolması bekleniyor: {}", locator);
        try {
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        } catch (Exception e) {
            logger.warn("HIDDEN state hatası, DETACHED kullanılıyor: {}", e.getMessage());
            locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
        }
    }
    
    /**
     * Element üzerine hover yapma
     * @param locator Element locator'ı
     */
    @Step("'{0}' elementi üzerine hover yap")
    protected void hover(Locator locator) {
        logger.debug("Element üzerine hover yapılıyor: {}", locator);
        locator.hover();
    }
    
    /**
     * Elementin görünür olup olmadığını kontrol et
     * @param locator Element locator'ı
     * @return Element görünür mü?
     */
    protected boolean isVisible(Locator locator) {
        try {
            return locator.isVisible();
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
    protected boolean isEnabled(Locator locator) {
        try {
            return locator.isEnabled();
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
    protected String getText(Locator locator) {
        String text = locator.textContent();
        logger.debug("Element metni alındı: {} -> {}", locator, text);
        return text;
    }
    
    /**
     * Element attribute değerini al
     * @param locator Element locator'ı
     * @param attributeName Attribute adı
     * @return Attribute değeri
     */
    protected String getAttribute(Locator locator, String attributeName) {
        String value = locator.getAttribute(attributeName);
        logger.debug("Element attribute alındı: {} [{}] -> {}", locator, attributeName, value);
        return value;
    }
    
    /**
     * Sayfayı yenile
     */
    @Step("Sayfayı yenile")
    protected void refreshPage() {
        logger.info("Sayfa yenileniyor");
        page.reload();
    }
    
    /**
     * JavaScript ile scroll yapma
     * @param pixels Scroll edilecek pixel miktarı
     */
    @Step("{0} pixel scroll yap")
    protected void scrollByPixels(int pixels) {
        logger.debug("Scroll yapılıyor: {} pixel", pixels);
        page.evaluate("window.scrollBy(0, " + pixels + ")");
    }
    
    /**
     * Elemente scroll yapma
     * @param locator Scroll yapılacak element
     */
    @Step("'{0}' elementine scroll yap")
    protected void scrollToElement(Locator locator) {
        logger.debug("Elemente scroll yapılıyor: {}", locator);
        locator.scrollIntoViewIfNeeded();
    }
    
    /**
     * Elementin CSS class'ına sahip olup olmadığını kontrol et
     * @param locator Element locator'ı
     * @param className Kontrol edilecek class adı
     * @return Class var mı?
     */
    protected boolean hasClass(Locator locator, String className) {
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
    protected void selectByValue(Locator locator, String value) {
        logger.debug("Dropdown'dan değer seçiliyor: {} -> {}", locator, value);
        locator.selectOption(value);
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
     * @param key Basılacak tuş (örn: "Escape", "Enter")
     */
    @Step("'{0}' tuşuna bas")
    protected void pressKey(String key) {
        logger.debug("Tuşa basılıyor: {}", key);
        page.keyboard().press(key);
    }
    
    /**
     * Element sayısını al
     * @param locator Element locator'ı
     * @return Element sayısı
     */
    protected int getElementCount(Locator locator) {
        int count = locator.count();
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
        page.navigate(url);
    }
    
    /**
     * Sayfa başlığını al
     * @return Sayfa başlığı
     */
    protected String getPageTitle() {
        String title = page.title();
        logger.debug("Sayfa başlığı: {}", title);
        return title;
    }
    
    /**
     * Mevcut URL'i al
     * @return Mevcut URL
     */
    protected String getCurrentUrl() {
        String url = page.url();
        logger.debug("Mevcut URL: {}", url);
        return url;
    }
    
    /**
     * Elementin var olup olmadığını kontrol et
     * @param locator Element locator'ı
     * @return Element var mı?
     */
    protected boolean elementExists(Locator locator) {
        try {
            return locator.count() > 0;
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
    protected boolean isClickable(Locator locator) {
        try {
            return locator.isVisible() && locator.isEnabled();
        } catch (Exception e) {
            logger.debug("Element tıklanabilirlik kontrolü başarısız: {}", e.getMessage());
            return false;
        }
    }
}
