package sabah.com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import sabah.com.base.BasePage;
import sabah.com.components.common.HeaderComponent;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Haber sayfası Page Object
 */
public class NewsPage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(NewsPage.class);
    private final HeaderComponent header;
    
    // Locators
    private final By haberBasliklari = By.cssSelector(".news-title, .article-title, h1, h2, h3");
    private final By haberIcerikleri = By.cssSelector(".news-content, .article-content, .content");
    private final By haberTarihleri = By.cssSelector(".news-date, .article-date, .date");
    private final By haberYazarlari = By.cssSelector(".news-author, .article-author, .author");
    private final By haberKategorileri = By.cssSelector(".news-category, .article-category, .category");
    private final By haberResimleri = By.cssSelector(".news-image, .article-image, img");
    private final By yorumBolumu = By.cssSelector(".comments-section, .comments");
    private final By yorumYazmaKutusu = By.cssSelector(".comment-input, textarea[name*='comment']");
    private final By yorumGonderButonu = By.cssSelector(".comment-submit, button[type='submit']");
    private final By paylasimButonlari = By.cssSelector(".share-buttons, .social-share");
    private final By ilgiliHaberler = By.cssSelector(".related-news, .related-articles");
    private final By sayfalama = By.cssSelector(".pagination, .pager");
    private final By sonrakiSayfa = By.cssSelector(".next-page, .pagination-next");
    private final By oncekiSayfa = By.cssSelector(".prev-page, .pagination-prev");
    
    /**
     * Constructor
     * @param driver WebDriver
     */
    public NewsPage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver);
        logger.info("NewsPage oluşturuldu");
    }
    
    /**
     * Header component'ini al
     * @return HeaderComponent
     */
    public HeaderComponent getHeader() {
        return header;
    }
    
    /**
     * Sayfa yüklendi mi kontrol et
     * @return Yüklendi mi?
     */
    @Step("Haber sayfası yükleme kontrolü")
    public boolean isPageLoaded() {
        try {
            // Temel elementlerin varlığını kontrol et
            if (!elementExists(haberBasliklari)) {
                logger.warn("Haber başlıkları bulunamadı");
                return false;
            }
            
            // Header'ın yüklendiğini kontrol et
            if (!header.isHeaderFullyLoaded()) {
                logger.warn("Header tam olarak yüklenemedi");
                return false;
            }
            
            logger.info("Haber sayfası başarıyla yüklendi");
            return true;
            
        } catch (Exception e) {
            logger.error("Haber sayfası yükleme kontrolü hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Haber başlıklarını al
     * @return Haber başlıkları listesi
     */
    @Step("Haber başlıkları alınıyor")
    public List<String> getNewsTitles() {
        try {
            List<WebElement> titleElements = driver.findElements(haberBasliklari);
            List<String> titles = titleElements.stream()
                    .map(WebElement::getText)
                    .filter(text -> !text.trim().isEmpty())
                    .toList();
            
            logger.info("{} adet haber başlığı bulundu", titles.size());
            return titles;
            
        } catch (Exception e) {
            logger.error("Haber başlıkları alma hatası: {}", e.getMessage());
            return List.of();
        }
    }
    
    /**
     * İlk habere tıkla
     * @return Başarılı mı?
     */
    @Step("İlk habere tıklanıyor")
    public boolean clickFirstNews() {
        try {
            List<WebElement> newsElements = driver.findElements(haberBasliklari);
            if (!newsElements.isEmpty()) {
                click(haberBasliklari);
                wait(2000); // Sayfa yükleme beklemesi
                logger.info("İlk habere tıklandı");
                return true;
            }
            
            logger.warn("Tıklanacak haber bulunamadı");
            return false;
            
        } catch (Exception e) {
            logger.error("İlk habere tıklama hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Belirli bir habere tıkla
     * @param newsTitle Haber başlığı
     * @return Başarılı mı?
     */
    @Step("'{0}' haberine tıklanıyor")
    public boolean clickNewsByTitle(String newsTitle) {
        try {
            List<WebElement> newsElements = driver.findElements(haberBasliklari);
            
            for (WebElement element : newsElements) {
                if (element.getText().contains(newsTitle)) {
                    element.click();
                    wait(2000);
                    logger.info("'{}' haberine tıklandı", newsTitle);
                    return true;
                }
            }
            
            logger.warn("'{}' başlıklı haber bulunamadı", newsTitle);
            return false;
            
        } catch (Exception e) {
            logger.error("Habere tıklama hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Haber içeriğini al
     * @return Haber içeriği
     */
    @Step("Haber içeriği alınıyor")
    public String getNewsContent() {
        try {
            if (elementExists(haberIcerikleri)) {
                String content = getText(haberIcerikleri);
                logger.info("Haber içeriği alındı ({} karakter)", content.length());
                return content;
            }
            
            logger.warn("Haber içeriği bulunamadı");
            return "";
            
        } catch (Exception e) {
            logger.error("Haber içeriği alma hatası: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Haber tarihini al
     * @return Haber tarihi
     */
    @Step("Haber tarihi alınıyor")
    public String getNewsDate() {
        try {
            if (elementExists(haberTarihleri)) {
                String date = getText(haberTarihleri);
                logger.info("Haber tarihi: {}", date);
                return date;
            }
            
            logger.warn("Haber tarihi bulunamadı");
            return "";
            
        } catch (Exception e) {
            logger.error("Haber tarihi alma hatası: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Haber yazarını al
     * @return Haber yazarı
     */
    @Step("Haber yazarı alınıyor")
    public String getNewsAuthor() {
        try {
            if (elementExists(haberYazarlari)) {
                String author = getText(haberYazarlari);
                logger.info("Haber yazarı: {}", author);
                return author;
            }
            
            logger.warn("Haber yazarı bulunamadı");
            return "";
            
        } catch (Exception e) {
            logger.error("Haber yazarı alma hatası: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Haber kategorisini al
     * @return Haber kategorisi
     */
    @Step("Haber kategorisi alınıyor")
    public String getNewsCategory() {
        try {
            if (elementExists(haberKategorileri)) {
                String category = getText(haberKategorileri);
                logger.info("Haber kategorisi: {}", category);
                return category;
            }
            
            logger.warn("Haber kategorisi bulunamadı");
            return "";
            
        } catch (Exception e) {
            logger.error("Haber kategorisi alma hatası: {}", e.getMessage());
            return "";
        }
    }
    
    /**
     * Haber resimlerini al
     * @return Resim sayısı
     */
    @Step("Haber resimleri alınıyor")
    public int getNewsImageCount() {
        try {
            int count = getElementCount(haberResimleri);
            logger.info("Haber resim sayısı: {}", count);
            return count;
            
        } catch (Exception e) {
            logger.error("Haber resim sayısı alma hatası: {}", e.getMessage());
            return 0;
        }
    }
    
    /**
     * Yorum yaz
     * @param comment Yorum metni
     * @return Başarılı mı?
     */
    @Step("'{0}' yorumu yazılıyor")
    public boolean writeComment(String comment) {
        try {
            if (elementExists(yorumYazmaKutusu)) {
                type(yorumYazmaKutusu, comment);
                wait(1000);
                
                if (elementExists(yorumGonderButonu)) {
                    click(yorumGonderButonu);
                    wait(2000);
                    logger.info("Yorum gönderildi: {}", comment);
                    return true;
                }
            }
            
            logger.warn("Yorum yazma alanı bulunamadı");
            return false;
            
        } catch (Exception e) {
            logger.error("Yorum yazma hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Sosyal medyada paylaş
     * @param platform Paylaşım platformu (facebook, twitter, etc.)
     * @return Başarılı mı?
     */
    @Step("'{0}' platformunda paylaşım yapılıyor")
    public boolean shareOnSocialMedia(String platform) {
        try {
            List<WebElement> shareButtons = driver.findElements(paylasimButonlari);
            
            for (WebElement button : shareButtons) {
                if (button.getText().toLowerCase().contains(platform.toLowerCase()) ||
                    button.getAttribute("class").toLowerCase().contains(platform.toLowerCase())) {
                    button.click();
                    wait(2000);
                    logger.info("'{}' platformunda paylaşım yapıldı", platform);
                    return true;
                }
            }
            
            logger.warn("'{}' paylaşım butonu bulunamadı", platform);
            return false;
            
        } catch (Exception e) {
            logger.error("Sosyal medya paylaşım hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * İlgili haberleri al
     * @return İlgili haber sayısı
     */
    @Step("İlgili haberler alınıyor")
    public int getRelatedNewsCount() {
        try {
            if (elementExists(ilgiliHaberler)) {
                int count = getElementCount(By.cssSelector(".related-news a, .related-articles a"));
                logger.info("İlgili haber sayısı: {}", count);
                return count;
            }
            
            logger.warn("İlgili haberler bölümü bulunamadı");
            return 0;
            
        } catch (Exception e) {
            logger.error("İlgili haber sayısı alma hatası: {}", e.getMessage());
            return 0;
        }
    }
    
    /**
     * Sonraki sayfaya git
     * @return Başarılı mı?
     */
    @Step("Sonraki sayfaya gidiliyor")
    public boolean goToNextPage() {
        try {
            if (elementExists(sonrakiSayfa) && isClickable(sonrakiSayfa)) {
                click(sonrakiSayfa);
                wait(2000);
                logger.info("Sonraki sayfaya gidildi");
                return true;
            }
            
            logger.warn("Sonraki sayfa butonu bulunamadı veya tıklanamaz");
            return false;
            
        } catch (Exception e) {
            logger.error("Sonraki sayfaya gitme hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Önceki sayfaya git
     * @return Başarılı mı?
     */
    @Step("Önceki sayfaya gidiliyor")
    public boolean goToPreviousPage() {
        try {
            if (elementExists(oncekiSayfa) && isClickable(oncekiSayfa)) {
                click(oncekiSayfa);
                wait(2000);
                logger.info("Önceki sayfaya gidildi");
                return true;
            }
            
            logger.warn("Önceki sayfa butonu bulunamadı veya tıklanamaz");
            return false;
            
        } catch (Exception e) {
            logger.error("Önceki sayfaya gitme hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Sayfa numarasına git
     * @param pageNumber Sayfa numarası
     * @return Başarılı mı?
     */
    @Step("'{0}' numaralı sayfaya gidiliyor")
    public boolean goToPage(int pageNumber) {
        try {
            By pageLocator = By.cssSelector(String.format(".pagination a[href*='page=%d'], .pager a[href*='page=%d']", pageNumber, pageNumber));
            
            if (elementExists(pageLocator)) {
                click(pageLocator);
                wait(2000);
                logger.info("{} numaralı sayfaya gidildi", pageNumber);
                return true;
            }
            
            logger.warn("{} numaralı sayfa bulunamadı", pageNumber);
            return false;
            
        } catch (Exception e) {
            logger.error("Sayfa numarasına gitme hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Toplam sayfa sayısını al
     * @return Sayfa sayısı
     */
    @Step("Toplam sayfa sayısı alınıyor")
    public int getTotalPageCount() {
        try {
            if (elementExists(sayfalama)) {
                List<WebElement> pageElements = driver.findElements(By.cssSelector(".pagination a, .pager a"));
                int count = pageElements.size();
                logger.info("Toplam sayfa sayısı: {}", count);
                return count;
            }
            
            logger.warn("Sayfalama bulunamadı");
            return 1;
            
        } catch (Exception e) {
            logger.error("Sayfa sayısı alma hatası: {}", e.getMessage());
            return 1;
        }
    }
    
    /**
     * Mevcut URL'i al
     * @return Mevcut URL
     */
    public String getCurrentUrl() {
        return super.getCurrentUrl();
    }
    
    /**
     * Klavye tuşuna basma
     * @param key Basılacak tuş
     */
    public void pressKey(org.openqa.selenium.Keys key) {
        super.pressKey(key);
    }
    
    /**
     * Bekle - milisaniye
     * @param milliseconds Bekleme süresi
     */
    public void wait(int milliseconds) {
        super.wait(milliseconds);
    }
}
