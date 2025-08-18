package sabah.com.visual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import sabah.com.data.TestDataManager;
import sabah.com.utils.ScreenshotUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Görsel test yönetimi için sınıf
 * Screenshot karşılaştırma ve görsel doğrulama işlemleri
 */
public class VisualTestManager {
    
    private static final Logger logger = LoggerFactory.getLogger(VisualTestManager.class);
    private static final String BASELINE_PATH = TestDataManager.getTestData("visual.baseline.path");
    private static final String SCREENSHOT_PATH = TestDataManager.getTestData("visual.screenshot.path");
    private static final double THRESHOLD = Double.parseDouble(TestDataManager.getTestData("visual.threshold", "0.95"));
    
    /**
     * Görsel test yap
     * @param driver WebDriver
     * @param testName Test adı
     * @return Görsel test sonucu
     */
    public static VisualTestResult compareScreenshot(WebDriver driver, String testName) {
        try {
            // Test dizinlerini oluştur
            createDirectories();
            
            // Mevcut screenshot al
            String currentScreenshotPath = takeCurrentScreenshot(driver, testName);
            
            // Baseline screenshot'ı kontrol et
            String baselineScreenshotPath = getBaselinePath(testName);
            
            if (!Files.exists(Paths.get(baselineScreenshotPath))) {
                // Baseline yoksa oluştur
                createBaseline(currentScreenshotPath, baselineScreenshotPath);
                return new VisualTestResult(true, 1.0, "Baseline oluşturuldu", currentScreenshotPath, baselineScreenshotPath);
            }
            
            // Karşılaştırma yap
            double similarity = compareImages(baselineScreenshotPath, currentScreenshotPath);
            boolean isPassed = similarity >= THRESHOLD;
            
            String message = isPassed ? 
                "Görsel test başarılı" : 
                String.format("Görsel test başarısız. Benzerlik: %.2f, Eşik: %.2f", similarity, THRESHOLD);
            
            return new VisualTestResult(isPassed, similarity, message, currentScreenshotPath, baselineScreenshotPath);
            
        } catch (Exception e) {
            logger.error("Görsel test hatası: {}", e.getMessage());
            return new VisualTestResult(false, 0.0, "Görsel test hatası: " + e.getMessage(), "", "");
        }
    }
    
    /**
     * Baseline screenshot oluştur
     * @param driver WebDriver
     * @param testName Test adı
     * @return Başarılı mı?
     */
    public static boolean createBaseline(WebDriver driver, String testName) {
        try {
            createDirectories();
            String currentScreenshotPath = takeCurrentScreenshot(driver, testName);
            String baselineScreenshotPath = getBaselinePath(testName);
            
            createBaseline(currentScreenshotPath, baselineScreenshotPath);
            logger.info("Baseline oluşturuldu: {}", baselineScreenshotPath);
            return true;
            
        } catch (Exception e) {
            logger.error("Baseline oluşturma hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Mevcut screenshot al
     * @param driver WebDriver
     * @param testName Test adı
     * @return Screenshot dosya yolu
     */
    private static String takeCurrentScreenshot(WebDriver driver, String testName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = SCREENSHOT_PATH + File.separator + fileName;
        
        ScreenshotUtils.takeScreenshot(driver, filePath);
        return filePath;
    }
    
    /**
     * Baseline dosya yolunu al
     * @param testName Test adı
     * @return Baseline dosya yolu
     */
    private static String getBaselinePath(String testName) {
        return BASELINE_PATH + File.separator + testName + "_baseline.png";
    }
    
    /**
     * Baseline oluştur
     * @param currentPath Mevcut screenshot yolu
     * @param baselinePath Baseline yolu
     */
    private static void createBaseline(String currentPath, String baselinePath) throws IOException {
        Files.copy(Paths.get(currentPath), Paths.get(baselinePath));
    }
    
    /**
     * Dizinleri oluştur
     */
    private static void createDirectories() throws IOException {
        Files.createDirectories(Paths.get(BASELINE_PATH));
        Files.createDirectories(Paths.get(SCREENSHOT_PATH));
    }
    
    /**
     * İki resmi karşılaştır
     * @param baselinePath Baseline resim yolu
     * @param currentPath Mevcut resim yolu
     * @return Benzerlik oranı (0.0 - 1.0)
     */
    private static double compareImages(String baselinePath, String currentPath) {
        try {
            BufferedImage baseline = ImageIO.read(new File(baselinePath));
            BufferedImage current = ImageIO.read(new File(currentPath));
            
            // Boyut kontrolü
            if (baseline.getWidth() != current.getWidth() || baseline.getHeight() != current.getHeight()) {
                logger.warn("Resim boyutları farklı: Baseline({}x{}), Current({}x{})", 
                        baseline.getWidth(), baseline.getHeight(), 
                        current.getWidth(), current.getHeight());
                return 0.0;
            }
            
            // Pixel karşılaştırma
            int totalPixels = baseline.getWidth() * baseline.getHeight();
            int matchingPixels = 0;
            
            for (int x = 0; x < baseline.getWidth(); x++) {
                for (int y = 0; y < baseline.getHeight(); y++) {
                    if (baseline.getRGB(x, y) == current.getRGB(x, y)) {
                        matchingPixels++;
                    }
                }
            }
            
            double similarity = (double) matchingPixels / totalPixels;
            logger.debug("Görsel benzerlik: {}/{} = {:.2f}", matchingPixels, totalPixels, similarity);
            
            return similarity;
            
        } catch (IOException e) {
            logger.error("Resim karşılaştırma hatası: {}", e.getMessage());
            return 0.0;
        }
    }
    
    /**
     * Tüm baseline'ları temizle
     */
    public static void clearAllBaselines() {
        try {
            File baselineDir = new File(BASELINE_PATH);
            if (baselineDir.exists()) {
                File[] files = baselineDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.getName().endsWith("_baseline.png")) {
                            file.delete();
                        }
                    }
                }
            }
            logger.info("Tüm baseline'lar temizlendi");
        } catch (Exception e) {
            logger.error("Baseline temizleme hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Belirli bir baseline'ı sil
     * @param testName Test adı
     */
    public static void deleteBaseline(String testName) {
        try {
            String baselinePath = getBaselinePath(testName);
            File baselineFile = new File(baselinePath);
            if (baselineFile.exists()) {
                baselineFile.delete();
                logger.info("Baseline silindi: {}", baselinePath);
            }
        } catch (Exception e) {
            logger.error("Baseline silme hatası: {}", e.getMessage());
        }
    }
}
