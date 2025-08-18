package sabah.com.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.Assert;
import sabah.com.base.BaseTest;
import sabah.com.performance.PerformanceManager;
import sabah.com.performance.PerformanceResult;
import sabah.com.performance.LoadTestResult;
import sabah.com.data.TestDataManager;
import io.qameta.allure.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Sabah.com Performans Test Senaryoları
 */
@Epic("Sabah.com Web Sitesi Testleri")
@Feature("Performans Testleri")
public class PerformanceTest extends BaseTest {
    
    /**
     * Ana sayfa yükleme performans testi
     */
    @Test(priority = 1, groups = {"performance", "regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Ana Sayfa Performans")
    @Description("Ana sayfanın performans kriterlerini karşıladığını doğrula")
    public void testHomePagePerformance() {
        logger.info("Ana sayfa performans testi başlıyor...");
        
        String baseUrl = TestDataManager.getTestData("url.home");
        PerformanceResult result = PerformanceManager.measurePageLoadTime(driver, baseUrl);
        
        // Performans kriterlerini kontrol et
        Assert.assertTrue(result.isPassed(), 
            "Ana sayfa yükleme süresi kriterleri karşılanmadı! Süre: " + result.getPageLoadTimeSeconds() + "s");
        
        // DOM Ready süresini kontrol et
        Assert.assertTrue(result.getDomReadyTimeSeconds() <= 3.0, 
            "DOM Ready süresi çok uzun! Süre: " + result.getDomReadyTimeSeconds() + "s");
        
        logger.info("Ana sayfa performans testi tamamlandı - Yükleme: {}s, DOM Ready: {}s", 
                result.getPageLoadTimeSeconds(), result.getDomReadyTimeSeconds());
    }
    
    /**
     * Kategori sayfaları performans testi
     */
    @Test(priority = 2, groups = {"performance", "regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Kategori Sayfaları Performans")
    @Description("Kategori sayfalarının performans kriterlerini karşıladığını doğrula")
    public void testCategoryPagesPerformance() {
        logger.info("Kategori sayfaları performans testi başlıyor...");
        
        List<String> categoryUrls = new ArrayList<>();
        categoryUrls.add(TestDataManager.getTestData("url.gundem"));
        categoryUrls.add(TestDataManager.getTestData("url.ekonomi"));
        categoryUrls.add(TestDataManager.getTestData("url.spor"));
        
        List<PerformanceResult> results = new ArrayList<>();
        
        for (String url : categoryUrls) {
            if (!url.isEmpty()) {
                PerformanceResult result = PerformanceManager.measurePageLoadTime(driver, url);
                results.add(result);
                
                logger.info("{} - Yükleme: {}s, DOM Ready: {}s, Başarılı: {}", 
                        url, result.getPageLoadTimeSeconds(), result.getDomReadyTimeSeconds(), result.isPassed());
            }
        }
        
        // Tüm sayfaların performans kriterlerini karşıladığını kontrol et
        long passedCount = results.stream().filter(PerformanceResult::isPassed).count();
        Assert.assertTrue(passedCount >= results.size() * 0.8, 
            "Çok fazla sayfa performans kriterlerini karşılamıyor! Başarılı: " + passedCount + "/" + results.size());
        
        logger.info("Kategori sayfaları performans testi tamamlandı - Başarılı: {}/{}", passedCount, results.size());
    }
    
    /**
     * Arama sayfası performans testi
     */
    @Test(priority = 3, groups = {"performance", "regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Arama Sayfası Performans")
    @Description("Arama sayfasının performans kriterlerini karşıladığını doğrula")
    public void testSearchPagePerformance() {
        logger.info("Arama sayfası performans testi başlıyor...");
        
        // Arama yap
        String searchKeyword = TestDataManager.getTestData("search.valid");
        String searchUrl = TestDataManager.getTestData("url.home") + "search?q=" + searchKeyword;
        
        PerformanceResult result = PerformanceManager.measurePageLoadTime(driver, searchUrl);
        
        // Performans kriterlerini kontrol et
        Assert.assertTrue(result.isPassed(), 
            "Arama sayfası yükleme süresi kriterleri karşılanmadı! Süre: " + result.getPageLoadTimeSeconds() + "s");
        
        logger.info("Arama sayfası performans testi tamamlandı - Yükleme: {}s", result.getPageLoadTimeSeconds());
    }
    
    /**
     * Haber detay sayfası performans testi
     */
    @Test(priority = 4, groups = {"performance", "regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Haber Detay Sayfası Performans")
    @Description("Haber detay sayfasının performans kriterlerini karşıladığını doğrula")
    public void testNewsDetailPagePerformance() {
        logger.info("Haber detay sayfası performans testi başlıyor...");
        
        // Önce ana sayfaya git
        String baseUrl = TestDataManager.getTestData("url.home");
        driver.get(baseUrl);
        
        // İlk habere tıkla (basit simülasyon)
        String newsUrl = baseUrl + "haber/detay";
        PerformanceResult result = PerformanceManager.measurePageLoadTime(driver, newsUrl);
        
        // Performans kriterlerini kontrol et
        Assert.assertTrue(result.isPassed(), 
            "Haber detay sayfası yükleme süresi kriterleri karşılanmadı! Süre: " + result.getPageLoadTimeSeconds() + "s");
        
        logger.info("Haber detay sayfası performans testi tamamlandı - Yükleme: {}s", result.getPageLoadTimeSeconds());
    }
    
    /**
     * Basit yük testi
     */
    @Test(priority = 5, groups = {"performance", "load"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Yük Testi")
    @Description("Sitenin belirli bir yük altında çalışabildiğini doğrula")
    public void testLoadTest() {
        logger.info("Yük testi başlıyor...");
        
        String baseUrl = TestDataManager.getTestData("url.home");
        int concurrentUsers = 5; // Düşük yük
        int duration = 30; // 30 saniye
        
        LoadTestResult result = PerformanceManager.performLoadTest(baseUrl, concurrentUsers, duration);
        
        // Yük test kriterlerini kontrol et
        Assert.assertTrue(result.isPassed(), 
            "Yük testi başarısız! Başarı oranı: " + result.getSuccessRatePercentage() + "%, Ortalama süre: " + result.getAvgResponseTimeSeconds() + "s");
        
        // Başarı oranı en az %95 olmalı
        Assert.assertTrue(result.getSuccessRate() >= 0.95, 
            "Başarı oranı çok düşük! Oran: " + result.getSuccessRatePercentage() + "%");
        
        // Ortalama yanıt süresi 5 saniyeden az olmalı
        Assert.assertTrue(result.getAvgResponseTimeSeconds() <= 5.0, 
            "Ortalama yanıt süresi çok uzun! Süre: " + result.getAvgResponseTimeSeconds() + "s");
        
        logger.info("Yük testi tamamlandı - Başarı oranı: {}%, Ortalama süre: {}s, Toplam istek: {}", 
                result.getSuccessRatePercentage(), result.getAvgResponseTimeSeconds(), result.getTotalRequests());
    }
    
    /**
     * Performans raporu oluşturma testi
     */
    @Test(priority = 6, groups = {"performance", "reporting"})
    @Severity(SeverityLevel.MINOR)
    @Story("Performans Raporu")
    @Description("Performans raporunun oluşturulabildiğini doğrula")
    public void testPerformanceReporting() {
        logger.info("Performans raporu testi başlıyor...");
        
        // Test verileri oluştur
        List<PerformanceResult> results = new ArrayList<>();
        String baseUrl = TestDataManager.getTestData("url.home");
        
        // Birkaç test sonucu ekle
        for (int i = 0; i < 3; i++) {
            PerformanceResult result = PerformanceManager.measurePageLoadTime(driver, baseUrl);
            results.add(result);
        }
        
        // Rapor oluştur
        String report = PerformanceManager.generatePerformanceReport(results);
        
        // Raporun boş olmadığını kontrol et
        Assert.assertFalse(report.isEmpty(), "Performans raporu boş!");
        Assert.assertTrue(report.contains("PERFORMANS RAPORU"), "Rapor başlığı bulunamadı!");
        
        logger.info("Performans raporu oluşturuldu:\n{}", report);
    }
    
    /**
     * JavaScript metrikleri testi
     */
    @Test(priority = 7, groups = {"performance", "regression"})
    @Severity(SeverityLevel.MINOR)
    @Story("JavaScript Metrikleri")
    @Description("JavaScript performans metriklerinin alınabildiğini doğrula")
    public void testJavaScriptMetrics() {
        logger.info("JavaScript metrikleri testi başlıyor...");
        
        String baseUrl = TestDataManager.getTestData("url.home");
        PerformanceResult result = PerformanceManager.measurePageLoadTime(driver, baseUrl);
        
        // JavaScript metriklerinin var olduğunu kontrol et
        String jsMetrics = result.getJsMetrics();
        Assert.assertFalse(jsMetrics.isEmpty(), "JavaScript metrikleri alınamadı!");
        
        // Temel metriklerin var olduğunu kontrol et
        Assert.assertTrue(jsMetrics.contains("readyState") || jsMetrics.contains("title"), 
            "Temel JavaScript metrikleri bulunamadı!");
        
        logger.info("JavaScript metrikleri alındı: {}", jsMetrics);
    }
    
    /**
     * Farklı tarayıcı boyutlarında performans testi
     */
    @Test(priority = 8, groups = {"performance", "responsive"})
    @Severity(SeverityLevel.MINOR)
    @Story("Responsive Performans")
    @Description("Farklı ekran boyutlarında performansın korunduğunu doğrula")
    public void testResponsivePerformance() {
        logger.info("Responsive performans testi başlıyor...");
        
        String baseUrl = TestDataManager.getTestData("url.home");
        
        // Desktop boyutu
        driver.manage().window().maximize();
        PerformanceResult desktopResult = PerformanceManager.measurePageLoadTime(driver, baseUrl);
        
        // Tablet boyutu
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
        PerformanceResult tabletResult = PerformanceManager.measurePageLoadTime(driver, baseUrl);
        
        // Mobile boyutu
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
        PerformanceResult mobileResult = PerformanceManager.measurePageLoadTime(driver, baseUrl);
        
        // Tüm boyutlarda performans kriterlerini kontrol et
        Assert.assertTrue(desktopResult.isPassed(), "Desktop performans kriterleri karşılanmadı!");
        Assert.assertTrue(tabletResult.isPassed(), "Tablet performans kriterleri karşılanmadı!");
        Assert.assertTrue(mobileResult.isPassed(), "Mobile performans kriterleri karşılanmadı!");
        
        logger.info("Responsive performans testi tamamlandı - Desktop: {}s, Tablet: {}s, Mobile: {}s", 
                desktopResult.getPageLoadTimeSeconds(), tabletResult.getPageLoadTimeSeconds(), mobileResult.getPageLoadTimeSeconds());
    }
}
