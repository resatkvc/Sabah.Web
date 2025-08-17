package sabah.com.pages;

import com.microsoft.playwright.Page;
import sabah.com.base.BasePage;
import sabah.com.components.common.HeaderComponent;
import io.qameta.allure.Step;

/**
 * Sabah.com Ana Sayfa Page Object
 * Ana sayfaya özel elementler ve işlemler
 */
public class HomePage extends BasePage {
    
    // Header component
    private final HeaderComponent header;
    
    // Ana sayfa özel locator'ları
    private final String mansetHaber = ".headline-news, .main-headline";
    private final String ustMansetler = ".top-headlines, .featured-news";
    private final String haberGrid = ".news-grid, .news-list";
    private final String sonDakikaBar = ".breaking-news-bar, .flash-news";
    private final String videoGaleri = ".video-gallery, .video-section";
    private final String fotogaleri = ".photo-gallery, .gallery-section";
    
    /**
     * HomePage constructor
     * @param page Playwright page nesnesi
     */
    public HomePage(Page page) {
        super(page);
        this.header = new HeaderComponent(page);
        logger.info("HomePage oluşturuldu");
    }
    
    /**
     * Header component'i getir
     * @return HeaderComponent nesnesi
     */
    public HeaderComponent getHeader() {
        return header;
    }
    
    /**
     * Ana sayfanın yüklendiğini doğrula
     * @return Sayfa yüklendi mi?
     */
    @Step("Ana sayfa yükleme kontrolü")
    public boolean isPageLoaded() {
        try {
            // Header yüklendi mi?
            if (!header.isHeaderFullyLoaded()) {
                logger.error("Header yüklenemedi");
                return false;
            }
            
            // Manşet haber görünür mü?
            waitForVisible(page.locator(mansetHaber), SHORT_TIMEOUT);
            
            // Haber grid'i yüklendi mi?
            waitForVisible(page.locator(haberGrid), SHORT_TIMEOUT);
            
            logger.info("Ana sayfa başarıyla yüklendi");
            return true;
            
        } catch (Exception e) {
            logger.error("Ana sayfa yükleme hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Manşet habere tıkla
     */
    @Step("Manşet habere tıkla")
    public void clickMansetHaber() {
        click(page.locator(mansetHaber).first());
        logger.info("Manşet habere tıklandı");
    }
    
    /**
     * Son dakika haberlerinin görünür olup olmadığını kontrol et
     * @return Son dakika bar'ı görünür mü?
     */
    @Step("Son dakika bar kontrolü")
    public boolean isSonDakikaBarVisible() {
        boolean isVisible = isVisible(page.locator(sonDakikaBar));
        logger.info("Son dakika bar durumu: {}", isVisible ? "Görünür" : "Görünmez");
        return isVisible;
    }
    
    /**
     * Video galeriye git
     */
    @Step("Video galeriye git")
    public void goToVideoGallery() {
        scrollToElement(page.locator(videoGaleri));
        click(page.locator(videoGaleri));
        logger.info("Video galeriye gidildi");
    }
    
    /**
     * Foto galeriye git
     */
    @Step("Foto galeriye git")
    public void goToPhotoGallery() {
        scrollToElement(page.locator(fotogaleri));
        click(page.locator(fotogaleri));
        logger.info("Foto galeriye gidildi");
    }
    
    /**
     * Sayfa başlığını kontrol et
     * @param expectedTitle Beklenen başlık
     * @return Başlık doğru mu?
     */
    @Step("Sayfa başlığı kontrolü: '{0}'")
    public boolean verifyPageTitle(String expectedTitle) {
        String actualTitle = page.title();
        boolean isCorrect = actualTitle.contains(expectedTitle);
        
        if (isCorrect) {
            logger.info("Sayfa başlığı doğru: {}", actualTitle);
        } else {
            logger.error("Sayfa başlığı yanlış. Beklenen: '{}', Bulunan: '{}'", expectedTitle, actualTitle);
        }
        
        return isCorrect;
    }
    
    /**
     * Ana sayfada arama yap
     * @param searchText Aranacak metin
     */
    @Step("'{0}' metni için arama yap")
    public void search(String searchText) {
        header.clickSearchButton();
        // Arama input'unun açılmasını bekle
        wait(500);
        
        // Arama kutusuna yaz ve Enter'a bas
        page.keyboard().type(searchText);
        page.keyboard().press("Enter");
        
        logger.info("'{}' için arama yapıldı", searchText);
    }
    
    /**
     * Belirli bir kategoriye git (header üzerinden)
     * @param kategori Kategori adı
     */
    @Step("'{0}' kategorisine git")
    public void navigateToCategory(String kategori) {
        header.clickMainMenuItem(kategori);
        logger.info("{} kategorisine gidildi", kategori);
    }
    
    /**
     * Hamburger menüden kategori seç
     * @param kategori Kategori adı
     */
    @Step("Hamburger menüden '{0}' kategorisini seç")
    public void selectFromHamburgerMenu(String kategori) {
        header.selectFromHamburgerMenu(kategori);
        logger.info("Hamburger menüden {} seçildi", kategori);
    }
    
    /**
     * Üst haber listesindeki haber sayısını al
     * @return Haber sayısı
     */
    @Step("Üst manşet haber sayısını al")
    public int getTopNewsCount() {
        int count = getElementCount(page.locator(ustMansetler + " article"));
        logger.info("Üst manşetlerde {} haber bulundu", count);
        return count;
    }
    
    /**
     * Belirli bir sosyal medya platformuna git
     * @param platform Platform adı
     */
    @Step("'{0}' sosyal medya sayfasına git")
    public void goToSocialMedia(String platform) {
        header.clickSocialMediaIcon(platform);
        logger.info("{} sosyal medya sayfasına gidildi", platform);
    }
    
    /**
     * Canlı yayın sayfasına git
     */
    @Step("Canlı yayın sayfasına git")
    public void goToLiveStream() {
        header.clickCanliYayin();
        logger.info("Canlı yayın sayfasına gidildi");
    }
    
    /**
     * Borsa verilerini kontrol et
     * @param borsaTipi Borsa tipi (Bist, Dolar, Euro vb.)
     * @return Borsa değeri
     */
    @Step("'{0}' borsa verisini kontrol et")
    public String checkBorsaData(String borsaTipi) {
        return header.getBorsaData(borsaTipi);
    }
    
    /**
     * Hava durumu bilgisini al
     * @return Sıcaklık değeri
     */
    @Step("Hava durumu bilgisini al")
    public String getWeatherInfo() {
        return header.getCurrentTemperature();
    }
}
