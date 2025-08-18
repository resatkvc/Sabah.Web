package sabah.com.utils;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.config.ConfigReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Ekran görüntüsü alma işlemleri için yardımcı sınıf
 * Selenium WebDriver ile ekran görüntüsü alma işlemlerini kolaylaştırır
 */
public class ScreenshotUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    
    // Screenshot dizini
    private static final String SCREENSHOT_DIR = ConfigReader.getProperty(ConfigReader.SCREENSHOT_PATH, "target/screenshots");
    
    /**
     * Private constructor - Utility sınıfı
     */
    private ScreenshotUtils() {
        // Utility sınıfı olduğu için private constructor
    }
    
    /**
     * Ekran görüntüsü al ve dosyaya kaydet
     * @param driver WebDriver nesnesi
     * @param fileName Dosya adı (uzantısız)
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeScreenshot(WebDriver driver, String fileName) {
        try {
            // Screenshot dizinini oluştur
            createScreenshotDirectory();
            
            // Dosya adını oluştur
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fullFileName = fileName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + File.separator + fullFileName;
            
            // Screenshot al
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            
            // Dosyaya kaydet
            Files.copy(screenshot.toPath(), Paths.get(filePath));
            
            logger.info("Ekran görüntüsü kaydedildi: {}", filePath);
            return filePath;
            
        } catch (IOException e) {
            logger.error("Ekran görüntüsü alma hatası: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Ekran görüntüsü al ve byte array olarak döndür
     * @param driver WebDriver nesnesi
     * @return Ekran görüntüsü byte array
     */
    public static byte[] takeScreenshotAsBytes(WebDriver driver) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            logger.debug("Ekran görüntüsü byte array olarak alındı");
            return screenshot;
        } catch (Exception e) {
            logger.error("Ekran görüntüsü alma hatası: {}", e.getMessage());
            return new byte[0];
        }
    }
    
    /**
     * Belirli bir elementin ekran görüntüsünü al
     * @param driver WebDriver nesnesi
     * @param element Screenshot alınacak element
     * @param fileName Dosya adı (uzantısız)
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeElementScreenshot(WebDriver driver, WebElement element, String fileName) {
        try {
            // Screenshot dizinini oluştur
            createScreenshotDirectory();
            
            // Dosya adını oluştur
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fullFileName = fileName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + File.separator + fullFileName;
            
            // Element screenshot al
            File screenshot = element.getScreenshotAs(OutputType.FILE);
            
            // Dosyaya kaydet
            Files.copy(screenshot.toPath(), Paths.get(filePath));
            
            logger.info("Element ekran görüntüsü kaydedildi: {}", filePath);
            return filePath;
            
        } catch (IOException e) {
            logger.error("Element ekran görüntüsü alma hatası: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Belirli bir elementin ekran görüntüsünü al ve byte array olarak döndür
     * @param driver WebDriver nesnesi
     * @param element Screenshot alınacak element
     * @return Ekran görüntüsü byte array
     */
    public static byte[] takeElementScreenshotAsBytes(WebDriver driver, WebElement element) {
        try {
            byte[] screenshot = element.getScreenshotAs(OutputType.BYTES);
            logger.debug("Element ekran görüntüsü byte array olarak alındı");
            return screenshot;
        } catch (Exception e) {
            logger.error("Element ekran görüntüsü alma hatası: {}", e.getMessage());
            return new byte[0];
        }
    }
    
    /**
     * Tam sayfa ekran görüntüsü al
     * @param driver WebDriver nesnesi
     * @param fileName Dosya adı (uzantısız)
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeFullPageScreenshot(WebDriver driver, String fileName) {
        try {
            // Screenshot dizinini oluştur
            createScreenshotDirectory();
            
            // Dosya adını oluştur
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fullFileName = fileName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + File.separator + fullFileName;
            
            // JavaScript ile tam sayfa screenshot al
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            // Sayfa yüksekliğini al
            Long pageHeight = (Long) js.executeScript("return document.body.scrollHeight");
            Long viewportHeight = (Long) js.executeScript("return window.innerHeight");
            
            // Tam sayfa screenshot için scroll ve birleştirme
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screenshot = ts.getScreenshotAs(OutputType.FILE);
            
            // Dosyaya kaydet
            Files.copy(screenshot.toPath(), Paths.get(filePath));
            
            logger.info("Tam sayfa ekran görüntüsü kaydedildi: {}", filePath);
            return filePath;
            
        } catch (IOException e) {
            logger.error("Tam sayfa ekran görüntüsü alma hatası: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Hata durumunda otomatik ekran görüntüsü al
     * @param driver WebDriver nesnesi
     * @param testName Test adı
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeErrorScreenshot(WebDriver driver, String testName) {
        String fileName = "ERROR_" + testName.replaceAll("[^a-zA-Z0-9]", "_");
        return takeScreenshot(driver, fileName);
    }
    
    /**
     * Başarı durumunda otomatik ekran görüntüsü al
     * @param driver WebDriver nesnesi
     * @param testName Test adı
     * @return Kaydedilen dosyanın tam yolu
     */
    public static String takeSuccessScreenshot(WebDriver driver, String testName) {
        String fileName = "SUCCESS_" + testName.replaceAll("[^a-zA-Z0-9]", "_");
        return takeScreenshot(driver, fileName);
    }
    
    /**
     * Screenshot dizinini oluştur
     */
    private static void createScreenshotDirectory() {
        try {
            File directory = new File(SCREENSHOT_DIR);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (created) {
                    logger.debug("Screenshot dizini oluşturuldu: {}", SCREENSHOT_DIR);
                } else {
                    logger.warn("Screenshot dizini oluşturulamadı: {}", SCREENSHOT_DIR);
                }
            }
        } catch (Exception e) {
            logger.error("Screenshot dizini oluşturma hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Screenshot dizinini temizle
     * @param olderThanDays Belirtilen günden eski dosyaları sil
     */
    public static void cleanScreenshotDirectory(int olderThanDays) {
        try {
            File directory = new File(SCREENSHOT_DIR);
            if (!directory.exists()) {
                return;
            }
            
            File[] files = directory.listFiles();
            if (files == null) {
                return;
            }
            
            long cutoffTime = System.currentTimeMillis() - (olderThanDays * 24 * 60 * 60 * 1000L);
            int deletedCount = 0;
            
            for (File file : files) {
                if (file.isFile() && file.lastModified() < cutoffTime) {
                    if (file.delete()) {
                        deletedCount++;
                        logger.debug("Eski screenshot dosyası silindi: {}", file.getName());
                    }
                }
            }
            
            logger.info("{} eski screenshot dosyası silindi", deletedCount);
            
        } catch (Exception e) {
            logger.error("Screenshot dizini temizleme hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Screenshot dizinindeki dosya sayısını al
     * @return Dosya sayısı
     */
    public static int getScreenshotCount() {
        try {
            File directory = new File(SCREENSHOT_DIR);
            if (!directory.exists()) {
                return 0;
            }
            
            File[] files = directory.listFiles();
            return files != null ? files.length : 0;
            
        } catch (Exception e) {
            logger.error("Screenshot sayısı alma hatası: {}", e.getMessage());
            return 0;
        }
    }
    
    /**
     * Screenshot dizininin boyutunu al
     * @return Dizin boyutu (byte)
     */
    public static long getScreenshotDirectorySize() {
        try {
            File directory = new File(SCREENSHOT_DIR);
            if (!directory.exists()) {
                return 0;
            }
            
            long size = 0;
            File[] files = directory.listFiles();
            
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        size += file.length();
                    }
                }
            }
            
            return size;
            
        } catch (Exception e) {
            logger.error("Screenshot dizin boyutu alma hatası: {}", e.getMessage());
            return 0;
        }
    }
    
    /**
     * Screenshot dizinini sıkıştır (opsiyonel)
     * @param zipFileName Sıkıştırılmış dosya adı
     * @return Sıkıştırılmış dosyanın yolu
     */
    public static String compressScreenshotDirectory(String zipFileName) {
        try {
            // Bu metod implement edilebilir (Apache Commons Compress kullanarak)
            logger.info("Screenshot dizini sıkıştırma özelliği henüz implement edilmedi");
            return null;
        } catch (Exception e) {
            logger.error("Screenshot dizini sıkıştırma hatası: {}", e.getMessage());
            return null;
        }
    }
}
