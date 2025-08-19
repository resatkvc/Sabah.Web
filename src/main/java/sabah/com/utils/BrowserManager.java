package sabah.com.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.config.ConfigReader;
import io.qameta.allure.Step;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Selenium WebDriver yonetimi icin utility sinifi
 * Browser acma, kapatma ve konfigurasyon islemlerini yonetir
 */
public class BrowserManager {
    
    private static final Logger logger = LoggerFactory.getLogger(BrowserManager.class);
    
    private static WebDriver driver;
    private static String screenshotDir = "target/screenshots";
    
    /**
     * Private constructor - Singleton pattern
     */
    private BrowserManager() {
        // Utility sinifi oldugu icin private constructor
    }
    
    /**
     * Browser'i baslat ve yapilandir
     * @return Yapilandirilmis WebDriver nesnesi
     */
    @Step("Browser baslatiliyor")
    public static WebDriver initBrowser() {
        try {
            // Browser tipini config'den al
            String browserType = ConfigReader.getProperty(ConfigReader.BROWSER_TYPE, "chrome");
            boolean headless = ConfigReader.getBooleanProperty(ConfigReader.BROWSER_HEADLESS, false);
            
            // Browser'i baslat
            switch (browserType.toLowerCase()) {
                case "chrome":
                    driver = createChromeDriver(headless);
                    logger.info("Chrome browser baslatildi");
                    break;
                case "firefox":
                    driver = createFirefoxDriver(headless);
                    logger.info("Firefox browser baslatildi");
                    break;
                case "edge":
                    driver = createEdgeDriver(headless);
                    logger.info("Edge browser baslatildi");
                    break;
                case "safari":
                    driver = createSafariDriver(headless);
                    logger.info("Safari browser baslatildi");
                    break;
                default:
                    driver = createChromeDriver(headless);
                    logger.warn("Bilinmeyen browser tipi: {}. Chrome kullaniliyor.", browserType);
            }
            
            // Browser ayarlari
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(
                java.time.Duration.ofSeconds(ConfigReader.getIntProperty(ConfigReader.WAIT_TIMEOUT_DEFAULT, 10))
            );
            driver.manage().timeouts().pageLoadTimeout(
                java.time.Duration.ofSeconds(ConfigReader.getIntProperty(ConfigReader.PAGE_LOAD_TIMEOUT, 30))
            );
            
            logger.info("Browser basariyla baslatildi: {}", browserType);
            return driver;
            
        } catch (Exception e) {
            logger.error("Browser baslatma hatasi: {}", e.getMessage());
            throw new RuntimeException("Browser baslatilamadi", e);
        }
    }
    
    /**
     * Chrome driver oluştur
     * @param headless Headless mod
     * @return Chrome WebDriver
     */
    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Chrome ayarları
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--ignore-ssl-errors");
        options.addArguments("--start-maximized");
        
        return new ChromeDriver(options);
    }
    
    /**
     * Firefox driver oluştur
     * @param headless Headless mod
     * @return Firefox WebDriver
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Firefox ayarları
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Edge driver oluştur
     * @param headless Headless mod
     * @return Edge WebDriver
     */
    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Edge ayarları
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        
        return new EdgeDriver(options);
    }
    
    /**
     * Safari driver oluştur
     * @param headless Headless mod (Safari'de desteklenmez)
     * @return Safari WebDriver
     */
    private static WebDriver createSafariDriver(boolean headless) {
        WebDriverManager.safaridriver().setup();
        SafariOptions options = new SafariOptions();
        
        // Safari headless modu desteklemez
        if (headless) {
            logger.warn("Safari headless modu desteklemez, normal mod kullanılıyor");
        }
        
        return new SafariDriver(options);
    }
    
    /**
     * Ana sayfaya git
     */
    @Step("Ana sayfaya gidiliyor")
    public static void navigateToBaseUrl() {
        try {
            String baseUrl = ConfigReader.getProperty(ConfigReader.BASE_URL);
            logger.info("Ana sayfaya gidiliyor: {}", baseUrl);
            driver.navigate().to(baseUrl);
            
            // Sayfa yükleme kontrolü
            waitForPageLoad();
            
        } catch (Exception e) {
            logger.error("Ana sayfaya gitme hatası: {}", e.getMessage());
            throw new RuntimeException("Ana sayfaya gidilemedi", e);
        }
    }
    
    /**
     * Sayfa yükleme kontrolü
     */
    private static void waitForPageLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(30));
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
            logger.info("Sayfa tamamen yüklendi");
        } catch (Exception e) {
            logger.warn("Sayfa yükleme kontrolü başarısız: {}", e.getMessage());
        }
    }
    
    /**
     * Browser'ı kapat
     */
    @Step("Browser kapatılıyor")
    public static void closeBrowser() {
        try {
            if (driver != null) {
                driver.quit();
                driver = null;
                logger.info("Browser başarıyla kapatıldı");
            }
        } catch (Exception e) {
            logger.error("Browser kapatma hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Ekran görüntüsü al
     * @param screenshotName Ekran görüntüsü adı
     */
    public static void takeScreenshot(String screenshotName) {
        try {
            // Screenshot dizinini oluştur
            File directory = new File(screenshotDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Dosya adını oluştur
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = screenshotName + "_" + timestamp + ".png";
            String filePath = screenshotDir + File.separator + fileName;
            
            // Screenshot al
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            
            // Dosyaya kaydet
            Files.copy(screenshot.toPath(), Paths.get(filePath));
            
            logger.info("Ekran görüntüsü kaydedildi: {}", filePath);
            
        } catch (IOException e) {
            logger.error("Ekran görüntüsü alma hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Ekran görüntüsü al ve byte array olarak döndür
     * @return Ekran görüntüsü byte array
     */
    public static byte[] takeScreenshotAsBytes() {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            return ts.getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Ekran görüntüsü alma hatası: {}", e.getMessage());
            return new byte[0];
        }
    }
    
    /**
     * Mevcut WebDriver'ı al
     * @return WebDriver nesnesi
     */
    public static WebDriver getDriver() {
        return driver;
    }
    
    /**
     * Browser'ın açık olup olmadığını kontrol et
     * @return Browser açık mı?
     */
    public static boolean isBrowserOpen() {
        try {
            if (driver != null) {
                driver.getCurrentUrl();
                return true;
            }
        } catch (Exception e) {
            logger.debug("Browser kontrol hatası: {}", e.getMessage());
        }
        return false;
    }
    
    /**
     * Browser'ı yeniden başlat
     * @return Yeni WebDriver nesnesi
     */
    public static WebDriver restartBrowser() {
        logger.info("Browser yeniden başlatılıyor...");
        closeBrowser();
        return initBrowser();
    }
}
