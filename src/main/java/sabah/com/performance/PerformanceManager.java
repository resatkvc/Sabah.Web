package sabah.com.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import sabah.com.data.TestDataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Performans test yönetimi için sınıf
 * Sayfa yükleme süreleri, yük testleri ve performans metrikleri
 */
public class PerformanceManager {
    
    private static final Logger logger = LoggerFactory.getLogger(PerformanceManager.class);
    private static final int LOAD_TIME_THRESHOLD = Integer.parseInt(TestDataManager.getTestData("performance.load.time", "10"));
    private static final int TIMEOUT_THRESHOLD = Integer.parseInt(TestDataManager.getTestData("performance.timeout", "30"));
    private static final int RETRY_COUNT = Integer.parseInt(TestDataManager.getTestData("performance.retry.count", "3"));
    
    /**
     * Sayfa yükleme süresini ölç
     * @param driver WebDriver
     * @param url Test edilecek URL
     * @return Performans sonucu
     */
    public static PerformanceResult measurePageLoadTime(WebDriver driver, String url) {
        long startTime = System.currentTimeMillis();
        long domReadyTime = 0;
        long pageLoadTime = 0;
        
        try {
            logger.info("Sayfa yükleme süresi ölçülüyor: {}", url);
            
            // Sayfa yükleme başlangıcı
            driver.get(url);
            
            // DOM Ready zamanı
            domReadyTime = System.currentTimeMillis() - startTime;
            
            // Tam sayfa yükleme zamanı
            pageLoadTime = System.currentTimeMillis() - startTime;
            
            // JavaScript ile ek metrikler al
            String jsMetrics = getJavaScriptMetrics(driver);
            
            PerformanceResult result = new PerformanceResult(
                    url,
                    domReadyTime,
                    pageLoadTime,
                    jsMetrics,
                    pageLoadTime <= LOAD_TIME_THRESHOLD * 1000
            );
            
            logger.info("Sayfa yükleme tamamlandı: {}ms (Eşik: {}ms)", 
                    pageLoadTime, LOAD_TIME_THRESHOLD * 1000);
            
            return result;
            
        } catch (Exception e) {
            logger.error("Sayfa yükleme hatası: {}", e.getMessage());
            return new PerformanceResult(url, 0, 0, "", false);
        }
    }
    
    /**
     * JavaScript metriklerini al
     * @param driver WebDriver
     * @return JavaScript metrikleri
     */
    private static String getJavaScriptMetrics(WebDriver driver) {
        try {
            // Navigation Timing API kullanarak detaylı metrikler
            String script = "return window.performance.timing;";
            Object timing = driver.executeScript(script);
            
            // Basit metrikler
            String readyState = (String) driver.executeScript("return document.readyState;");
            String title = driver.getTitle();
            
            return String.format("readyState=%s, title=%s", readyState, title);
            
        } catch (Exception e) {
            logger.warn("JavaScript metrikleri alınamadı: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Yük testi yap
     * @param url Test edilecek URL
     * @param concurrentUsers Eş zamanlı kullanıcı sayısı
     * @param duration Test süresi (saniye)
     * @return Yük test sonucu
     */
    public static LoadTestResult performLoadTest(String url, int concurrentUsers, int duration) {
        logger.info("Yük testi başlıyor: {} kullanıcı, {} saniye", concurrentUsers, duration);
        
        List<PerformanceResult> results = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(concurrentUsers);
        
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (duration * 1000);
        
        try {
            // Eş zamanlı istekler gönder
            List<CompletableFuture<PerformanceResult>> futures = new ArrayList<>();
            
            while (System.currentTimeMillis() < endTime) {
                for (int i = 0; i < concurrentUsers; i++) {
                    CompletableFuture<PerformanceResult> future = CompletableFuture.supplyAsync(() -> {
                        // Her thread için yeni WebDriver oluştur
                        WebDriver threadDriver = createThreadDriver();
                        try {
                            return measurePageLoadTime(threadDriver, url);
                        } finally {
                            if (threadDriver != null) {
                                threadDriver.quit();
                            }
                        }
                    }, executor);
                    
                    futures.add(future);
                }
                
                // Kısa bekleme
                Thread.sleep(1000);
            }
            
            // Sonuçları topla
            for (CompletableFuture<PerformanceResult> future : futures) {
                try {
                    PerformanceResult result = future.get(30, TimeUnit.SECONDS);
                    results.add(result);
                } catch (Exception e) {
                    logger.warn("Thread sonucu alınamadı: {}", e.getMessage());
                }
            }
            
        } catch (Exception e) {
            logger.error("Yük testi hatası: {}", e.getMessage());
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(30, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        return analyzeLoadTestResults(results, concurrentUsers, duration);
    }
    
    /**
     * Thread için WebDriver oluştur
     * @return WebDriver
     */
    private static WebDriver createThreadDriver() {
        try {
            // Basit WebDriver oluştur (gerçek implementasyonda BrowserManager kullanılabilir)
            return null; // Bu kısım gerçek implementasyonda doldurulacak
        } catch (Exception e) {
            logger.error("Thread WebDriver oluşturma hatası: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Yük test sonuçlarını analiz et
     * @param results Performans sonuçları
     * @param concurrentUsers Eş zamanlı kullanıcı sayısı
     * @param duration Test süresi
     * @return Yük test sonucu
     */
    private static LoadTestResult analyzeLoadTestResults(List<PerformanceResult> results, 
                                                        int concurrentUsers, int duration) {
        if (results.isEmpty()) {
            return new LoadTestResult(0, 0, 0, 0, 0, false);
        }
        
        // İstatistikleri hesapla
        long totalRequests = results.size();
        long successfulRequests = results.stream().filter(PerformanceResult::isPassed).count();
        long failedRequests = totalRequests - successfulRequests;
        
        double avgResponseTime = results.stream()
                .mapToLong(PerformanceResult::getPageLoadTime)
                .average()
                .orElse(0.0);
        
        double maxResponseTime = results.stream()
                .mapToLong(PerformanceResult::getPageLoadTime)
                .max()
                .orElse(0);
        
        double minResponseTime = results.stream()
                .mapToLong(PerformanceResult::getPageLoadTime)
                .min()
                .orElse(0);
        
        double successRate = (double) successfulRequests / totalRequests;
        boolean isPassed = successRate >= 0.95 && avgResponseTime <= LOAD_TIME_THRESHOLD * 1000;
        
        LoadTestResult loadTestResult = new LoadTestResult(
                totalRequests,
                successfulRequests,
                failedRequests,
                avgResponseTime,
                successRate,
                isPassed
        );
        
        logger.info("Yük testi tamamlandı: Başarı oranı={:.2f}%, Ortalama süre={:.2f}ms", 
                successRate * 100, avgResponseTime);
        
        return loadTestResult;
    }
    
    /**
     * Performans raporu oluştur
     * @param results Performans sonuçları
     * @return Rapor metni
     */
    public static String generatePerformanceReport(List<PerformanceResult> results) {
        if (results.isEmpty()) {
            return "Performans raporu: Veri yok";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("=== PERFORMANS RAPORU ===\n");
        report.append(String.format("Toplam test: %d\n", results.size()));
        
        long passedTests = results.stream().filter(PerformanceResult::isPassed).count();
        report.append(String.format("Başarılı test: %d\n", passedTests));
        report.append(String.format("Başarı oranı: %.2f%%\n", (double) passedTests / results.size() * 100));
        
        double avgLoadTime = results.stream()
                .mapToLong(PerformanceResult::getPageLoadTime)
                .average()
                .orElse(0.0);
        report.append(String.format("Ortalama yükleme süresi: %.2fms\n", avgLoadTime));
        
        double maxLoadTime = results.stream()
                .mapToLong(PerformanceResult::getPageLoadTime)
                .max()
                .orElse(0);
        report.append(String.format("Maksimum yükleme süresi: %.2fms\n", maxLoadTime));
        
        double minLoadTime = results.stream()
                .mapToLong(PerformanceResult::getPageLoadTime)
                .min()
                .orElse(0);
        report.append(String.format("Minimum yükleme süresi: %.2fms\n", minLoadTime));
        
        return report.toString();
    }
}
