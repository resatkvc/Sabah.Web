package sabah.com.utils;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.ViewportSize;
import com.microsoft.playwright.options.WaitUntilState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.config.ConfigReader;
import io.qameta.allure.Step;

/**
 * Playwright browser yönetimi için utility sınıfı
 * Browser açma, kapatma ve konfigürasyon işlemlerini yönetir
 */
public class BrowserManager {
    
    private static final Logger logger = LoggerFactory.getLogger(BrowserManager.class);
    
    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;
    
    /**
     * Private constructor - Singleton pattern
     */
    private BrowserManager() {
        // Utility sınıfı olduğu için private constructor
    }
    
    /**
     * Browser'ı başlat ve yapılandır
     * @return Yapılandırılmış Page nesnesi
     */
    @Step("Browser başlatılıyor")
    public static Page initBrowser() {
        try {
            // Playwright'ı başlat
            playwright = Playwright.create();
            logger.info("Playwright başlatıldı");
            
            // Browser tipini config'den al
            String browserType = ConfigReader.getProperty(ConfigReader.BROWSER_TYPE, "chromium");
            boolean headless = ConfigReader.getBooleanProperty(ConfigReader.BROWSER_HEADLESS, false);
            int slowMo = ConfigReader.getIntProperty(ConfigReader.BROWSER_SLOW_MOTION, 0);
            
            // Browser launch options
            BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(slowMo);
            
            // Browser'ı başlat
            switch (browserType.toLowerCase()) {
                case "chromium":
                    browser = playwright.chromium().launch(launchOptions);
                    logger.info("Chromium browser başlatıldı");
                    break;
                case "firefox":
                    browser = playwright.firefox().launch(launchOptions);
                    logger.info("Firefox browser başlatıldı");
                    break;
                case "webkit":
                    browser = playwright.webkit().launch(launchOptions);
                    logger.info("WebKit browser başlatıldı");
                    break;
                default:
                    browser = playwright.chromium().launch(launchOptions);
                    logger.warn("Bilinmeyen browser tipi: {}. Chromium kullanılıyor.", browserType);
            }
            
            // Browser context oluştur
            Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(new ViewportSize(
                    ConfigReader.getIntProperty(ConfigReader.VIEWPORT_WIDTH, 1920),
                    ConfigReader.getIntProperty(ConfigReader.VIEWPORT_HEIGHT, 1080)
                ))
                .setLocale("tr-TR")
                .setTimezoneId("Europe/Istanbul");
            
            // Video kayıt özelliği (opsiyonel)
            if (ConfigReader.getBooleanProperty(ConfigReader.VIDEO_RECORD, false)) {
                contextOptions.setRecordVideoDir(java.nio.file.Paths.get(ConfigReader.getProperty(ConfigReader.VIDEO_RECORD_DIR, "target/videos")));
                contextOptions.setRecordVideoSize(new com.microsoft.playwright.options.RecordVideoSize(1280, 720));
            }
            
            context = browser.newContext(contextOptions);
            logger.info("Browser context oluşturuldu");
            
            // Tracing başlat (debug için)
            if (ConfigReader.getBooleanProperty(ConfigReader.TRACING_ENABLED, false)) {
                context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(false));
                logger.info("Tracing başlatıldı");
            }
            
            // Yeni sayfa aç
            page = context.newPage();
            
            // Sayfa timeout ayarları
            page.setDefaultTimeout(ConfigReader.getIntProperty(ConfigReader.BROWSER_TIMEOUT, 30000));
            page.setDefaultNavigationTimeout(ConfigReader.getIntProperty(ConfigReader.PAGE_LOAD_TIMEOUT, 60000));
            
            logger.info("Yeni sayfa açıldı ve yapılandırıldı");
            
            return page;
            
        } catch (Exception e) {
            logger.error("Browser başlatma hatası", e);
            throw new RuntimeException("Browser başlatılamadı: " + e.getMessage(), e);
        }
    }
    
    /**
     * Mevcut Page nesnesini getir
     * @return Aktif Page nesnesi
     */
    public static Page getPage() {
        if (page == null) {
            logger.warn("Page nesnesi null, yeni browser başlatılıyor");
            return initBrowser();
        }
        return page;
    }
    
    /**
     * Mevcut BrowserContext nesnesini getir
     * @return Aktif BrowserContext nesnesi
     */
    public static BrowserContext getContext() {
        if (context == null) {
            logger.warn("Context nesnesi null, yeni browser başlatılıyor");
            initBrowser();
        }
        return context;
    }
    
    /**
     * Yeni bir sayfa aç
     * @return Yeni Page nesnesi
     */
    @Step("Yeni sayfa açılıyor")
    public static Page newPage() {
        if (context == null) {
            initBrowser();
        }
        Page newPage = context.newPage();
        logger.info("Yeni sayfa açıldı");
        return newPage;
    }
    
    /**
     * Belirtilen URL'e git
     * @param url Gidilecek URL
     */
    @Step("'{0}' adresine gidiliyor")
    public static void navigateTo(String url) {
        if (page == null) {
            initBrowser();
        }
        
        Page.NavigateOptions options = new Page.NavigateOptions();
        String waitUntil = ConfigReader.getProperty(ConfigReader.PAGE_LOAD_WAIT_UNTIL, "domcontentloaded");
        
        switch (waitUntil.toLowerCase()) {
            case "load":
                options.setWaitUntil(WaitUntilState.LOAD);
                break;
            case "domcontentloaded":
                options.setWaitUntil(WaitUntilState.DOMCONTENTLOADED);
                break;
            case "networkidle":
                options.setWaitUntil(WaitUntilState.NETWORKIDLE);
                break;
            default:
                options.setWaitUntil(WaitUntilState.DOMCONTENTLOADED);
        }
        
        page.navigate(url, options);
        logger.info("'{}' adresine gidildi", url);
    }
    
    /**
     * Ana URL'e git (config'den okur)
     */
    @Step("Ana sayfaya gidiliyor")
    public static void navigateToBaseUrl() {
        String baseUrl = ConfigReader.getProperty(ConfigReader.BASE_URL);
        navigateTo(baseUrl);
    }
    
    /**
     * Browser'ı kapat
     */
    @Step("Browser kapatılıyor")
    public static void closeBrowser() {
        try {
            // Tracing'i kaydet
            if (context != null && ConfigReader.getBooleanProperty(ConfigReader.TRACING_ENABLED, false)) {
                String tracePath = "target/trace-" + System.currentTimeMillis() + ".zip";
                context.tracing().stop(new Tracing.StopOptions()
                    .setPath(java.nio.file.Paths.get(tracePath)));
                logger.info("Tracing kaydedildi: {}", tracePath);
            }
            
            // Sayfayı kapat
            if (page != null && !page.isClosed()) {
                page.close();
                logger.info("Sayfa kapatıldı");
            }
            
            // Context'i kapat
            if (context != null) {
                context.close();
                logger.info("Browser context kapatıldı");
            }
            
            // Browser'ı kapat
            if (browser != null) {
                browser.close();
                logger.info("Browser kapatıldı");
            }
            
            // Playwright'ı kapat
            if (playwright != null) {
                playwright.close();
                logger.info("Playwright kapatıldı");
            }
            
            // Referansları temizle
            page = null;
            context = null;
            browser = null;
            playwright = null;
            
        } catch (Exception e) {
            logger.error("Browser kapatma hatası", e);
        }
    }
    
    /**
     * Ekran görüntüsü al
     * @param fileName Dosya adı (uzantısız)
     * @return Kaydedilen dosyanın tam yolu
     */
    @Step("Ekran görüntüsü alınıyor: {0}")
    public static String takeScreenshot(String fileName) {
        if (page == null) {
            logger.error("Page nesnesi null, ekran görüntüsü alınamadı");
            return null;
        }
        
        try {
            String screenshotPath = ConfigReader.getProperty(ConfigReader.SCREENSHOT_PATH, "target/screenshots");
            java.nio.file.Path path = java.nio.file.Paths.get(screenshotPath);
            
            // Klasör yoksa oluştur
            if (!java.nio.file.Files.exists(path)) {
                java.nio.file.Files.createDirectories(path);
            }
            
            // Dosya adını oluştur
            String fullFileName = fileName + "_" + System.currentTimeMillis() + ".png";
            java.nio.file.Path filePath = path.resolve(fullFileName);
            
            // Ekran görüntüsü al
            page.screenshot(new Page.ScreenshotOptions()
                .setPath(filePath)
                .setFullPage(true));
            
            logger.info("Ekran görüntüsü kaydedildi: {}", filePath);
            return filePath.toString();
            
        } catch (Exception e) {
            logger.error("Ekran görüntüsü alma hatası", e);
            return null;
        }
    }
    
    /**
     * Sayfa başlığını al
     * @return Sayfa başlığı
     */
    public static String getPageTitle() {
        if (page == null) {
            return null;
        }
        return page.title();
    }
    
    /**
     * Mevcut URL'i al
     * @return Mevcut URL
     */
    public static String getCurrentUrl() {
        if (page == null) {
            return null;
        }
        return page.url();
    }
    
    /**
     * Sayfayı yenile
     */
    @Step("Sayfa yenileniyor")
    public static void refreshPage() {
        if (page != null) {
            page.reload();
            logger.info("Sayfa yenilendi");
        }
    }
    
    /**
     * Geri git
     */
    @Step("Geri gidiliyor")
    public static void goBack() {
        if (page != null) {
            page.goBack();
            logger.info("Geri gidildi");
        }
    }
    
    /**
     * İleri git
     */
    @Step("İleri gidiliyor")
    public static void goForward() {
        if (page != null) {
            page.goForward();
            logger.info("İleri gidildi");
        }
    }
    
    /**
     * Yeni sekme aç
     * @param url Açılacak URL
     * @return Yeni sekmenin Page nesnesi
     */
    @Step("Yeni sekmede '{0}' açılıyor")
    public static Page openNewTab(String url) {
        if (context == null) {
            initBrowser();
        }
        
        Page newTab = context.newPage();
        newTab.navigate(url);
        logger.info("Yeni sekmede '{}' açıldı", url);
        return newTab;
    }
    
    /**
     * Browser'ın açık olup olmadığını kontrol et
     * @return Browser açık mı?
     */
    public static boolean isBrowserOpen() {
        return browser != null && browser.isConnected();
    }
    
    /**
     * Console loglarını dinlemeye başla
     */
    public static void startConsoleLogging() {
        if (page != null) {
            page.onConsoleMessage(msg -> {
                logger.debug("Browser Console [{}]: {}", msg.type(), msg.text());
            });
            logger.info("Console log dinleme başlatıldı");
        }
    }
    
    /**
     * Network isteklerini dinlemeye başla
     */
    public static void startNetworkLogging() {
        if (page != null) {
            page.onRequest(request -> {
                logger.debug("Request: {} {}", request.method(), request.url());
            });
            
            page.onResponse(response -> {
                logger.debug("Response: {} {} - {}", response.status(), response.url(), response.statusText());
            });
            
            logger.info("Network log dinleme başlatıldı");
        }
    }
}
