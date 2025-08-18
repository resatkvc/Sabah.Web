package sabah.com.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.config.ConfigReader;
import sabah.com.utils.BrowserManager;
import io.qameta.allure.Attachment;

/**
 * Tüm test sınıflarının extend edeceği temel test sınıfı
 * Test öncesi ve sonrası işlemleri burada yönetilir
 */
public abstract class BaseTest {
    
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;
    
    /**
     * Test suite başlamadan önce bir kez çalışır
     */
    @BeforeSuite
    public void beforeSuite() {
        logger.info("=== Test Suite Başlıyor ===");
        logger.info("Test Ortamı: {}", ConfigReader.getProperty(ConfigReader.TEST_ENVIRONMENT));
        logger.info("Base URL: {}", ConfigReader.getProperty(ConfigReader.BASE_URL));
        logger.info("Browser: {}", ConfigReader.getProperty(ConfigReader.BROWSER_TYPE));
        logger.info("Headless: {}", ConfigReader.getBooleanProperty(ConfigReader.BROWSER_HEADLESS));
    }
    
    /**
     * Her test class'ından önce bir kez çalışır
     */
    @BeforeClass
    public void beforeClass() {
        logger.info("Test Class başlıyor: {}", this.getClass().getSimpleName());
    }
    
    /**
     * Her test metodundan önce çalışır
     */
    @BeforeMethod
    public void beforeMethod() {
        logger.info("Test metodu başlıyor...");
        
        // Browser'ı başlat
        driver = BrowserManager.initBrowser();
        
        // Ana sayfaya git
        BrowserManager.navigateToBaseUrl();
        
        // Sayfa yükleme kontrolü
        try {
            logger.info("Sayfa yükleme kontrolü yapılıyor...");
            
            // Sayfa başlığını kontrol et
            String pageTitle = driver.getTitle();
            logger.info("Sayfa başlığı: {}", pageTitle);
            
            // URL kontrolü
            String currentUrl = driver.getCurrentUrl();
            logger.info("Mevcut URL: {}", currentUrl);
            
            if (!currentUrl.contains("sabah.com.tr")) {
                logger.error("Yanlış URL'deyiz: {}", currentUrl);
                throw new RuntimeException("Sayfa doğru URL'de değil: " + currentUrl);
            }
            
            logger.info("Sayfa başarıyla yüklendi");
            
        } catch (Exception e) {
            logger.error("Sayfa yükleme hatası: {}", e.getMessage());
            throw new RuntimeException("Sayfa yüklenemedi", e);
        }
        
        logger.info("Test hazırlıkları tamamlandı");
    }
    
    /**
     * Her test metodundan sonra çalışır
     */
    @AfterMethod
    public void afterMethod(ITestResult result) {
        try {
            // Test sonucunu logla
            if (result.isSuccess()) {
                logger.info("✓ Test BAŞARILI: {}", result.getMethod().getMethodName());
            } else {
                logger.error("✗ Test BAŞARISIZ: {}", result.getMethod().getMethodName());
                logger.error("Hata: ", result.getThrowable());
                
                // Hata durumunda ekran görüntüsü al
                if (ConfigReader.getBooleanProperty(ConfigReader.SCREENSHOT_ON_FAILURE, true)) {
                    takeScreenshotOnFailure(result.getMethod().getMethodName());
                }
            }
            
            // Test süresi
            long duration = result.getEndMillis() - result.getStartMillis();
            logger.info("Test süresi: {} ms", duration);
            
        } catch (Exception e) {
            logger.error("AfterMethod hatası", e);
        } finally {
            // Browser'ı kapat
            BrowserManager.closeBrowser();
            logger.info("Browser kapatıldı");
        }
    }
    
    /**
     * Her test class'ından sonra bir kez çalışır
     */
    @AfterClass
    public void afterClass() {
        logger.info("Test Class tamamlandı: {}", this.getClass().getSimpleName());
    }
    
    /**
     * Test suite bittikten sonra bir kez çalışır
     */
    @AfterSuite
    public void afterSuite() {
        logger.info("=== Test Suite Tamamlandı ===");
    }
    
    /**
     * Test başarısız olduğunda ekran görüntüsü al
     * @param testName Test adı
     * @return Ekran görüntüsü byte array
     */
    @Attachment(value = "Hata Ekran Görüntüsü - {0}", type = "image/png")
    private byte[] takeScreenshotOnFailure(String testName) {
        try {
            if (driver != null) {
                byte[] screenshot = BrowserManager.takeScreenshotAsBytes();
                logger.info("Hata ekran görüntüsü alındı: {}", testName);
                
                // Ayrıca dosyaya da kaydet
                BrowserManager.takeScreenshot("HATA_" + testName);
                
                return screenshot;
            }
        } catch (Exception e) {
            logger.error("Ekran görüntüsü alma hatası", e);
        }
        return new byte[0];
    }
    
    /**
     * Test sırasında ekran görüntüsü al (Allure raporu için)
     * @param screenshotName Ekran görüntüsü adı
     * @return Ekran görüntüsü byte array
     */
    @Attachment(value = "{0}", type = "image/png")
    protected byte[] takeScreenshot(String screenshotName) {
        try {
            if (driver != null) {
                byte[] screenshot = BrowserManager.takeScreenshotAsBytes();
                logger.info("Ekran görüntüsü alındı: {}", screenshotName);
                return screenshot;
            }
        } catch (Exception e) {
            logger.error("Ekran görüntüsü alma hatası", e);
        }
        return new byte[0];
    }
    
    /**
     * Sayfa kaynağını Allure raporuna ekle
     * @return Sayfa HTML içeriği
     */
    @Attachment(value = "Sayfa Kaynağı", type = "text/html")
    protected String attachPageSource() {
        try {
            if (driver != null) {
                return driver.getPageSource();
            }
        } catch (Exception e) {
            logger.error("Sayfa kaynağı alma hatası", e);
        }
        return "Sayfa kaynağı alınamadı";
    }
    
    /**
     * Test datasını parametre olarak al
     * @param parameters Test parametreleri
     */
    @Parameters
    public void setTestParameters(String... parameters) {
        if (parameters != null && parameters.length > 0) {
            logger.info("Test parametreleri: {}", String.join(", ", parameters));
        }
    }
    
    /**
     * Belirli bir süre bekle (test amaçlı)
     * @param seconds Saniye cinsinden bekleme süresi
     */
    protected void waitForSeconds(int seconds) {
        try {
            logger.debug("{} saniye bekleniyor...", seconds);
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            logger.error("Bekleme hatası", e);
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Test retry sayısını al
     * @return Retry sayısı
     */
    protected int getRetryCount() {
        return ConfigReader.getIntProperty(ConfigReader.TEST_RETRY_COUNT, 0);
    }
    
    /**
     * Test ortamını al
     * @return Test ortamı (production, staging, test vb.)
     */
    protected String getTestEnvironment() {
        return ConfigReader.getProperty(ConfigReader.TEST_ENVIRONMENT, "production");
    }
    
    /**
     * Paralel test çalışıyor mu kontrolü
     * @return Paralel test aktif mi?
     */
    protected boolean isParallelExecution() {
        return ConfigReader.getBooleanProperty(ConfigReader.PARALLEL_TESTS, false);
    }
}
