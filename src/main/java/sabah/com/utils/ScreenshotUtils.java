package sabah.com.utils;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.config.ConfigReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Ekran görüntüsü alma işlemleri için yardımcı sınıf
 * Playwright'ın screenshot özelliklerini kolaylaştırır
 */
public class ScreenshotUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    
    // Varsayılan ayarlar
    private static final String DEFAULT_SCREENSHOT_PATH = ConfigReader.getProperty(ConfigReader.SCREENSHOT_PATH, "target/screenshots");
    private static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    
    /**
     * Private constructor - Utility sınıfı
     */
    private ScreenshotUtils() {
        // Utility sınıfı olduğu için private constructor
    }
    
    /**
     * Tam sayfa ekran görüntüsü al
     * @param page Playwright page nesnesi
     * @param fileName Dosya adı (uzantısız)
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeFullPageScreenshot(Page page, String fileName) {
        return takeScreenshot(page, fileName, true);
    }
    
    /**
     * Görünür alan ekran görüntüsü al
     * @param page Playwright page nesnesi
     * @param fileName Dosya adı (uzantısız)
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeVisibleScreenshot(Page page, String fileName) {
        return takeScreenshot(page, fileName, false);
    }
    
    /**
     * Element ekran görüntüsü al
     * @param page Playwright page nesnesi
     * @param fileName Dosya adı (uzantısız)
     * @param selector Element selector'ı
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeElementScreenshot(Page page, String fileName, String selector) {
        try {
            // Klasörü oluştur
            Path screenshotDir = createScreenshotDirectory();
            
            // Dosya adını oluştur
            String fullFileName = generateFileName(fileName);
            Path filePath = screenshotDir.resolve(fullFileName);
            
            // Element ekran görüntüsü al
            Locator element = page.locator(selector);
            element.screenshot(new Locator.ScreenshotOptions().setPath(filePath));
            
            logger.info("Element ekran görüntüsü kaydedildi: {}", filePath);
            return filePath.toString();
            
        } catch (Exception e) {
            logger.error("Element ekran görüntüsü alma hatası: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Ekran görüntüsü al - genel metod
     * @param page Playwright page nesnesi
     * @param fileName Dosya adı (uzantısız)
     * @param fullPage Tam sayfa mı?
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeScreenshot(Page page, String fileName, boolean fullPage) {
        try {
            // Klasörü oluştur
            Path screenshotDir = createScreenshotDirectory();
            
            // Dosya adını oluştur
            String fullFileName = generateFileName(fileName);
            Path filePath = screenshotDir.resolve(fullFileName);
            
            // Ekran görüntüsü al
            page.screenshot(new Page.ScreenshotOptions()
                .setPath(filePath)
                .setFullPage(fullPage));
            
            logger.info("Ekran görüntüsü kaydedildi: {}", filePath);
            return filePath.toString();
            
        } catch (Exception e) {
            logger.error("Ekran görüntüsü alma hatası: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Hata durumu için ekran görüntüsü al
     * @param page Playwright page nesnesi
     * @param testName Test adı
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeErrorScreenshot(Page page, String testName) {
        return takeScreenshot(page, "ERROR_" + testName, true);
    }
    
    /**
     * Başarı durumu için ekran görüntüsü al
     * @param page Playwright page nesnesi
     * @param testName Test adı
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeSuccessScreenshot(Page page, String testName) {
        return takeScreenshot(page, "SUCCESS_" + testName, false);
    }
    
    /**
     * Belirli bir alanın ekran görüntüsünü al
     * @param page Playwright page nesnesi
     * @param fileName Dosya adı (uzantısız)
     * @param x X koordinatı
     * @param y Y koordinatı
     * @param width Genişlik
     * @param height Yükseklik
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeAreaScreenshot(Page page, String fileName, int x, int y, int width, int height) {
        try {
            // Klasörü oluştur
            Path screenshotDir = createScreenshotDirectory();
            
            // Dosya adını oluştur
            String fullFileName = generateFileName(fileName);
            Path filePath = screenshotDir.resolve(fullFileName);
            
            // Belirli alanın ekran görüntüsünü al
            page.screenshot(new Page.ScreenshotOptions()
                .setPath(filePath)
                .setClip(x, y, width, height));
            
            logger.info("Alan ekran görüntüsü kaydedildi: {}", filePath);
            return filePath.toString();
            
        } catch (Exception e) {
            logger.error("Alan ekran görüntüsü alma hatası: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Ekran görüntüsü klasörünü oluştur
     * @return Oluşturulan klasör yolu
     */
    private static Path createScreenshotDirectory() throws Exception {
        Path screenshotDir = Paths.get(DEFAULT_SCREENSHOT_PATH);
        
        if (!Files.exists(screenshotDir)) {
            Files.createDirectories(screenshotDir);
            logger.debug("Ekran görüntüsü klasörü oluşturuldu: {}", screenshotDir);
        }
        
        return screenshotDir;
    }
    
    /**
     * Dosya adını oluştur
     * @param baseName Temel dosya adı
     * @return Tam dosya adı
     */
    private static String generateFileName(String baseName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMESTAMP_FORMAT));
        return baseName + "_" + timestamp + ".png";
    }
    
    /**
     * Ekran görüntüsü byte array olarak al
     * @param page Playwright page nesnesi
     * @param fullPage Tam sayfa mı?
     * @return Ekran görüntüsü byte array
     */
    public static byte[] takeScreenshotAsBytes(Page page, boolean fullPage) {
        try {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                .setFullPage(fullPage));
            
            logger.debug("Ekran görüntüsü byte array olarak alındı");
            return screenshot;
            
        } catch (Exception e) {
            logger.error("Ekran görüntüsü byte array alma hatası: {}", e.getMessage());
            return new byte[0];
        }
    }
    
    /**
     * Ekran görüntüsü klasörünü temizle
     * @param daysToKeep Saklanacak gün sayısı
     */
    public static void cleanScreenshotDirectory(int daysToKeep) {
        try {
            Path screenshotDir = Paths.get(DEFAULT_SCREENSHOT_PATH);
            
            if (Files.exists(screenshotDir)) {
                LocalDateTime cutoffDate = LocalDateTime.now().minusDays(daysToKeep);
                
                Files.list(screenshotDir)
                    .filter(path -> path.toString().endsWith(".png"))
                    .filter(path -> {
                        try {
                            return Files.getLastModifiedTime(path).toInstant()
                                .isBefore(cutoffDate.toInstant(java.time.ZoneOffset.UTC));
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                            logger.debug("Eski ekran görüntüsü silindi: {}", path);
                        } catch (Exception e) {
                            logger.warn("Ekran görüntüsü silinemedi: {} - {}", path, e.getMessage());
                        }
                    });
                
                logger.info("Ekran görüntüsü klasörü temizlendi");
            }
            
        } catch (Exception e) {
            logger.error("Ekran görüntüsü klasörü temizleme hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Ekran görüntüsü sayısını al
     * @return Ekran görüntüsü sayısı
     */
    public static long getScreenshotCount() {
        try {
            Path screenshotDir = Paths.get(DEFAULT_SCREENSHOT_PATH);
            
            if (Files.exists(screenshotDir)) {
                return Files.list(screenshotDir)
                    .filter(path -> path.toString().endsWith(".png"))
                    .count();
            }
            
            return 0;
            
        } catch (Exception e) {
            logger.error("Ekran görüntüsü sayısı alma hatası: {}", e.getMessage());
            return 0;
        }
    }
}
