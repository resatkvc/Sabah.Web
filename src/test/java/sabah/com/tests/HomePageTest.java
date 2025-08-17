package sabah.com.tests;

import com.microsoft.playwright.Page;
import org.testng.annotations.Test;
import org.testng.Assert;
import sabah.com.base.BaseTest;
import sabah.com.pages.HomePage;
import io.qameta.allure.*;

/**
 * Sabah.com Ana Sayfa Test Senaryoları
 */
@Epic("Sabah.com Web Sitesi Testleri")
@Feature("Ana Sayfa Testleri")
public class HomePageTest extends BaseTest {
    
    /**
     * Ana sayfanın başarıyla yüklendiğini kontrol et
     */
    @Test(priority = 1, groups = {"smoke", "regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Ana Sayfa Yükleme")
    @Description("Ana sayfanın tüm temel elementleri ile birlikte başarıyla yüklendiğini doğrula")
    public void testHomePageLoading() {
        logger.info("Ana sayfa yükleme testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        // Ana sayfanın yüklendiğini doğrula
        Assert.assertTrue(homePage.isPageLoaded(), 
            "Ana sayfa başarıyla yüklenemedi!");
        
        // Sayfa başlığını kontrol et
        Assert.assertTrue(homePage.verifyPageTitle("SABAH"), 
            "Sayfa başlığı beklenen değeri içermiyor!");
        
        // Ekran görüntüsü al
        takeScreenshot("Ana Sayfa Yüklendi");
    }
    
    /**
     * Header elementlerinin görünür olduğunu kontrol et
     */
    @Test(priority = 2, groups = {"smoke", "regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Header Kontrolleri")
    @Description("Header'daki tüm önemli elementlerin görünür ve erişilebilir olduğunu doğrula")
    public void testHeaderElements() {
        logger.info("Header elementleri testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        // Header'ın tamamen yüklendiğini kontrol et
        Assert.assertTrue(homePage.getHeader().isHeaderFullyLoaded(),
            "Header tam olarak yüklenemedi!");
        
        // Üst header görünür mü?
        Assert.assertTrue(homePage.getHeader().isTopHeaderVisible(),
            "Üst header görünür değil!");
        
        // Logo'ya tıklayarak ana sayfaya dönüş testi
        homePage.getHeader().clickLogo();
        Assert.assertTrue(homePage.verifyPageTitle("SABAH"),
            "Logo'ya tıklandığında ana sayfaya dönülemedi!");
    }
    
    /**
     * Hamburger menü açılıp kapanma testi
     */
    @Test(priority = 3, groups = {"smoke", "regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Hamburger Menü İşlemleri")
    @Description("Hamburger menünün açılıp kapandığını ve temel işlevlerini kontrol et")
    public void testHamburgerMenuToggle() {
        logger.info("Hamburger menü toggle testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        // Başlangıçta menü kapalı olmalı
        Assert.assertFalse(homePage.getHeader().isHamburgerMenuOpen(),
            "Hamburger menü başlangıçta açık!");
        
        // Menüyü aç
        homePage.getHeader().openHamburgerMenu();
        Assert.assertTrue(homePage.getHeader().isHamburgerMenuOpen(),
            "Hamburger menü açılamadı!");
        
        takeScreenshot("Hamburger Menü Açık");
        
        // Menüyü kapat
        homePage.getHeader().closeHamburgerMenu();
        Assert.assertFalse(homePage.getHeader().isHamburgerMenuOpen(),
            "Hamburger menü kapatılamadı!");
    }
    
    /**
     * Hamburger menü ESC tuşu ile kapanma testi
     */
    @Test(priority = 4, groups = {"regression"})
    @Severity(SeverityLevel.MINOR)
    @Story("Hamburger Menü İşlemleri")
    @Description("Hamburger menünün ESC tuşu ile kapandığını kontrol et")
    public void testHamburgerMenuCloseWithEsc() {
        logger.info("Hamburger menü ESC tuşu testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        // Menüyü aç
        homePage.getHeader().openHamburgerMenu();
        Assert.assertTrue(homePage.getHeader().isHamburgerMenuOpen(),
            "Hamburger menü açılamadı!");
        
        // ESC ile kapat
        homePage.getHeader().closeHamburgerMenuWithEsc();
        Assert.assertFalse(homePage.getHeader().isHamburgerMenuOpen(),
            "Hamburger menü ESC tuşu ile kapatılamadı!");
    }
    
    /**
     * Ana menü navigasyon testi
     */
    @Test(priority = 5, groups = {"smoke", "regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Ana Menü Navigasyon")
    @Description("Ana menüden farklı kategorilere navigasyon yapılabildiğini kontrol et")
    public void testMainMenuNavigation() {
        logger.info("Ana menü navigasyon testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        // Gündem kategorisine git
        homePage.navigateToCategory("GÜNDEM");
        waitForSeconds(2);
        
        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("gundem"),
            "Gündem kategorisine gidilemedi! URL: " + currentUrl);
        
        takeScreenshot("Gündem Kategorisi");
        
        // Logo'ya tıklayarak ana sayfaya dön
        homePage.getHeader().clickLogo();
        
        // Ekonomi kategorisine git
        homePage.navigateToCategory("EKONOMİ");
        waitForSeconds(2);
        
        currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("ekonomi"),
            "Ekonomi kategorisine gidilemedi! URL: " + currentUrl);
    }
    
    /**
     * Borsa verileri görüntüleme testi
     */
    @Test(priority = 6, groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Borsa Verileri")
    @Description("Alt header'daki borsa verilerinin görüntülendiğini kontrol et")
    public void testBorsaDataDisplay() {
        logger.info("Borsa verileri testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        // Borsa verilerini kontrol et
        String bistDegeri = homePage.checkBorsaData("Bist");
        Assert.assertNotNull(bistDegeri, "Bist değeri alınamadı!");
        Assert.assertFalse(bistDegeri.isEmpty(), "Bist değeri boş!");
        logger.info("Bist değeri: {}", bistDegeri);
        
        String dolarDegeri = homePage.checkBorsaData("Dolar");
        Assert.assertNotNull(dolarDegeri, "Dolar değeri alınamadı!");
        Assert.assertFalse(dolarDegeri.isEmpty(), "Dolar değeri boş!");
        logger.info("Dolar değeri: {}", dolarDegeri);
        
        String euroDegeri = homePage.checkBorsaData("Euro");
        Assert.assertNotNull(euroDegeri, "Euro değeri alınamadı!");
        Assert.assertFalse(euroDegeri.isEmpty(), "Euro değeri boş!");
        logger.info("Euro değeri: {}", euroDegeri);
    }
    
    /**
     * Hava durumu bilgisi görüntüleme testi
     */
    @Test(priority = 7, groups = {"regression"})
    @Severity(SeverityLevel.MINOR)
    @Story("Hava Durumu")
    @Description("Hava durumu bilgisinin görüntülendiğini kontrol et")
    public void testWeatherInfo() {
        logger.info("Hava durumu bilgisi testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        String sicaklik = homePage.getWeatherInfo();
        Assert.assertNotNull(sicaklik, "Sıcaklık bilgisi alınamadı!");
        Assert.assertTrue(sicaklik.contains("°") || sicaklik.contains("C"),
            "Sıcaklık bilgisi geçerli bir formatta değil: " + sicaklik);
        
        logger.info("Hava durumu: {}", sicaklik);
    }
    
    /**
     * Hamburger menüden kategori seçimi testi
     */
    @Test(priority = 8, groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Hamburger Menü Navigasyon")
    @Description("Hamburger menüden kategori seçimi yapılabildiğini kontrol et")
    public void testHamburgerMenuCategorySelection() {
        logger.info("Hamburger menü kategori seçimi testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        // Spor kategorisini seç
        homePage.selectFromHamburgerMenu("Spor");
        waitForSeconds(2);
        
        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("spor"),
            "Spor kategorisine gidilemedi! URL: " + currentUrl);
        
        takeScreenshot("Spor Kategorisi");
    }
    
    /**
     * Hamburger menü hover ile alt menü açma testi
     */
    @Test(priority = 9, groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Hamburger Menü Alt Menü")
    @Description("Hamburger menüde hover yaparak alt menü açılabildiğini kontrol et")
    public void testHamburgerMenuHoverSubMenu() {
        logger.info("Hamburger menü hover testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        // Haber kategorisi üzerine hover yap
        homePage.getHeader().hoverOnHamburgerMenuItem("Haber");
        waitForSeconds(1);
        
        // Alt menüden "Gündem" seç
        homePage.getHeader().selectSubMenuItem("Haber", "Gündem");
        waitForSeconds(2);
        
        String currentUrl = page.url();
        Assert.assertTrue(currentUrl.contains("gundem"),
            "Gündem alt kategorisine gidilemedi! URL: " + currentUrl);
    }
    
    /**
     * Canlı yayın linklerine erişim testi
     */
    @Test(priority = 10, groups = {"regression"})
    @Severity(SeverityLevel.MINOR)
    @Story("Canlı Yayın Linkleri")
    @Description("Hamburger menüdeki canlı yayın linklerinin çalıştığını kontrol et")
    public void testLiveStreamLinks() {
        logger.info("Canlı yayın linkleri testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        // AHaber canlı yayın linkine tıkla
        homePage.getHeader().clickCanliYayinLink("AHaber");
        waitForSeconds(2);
        
        // Yeni sekme veya sayfa açıldığını kontrol et
        String currentUrl = page.url();
        logger.info("Canlı yayın URL: {}", currentUrl);
        
        // URL'de canlı yayın ile ilgili bir kelime olmalı
        Assert.assertTrue(
            currentUrl.contains("canli") || currentUrl.contains("live") || currentUrl.contains("ahaber"),
            "Canlı yayın sayfasına gidilemedi! URL: " + currentUrl
        );
    }
    
    /**
     * Son dakika bar'ı görünürlük testi
     */
    @Test(priority = 11, groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Son Dakika Haberleri")
    @Description("Son dakika haberler bar'ının görünür olduğunu kontrol et")
    public void testSonDakikaBar() {
        logger.info("Son dakika bar testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        boolean isSonDakikaVisible = homePage.isSonDakikaBarVisible();
        logger.info("Son dakika bar görünürlüğü: {}", isSonDakikaVisible);
        
        // Son dakika her zaman görünür olmayabilir, bu yüzden sadece log'luyoruz
        if (isSonDakikaVisible) {
            takeScreenshot("Son Dakika Bar");
        }
    }
    
    /**
     * Sosyal medya linkleri testi
     */
    @Test(priority = 12, groups = {"regression"})
    @Severity(SeverityLevel.MINOR)
    @Story("Sosyal Medya Linkleri")
    @Description("Sosyal medya linklerinin çalıştığını kontrol et")
    public void testSocialMediaLinks() {
        logger.info("Sosyal medya linkleri testi başlıyor...");
        
        HomePage homePage = new HomePage(page);
        
        // Facebook linkine tıkla
        homePage.goToSocialMedia("Facebook");
        waitForSeconds(2);
        
        // Yeni sekme açıldığını kontrol et
        int pageCount = page.context().pages().size();
        Assert.assertTrue(pageCount > 1,
            "Sosyal medya linki yeni sekmede açılmadı!");
        
        // Yeni sekmeye geç
        Page newPage = page.context().pages().get(pageCount - 1);
        String socialUrl = newPage.url();
        
        Assert.assertTrue(socialUrl.contains("facebook.com"),
            "Facebook sayfasına gidilemedi! URL: " + socialUrl);
        
        // Yeni sekmeyi kapat
        newPage.close();
    }
}
