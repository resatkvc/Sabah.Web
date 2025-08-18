package sabah.com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import sabah.com.base.BasePage;
import sabah.com.components.common.HeaderComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

/**
 * Sabah.com Ana Sayfa Page Object
 * Ana sayfaya özel elementler ve işlemler
 */
public class HomePage extends BasePage {
    
    // Header component
    private final HeaderComponent header;
    
    // Ana sayfa özel locator'ları
    private final By mansetHaber = By.cssSelector(".headline-news, .main-headline");
    private final By ustMansetler = By.cssSelector(".top-headlines, .featured-news");
    private final By haberGrid = By.cssSelector(".news-grid, .news-list");
    private final By sonDakikaBar = By.cssSelector(".breaking-news-bar, .flash-news");
    private final By videoGaleri = By.cssSelector(".video-gallery, .video-section");
    private final By fotogaleri = By.cssSelector(".photo-gallery, .gallery-section");
    
    /**
     * HomePage constructor
     * @param driver WebDriver nesnesi
     */
    public HomePage(WebDriver driver) {
        super(driver);
        this.header = new HeaderComponent(driver);
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
            logger.info("Ana sayfa yükleme kontrolü başlıyor...");
            
            // Kısa bir bekleme ekle
            logger.debug("Ek bekleme süresi: 3000ms");
            wait(3000);
            
            // Sayfa başlığını kontrol et
            String pageTitle = getPageTitle();
            logger.info("Sayfa başlığı: {}", pageTitle);
            
            if (pageTitle == null || pageTitle.trim().isEmpty()) {
                logger.error("Sayfa başlığı boş!");
                return false;
            }
            
            // URL kontrolü
            String currentUrl = getCurrentUrl();
            logger.info("Mevcut URL: {}", currentUrl);
            
            if (!currentUrl.contains("sabah.com.tr")) {
                logger.error("Yanlış URL'deyiz: {}", currentUrl);
                return false;
            }
            
            // Header yüklendi mi?
            logger.debug("Header yükleme kontrolü başlıyor...");
            if (!header.isHeaderFullyLoaded()) {
                logger.error("Header yüklenemedi");
                return false;
            }
            
            // Basit bir element kontrolü - herhangi bir haber linki var mı?
            try {
                logger.debug("Haber linkleri kontrol ediliyor...");
                int haberLinkCount = getElementCount(By.cssSelector("a[href*='/haber'], a[href*='/gundem'], a[href*='/ekonomi']"));
                logger.info("Bulunan haber link sayısı: {}", haberLinkCount);
                
                if (haberLinkCount == 0) {
                    logger.warn("Hiç haber linki bulunamadı, alternatif kontrol deneniyor...");
                    // Alternatif kontrol - herhangi bir link var mı?
                    int linkCount = getElementCount(By.tagName("a"));
                    logger.info("Toplam link sayısı: {}", linkCount);
                    
                    if (linkCount < 10) {
                        logger.error("Sayfada çok az link var, sayfa düzgün yüklenmemiş olabilir");
                        return false;
                    }
                }
                
                logger.info("Ana sayfa başarıyla yüklendi");
                return true;
                
            } catch (Exception e) {
                logger.error("Element kontrolü hatası: {}", e.getMessage());
                return false;
            }
            
        } catch (Exception e) {
            logger.error("Ana sayfa yükleme kontrolü hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Sayfa başlığını doğrula
     * @param expectedTitle Beklenen başlık
     * @return Başlık doğru mu?
     */
    @Step("Sayfa başlığı doğrulama: {0}")
    public boolean verifyPageTitle(String expectedTitle) {
        try {
            String actualTitle = getPageTitle();
            logger.info("Beklenen başlık: {}, Gerçek başlık: {}", expectedTitle, actualTitle);
            
            boolean result = actualTitle != null && actualTitle.contains(expectedTitle);
            logger.info("Başlık doğrulama sonucu: {}", result);
            
            return result;
            
        } catch (Exception e) {
            logger.error("Başlık doğrulama hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Manset haberlerini kontrol et
     * @return Manset haberleri var mı?
     */
    @Step("Manset haberleri kontrolü")
    public boolean hasMansetHaberler() {
        try {
            return elementExists(mansetHaber);
        } catch (Exception e) {
            logger.error("Manset haberleri kontrolü hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Üst mansetleri kontrol et
     * @return Üst mansetler var mı?
     */
    @Step("Üst mansetler kontrolü")
    public boolean hasUstMansetler() {
        try {
            return elementExists(ustMansetler);
        } catch (Exception e) {
            logger.error("Üst mansetler kontrolü hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Haber grid'ini kontrol et
     * @return Haber grid'i var mı?
     */
    @Step("Haber grid kontrolü")
    public boolean hasHaberGrid() {
        try {
            return elementExists(haberGrid);
        } catch (Exception e) {
            logger.error("Haber grid kontrolü hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Son dakika bar'ını kontrol et
     * @return Son dakika bar'ı var mı?
     */
    @Step("Son dakika bar kontrolü")
    public boolean hasSonDakikaBar() {
        try {
            return elementExists(sonDakikaBar);
        } catch (Exception e) {
            logger.error("Son dakika bar kontrolü hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Video galeri'yi kontrol et
     * @return Video galeri var mı?
     */
    @Step("Video galeri kontrolü")
    public boolean hasVideoGaleri() {
        try {
            return elementExists(videoGaleri);
        } catch (Exception e) {
            logger.error("Video galeri kontrolü hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Foto galeri'yi kontrol et
     * @return Foto galeri var mı?
     */
    @Step("Foto galeri kontrolü")
    public boolean hasFotogaleri() {
        try {
            return elementExists(fotogaleri);
        } catch (Exception e) {
            logger.error("Foto galeri kontrolü hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Sayfayı aşağı kaydır
     * @param pixels Kaydırılacak pixel miktarı
     */
    @Step("Sayfa {0} pixel aşağı kaydırılıyor")
    public void scrollDown(int pixels) {
        try {
            scrollByPixels(pixels);
            logger.info("Sayfa {} pixel aşağı kaydırıldı", pixels);
        } catch (Exception e) {
            logger.error("Sayfa kaydırma hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Sayfayı yukarı kaydır
     * @param pixels Kaydırılacak pixel miktarı
     */
    @Step("Sayfa {0} pixel yukarı kaydırılıyor")
    public void scrollUp(int pixels) {
        try {
            scrollByPixels(-pixels);
            logger.info("Sayfa {} pixel yukarı kaydırıldı", pixels);
        } catch (Exception e) {
            logger.error("Sayfa kaydırma hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Belirli bir elemente scroll yap
     * @param elementSelector Element selector'ı
     */
    @Step("'{0}' elementine scroll yapılıyor")
    public void scrollToElement(String elementSelector) {
        try {
            By locator = By.cssSelector(elementSelector);
            scrollToElement(locator);
            logger.info("'{}' elementine scroll yapıldı", elementSelector);
        } catch (Exception e) {
            logger.error("Elemente scroll yapma hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Sayfadaki toplam link sayısını al
     * @return Link sayısı
     */
    @Step("Sayfa link sayısı alınıyor")
    public int getTotalLinkCount() {
        try {
            int count = getElementCount(By.tagName("a"));
            logger.info("Sayfadaki toplam link sayısı: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Link sayısı alma hatası: {}", e.getMessage());
            return 0;
        }
    }
    
    /**
     * Sayfadaki toplam resim sayısını al
     * @return Resim sayısı
     */
    @Step("Sayfa resim sayısı alınıyor")
    public int getTotalImageCount() {
        try {
            int count = getElementCount(By.tagName("img"));
            logger.info("Sayfadaki toplam resim sayısı: {}", count);
            return count;
        } catch (Exception e) {
            logger.error("Resim sayısı alma hatası: {}", e.getMessage());
            return 0;
        }
    }
    
    /**
     * Sayfayı yenile
     */
    @Step("Ana sayfa yenileniyor")
    public void refreshPage() {
        try {
            super.refreshPage();
            logger.info("Ana sayfa yenilendi");
            
            // Yenileme sonrası kısa bekleme
            wait(2000);
            
        } catch (Exception e) {
            logger.error("Sayfa yenileme hatası: {}", e.getMessage());
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
    public void pressKey(Keys key) {
        super.pressKey(key);
    }
    
    /**
     * Belirli bir süre bekle
     * @param milliseconds Bekleme süresi (milisaniye)
     */
    public void wait(int milliseconds) {
        super.wait(milliseconds);
    }
}
