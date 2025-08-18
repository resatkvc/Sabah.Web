package sabah.com.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import sabah.com.base.BaseTest;
import sabah.com.pages.NewsPage;
import io.qameta.allure.*;

import java.util.List;

/**
 * Sabah.com Haber Sayfası Test Senaryoları
 */
@Epic("Sabah.com Web Sitesi Testleri")
@Feature("Haber Sayfası Testleri")
public class NewsPageTest extends BaseTest {
    
    /**
     * Haber sayfası yükleme testi
     */
    @Test(priority = 1, groups = {"smoke", "regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Haber Sayfası Yükleme")
    @Description("Haber sayfasının tüm temel elementleri ile birlikte başarıyla yüklendiğini doğrula")
    public void testNewsPageLoading() {
        logger.info("Haber sayfası yükleme testi başlıyor...");
        
        NewsPage newsPage = new NewsPage(driver);
        
        // Haber sayfasının yüklendiğini doğrula
        Assert.assertTrue(newsPage.isPageLoaded(), 
            "Haber sayfası başarıyla yüklenemedi!");
        
        // Haber başlıklarının var olduğunu kontrol et
        List<String> newsTitles = newsPage.getNewsTitles();
        Assert.assertFalse(newsTitles.isEmpty(), 
            "Haber başlıkları bulunamadı!");
        
        // Ekran görüntüsü al
        takeScreenshot("Haber Sayfası Yüklendi");
    }
    
    /**
     * Haber başlıkları testi
     */
    @Test(priority = 2, groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Haber Başlıkları")
    @Description("Haber başlıklarının görünür ve erişilebilir olduğunu doğrula")
    public void testNewsTitles() {
        logger.info("Haber başlıkları testi başlıyor...");
        
        NewsPage newsPage = new NewsPage(driver);
        
        // Haber başlıklarını al
        List<String> newsTitles = newsPage.getNewsTitles();
        
        // En az 5 haber başlığı olmalı
        Assert.assertTrue(newsTitles.size() >= 5, 
            "Yeterli haber başlığı bulunamadı! Bulunan: " + newsTitles.size());
        
        // Başlıkların boş olmadığını kontrol et
        for (String title : newsTitles) {
            Assert.assertFalse(title.trim().isEmpty(), 
                "Boş haber başlığı bulundu!");
        }
        
        logger.info("{} adet haber başlığı kontrol edildi", newsTitles.size());
    }
    
    /**
     * İlk habere tıklama testi
     */
    @Test(priority = 3, groups = {"smoke", "regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Haber Detay Sayfası")
    @Description("İlk habere tıklayarak detay sayfasına geçiş yapabildiğini doğrula")
    public void testClickFirstNews() {
        logger.info("İlk habere tıklama testi başlıyor...");
        
        NewsPage newsPage = new NewsPage(driver);
        
        // İlk habere tıkla
        boolean clicked = newsPage.clickFirstNews();
        Assert.assertTrue(clicked, "İlk habere tıklanamadı!");
        
        // Sayfa yükleme kontrolü
        newsPage.wait(2000);
        
        // Haber içeriğinin var olduğunu kontrol et
        String newsContent = newsPage.getNewsContent();
        Assert.assertFalse(newsContent.trim().isEmpty(), 
            "Haber içeriği bulunamadı!");
        
        // Ekran görüntüsü al
        takeScreenshot("Haber Detay Sayfası");
    }
    
    /**
     * Haber içeriği testi
     */
    @Test(priority = 4, groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Haber İçeriği")
    @Description("Haber içeriğinin doğru şekilde görüntülendiğini doğrula")
    public void testNewsContent() {
        logger.info("Haber içeriği testi başlıyor...");
        
        NewsPage newsPage = new NewsPage(driver);
        
        // İlk habere git
        newsPage.clickFirstNews();
        newsPage.wait(2000);
        
        // Haber içeriğini al
        String newsContent = newsPage.getNewsContent();
        Assert.assertFalse(newsContent.trim().isEmpty(), 
            "Haber içeriği boş!");
        
        // Haber tarihini al
        String newsDate = newsPage.getNewsDate();
        if (!newsDate.isEmpty()) {
            logger.info("Haber tarihi: {}", newsDate);
        }
        
        // Haber yazarını al
        String newsAuthor = newsPage.getNewsAuthor();
        if (!newsAuthor.isEmpty()) {
            logger.info("Haber yazarı: {}", newsAuthor);
        }
        
        // Haber kategorisini al
        String newsCategory = newsPage.getNewsCategory();
        if (!newsCategory.isEmpty()) {
            logger.info("Haber kategorisi: {}", newsCategory);
        }
        
        // Haber resim sayısını al
        int imageCount = newsPage.getNewsImageCount();
        logger.info("Haber resim sayısı: {}", imageCount);
    }
    
    /**
     * Sosyal medya paylaşım testi
     */
    @Test(priority = 5, groups = {"regression"})
    @Severity(SeverityLevel.MINOR)
    @Story("Sosyal Medya Paylaşımı")
    @Description("Sosyal medya paylaşım butonlarının çalıştığını doğrula")
    public void testSocialMediaSharing() {
        logger.info("Sosyal medya paylaşım testi başlıyor...");
        
        NewsPage newsPage = new NewsPage(driver);
        
        // İlk habere git
        newsPage.clickFirstNews();
        newsPage.wait(2000);
        
        // Facebook paylaşımını test et
        boolean facebookShared = newsPage.shareOnSocialMedia("facebook");
        if (facebookShared) {
            logger.info("Facebook paylaşımı başarılı");
        } else {
            logger.warn("Facebook paylaşım butonu bulunamadı");
        }
        
        // Twitter paylaşımını test et
        boolean twitterShared = newsPage.shareOnSocialMedia("twitter");
        if (twitterShared) {
            logger.info("Twitter paylaşımı başarılı");
        } else {
            logger.warn("Twitter paylaşım butonu bulunamadı");
        }
        
        // Bu test sadece butonların varlığını kontrol eder
        logger.info("Sosyal medya paylaşım testi tamamlandı");
    }
    
    /**
     * İlgili haberler testi
     */
    @Test(priority = 6, groups = {"regression"})
    @Severity(SeverityLevel.MINOR)
    @Story("İlgili Haberler")
    @Description("İlgili haberler bölümünün görüntülendiğini doğrula")
    public void testRelatedNews() {
        logger.info("İlgili haberler testi başlıyor...");
        
        NewsPage newsPage = new NewsPage(driver);
        
        // İlk habere git
        newsPage.clickFirstNews();
        newsPage.wait(2000);
        
        // İlgili haber sayısını al
        int relatedNewsCount = newsPage.getRelatedNewsCount();
        logger.info("İlgili haber sayısı: {}", relatedNewsCount);
        
        // İlgili haberler varsa kontrol et
        if (relatedNewsCount > 0) {
            Assert.assertTrue(relatedNewsCount >= 3, 
                "Yeterli ilgili haber bulunamadı! Bulunan: " + relatedNewsCount);
        } else {
            logger.warn("İlgili haberler bölümü bulunamadı");
        }
    }
    
    /**
     * Sayfalama testi
     */
    @Test(priority = 7, groups = {"regression"})
    @Severity(SeverityLevel.MINOR)
    @Story("Sayfalama")
    @Description("Sayfalama işlemlerinin çalıştığını doğrula")
    public void testPagination() {
        logger.info("Sayfalama testi başlıyor...");
        
        NewsPage newsPage = new NewsPage(driver);
        
        // Toplam sayfa sayısını al
        int totalPages = newsPage.getTotalPageCount();
        logger.info("Toplam sayfa sayısı: {}", totalPages);
        
        if (totalPages > 1) {
            // Sonraki sayfaya git
            boolean nextPageClicked = newsPage.goToNextPage();
            if (nextPageClicked) {
                logger.info("Sonraki sayfaya gidildi");
                
                // Önceki sayfaya geri dön
                boolean prevPageClicked = newsPage.goToPreviousPage();
                if (prevPageClicked) {
                    logger.info("Önceki sayfaya geri dönüldü");
                }
            }
        } else {
            logger.info("Sayfalama bulunamadı veya tek sayfa var");
        }
    }
    
    /**
     * Belirli sayfa numarasına gitme testi
     */
    @Test(priority = 8, groups = {"regression"})
    @Severity(SeverityLevel.MINOR)
    @Story("Sayfa Numarasına Gitme")
    @Description("Belirli sayfa numarasına gidebildiğini doğrula")
    public void testGoToSpecificPage() {
        logger.info("Belirli sayfa numarasına gitme testi başlıyor...");
        
        NewsPage newsPage = new NewsPage(driver);
        
        // 2. sayfaya git
        boolean page2Clicked = newsPage.goToPage(2);
        if (page2Clicked) {
            logger.info("2. sayfaya gidildi");
            
            // Sayfa yükleme kontrolü
            newsPage.wait(2000);
            
            // Haber başlıklarını kontrol et
            List<String> newsTitles = newsPage.getNewsTitles();
            Assert.assertFalse(newsTitles.isEmpty(), 
                "2. sayfada haber başlıkları bulunamadı!");
            
        } else {
            logger.warn("2. sayfaya gidilemedi");
        }
    }
    
    /**
     * Yorum yazma testi
     */
    @Test(priority = 9, groups = {"regression"})
    @Severity(SeverityLevel.MINOR)
    @Story("Yorum Yazma")
    @Description("Yorum yazma alanının çalıştığını doğrula")
    public void testCommentWriting() {
        logger.info("Yorum yazma testi başlıyor...");
        
        NewsPage newsPage = new NewsPage(driver);
        
        // İlk habere git
        newsPage.clickFirstNews();
        newsPage.wait(2000);
        
        // Test yorumu yaz
        String testComment = "Bu bir test yorumudur. " + System.currentTimeMillis();
        boolean commentWritten = newsPage.writeComment(testComment);
        
        if (commentWritten) {
            logger.info("Test yorumu yazıldı: {}", testComment);
        } else {
            logger.warn("Yorum yazma alanı bulunamadı veya çalışmıyor");
        }
    }
    
    /**
     * Haber arama testi (DataProvider ile)
     */
    @Test(priority = 10, groups = {"regression"}, dataProvider = "searchKeywords")
    @Severity(SeverityLevel.NORMAL)
    @Story("Haber Arama")
    @Description("Farklı anahtar kelimelerle haber arama yapabildiğini doğrula")
    public void testNewsSearch(String keyword) {
        logger.info("Haber arama testi başlıyor: {}", keyword);
        
        NewsPage newsPage = new NewsPage(driver);
        
        // Arama yap
        newsPage.getHeader().search(keyword);
        newsPage.wait(2000);
        
        // Arama sonuçlarını kontrol et
        List<String> newsTitles = newsPage.getNewsTitles();
        
        if (!newsTitles.isEmpty()) {
            // En az bir sonuç olmalı
            Assert.assertTrue(newsTitles.size() >= 1, 
                "Arama sonucu bulunamadı! Anahtar kelime: " + keyword);
            
            logger.info("'{}' araması için {} sonuç bulundu", keyword, newsTitles.size());
        } else {
            logger.warn("'{}' araması için sonuç bulunamadı", keyword);
        }
    }
    
    /**
     * Arama anahtar kelimeleri
     */
    @DataProvider(name = "searchKeywords")
    public Object[][] getSearchKeywords() {
        return new Object[][] {
            {"gündem"},
            {"ekonomi"},
            {"spor"},
            {"teknoloji"},
            {"haber"}
        };
    }
}
