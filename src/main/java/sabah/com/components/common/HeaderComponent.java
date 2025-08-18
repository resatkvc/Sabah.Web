package sabah.com.components.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import sabah.com.base.BasePage;
import io.qameta.allure.Step;
import java.util.List;
import java.util.ArrayList;

/**
 * Sabah.com Header Component
 * Tüm header elementlerini ve hamburger menü işlemlerini yönetir
 */
public class HeaderComponent extends BasePage {
    
    // ============= ÜST HEADER LOCATOR'LARI =============
    // Üst header container
    private final By topHeaderContainer = By.cssSelector("header, header.header, header[class*='header']");
    
    // Logo - Ana Sabah logosu
    private final By sabahLogo = By.cssSelector(".logo a[href='https://www.sabah.com.tr']");
    
    // Diğer siteler linkleri
    private final By sabahSporLink = By.cssSelector("a[href='/spor-haberleri']");
    private final By gunaydınLink = By.cssSelector("a[href='/magazin-haberleri']");
    private final By finansLink = By.cssSelector("a[href='/finans']");
    private final By rozaLink = By.cssSelector("a[href='/roza']");
    private final By memurlarLink = By.cssSelector("a[href='/memurlar']");
    private final By kobiLink = By.cssSelector("a[href='/kobi']");
    private final By videoLink = By.cssSelector("a[href='/video']");
    
    // Sosyal medya ikonları
    private final By socialMediaIcons = By.cssSelector(".mediaShare ul li a");
    private final By facebookIcon = By.cssSelector("a.face[href*='facebook.com']");
    private final By twitterIcon = By.cssSelector("a.twt[href*='x.com']");
    private final By instagramIcon = By.cssSelector("a.inst[href*='instagram.com']");
    private final By youtubeIcon = By.cssSelector("a.ytb[href*='youtube.com']");
    
    // Canlı yayın butonu
    private final By canliYayinButton = By.cssSelector(".menuDrop a");
    
    // ============= ORTA HEADER LOCATOR'LARI =============
    // Orta header container
    private final By middleHeaderContainer = By.cssSelector("nav.menu");
    
    // Ana menü
    private final By mainMenuContainer = By.cssSelector("nav.menu ul");
    private final By anaSayfaLink = By.cssSelector("a[href='https://www.sabah.com.tr']");
    private final By sonDakikaLink = By.cssSelector("a[href='/son-dakika-haberleri']");
    private final By gundemLink = By.cssSelector("a[href='/gundem']");
    private final By ekonomiLink = By.cssSelector("a[href='/ekonomi']");
    private final By yasamLink = By.cssSelector("a[href='/yasam']");
    private final By egitimLink = By.cssSelector("a[href='/egitim']");
    private final By dunyaLink = By.cssSelector("a[href='/dunya']");
    private final By resmiIlanlarLink = By.cssSelector("a[href='/resmi-ilan']");
    private final By yazarlarLink = By.cssSelector("a[href='/yazarlar']");
    
    // Sağ taraf elementleri
    private final By vavTvLink = By.cssSelector("a.vavtv[href*='vavtv']");
    private final By ziraatKupasiLink = By.cssSelector("a.icon.zk[href*='turkiye-kupasi']");
    private final By aramaButonu = By.cssSelector(".search i.fa-search");
    
    // ============= ALT HEADER LOCATOR'LARI =============
    // Alt header container
    private final By bottomHeaderContainer = By.cssSelector(".searchFrame");
    
    // Arama kutusu
    private final By aramaKutusu = By.id("txtHeaderSearch");
    private final By aramaButonuAlt = By.id("btnHeaderSearch");
    
    // Lokasyon seçici
    private final By lokasyonSecici = By.cssSelector(".location-selector, select.city-selector");
    private final By istanbulButonu = By.cssSelector("button, .location");
    
    // Canlı borsa ticker
    private final By borsaTicker = By.cssSelector(".stock-ticker, .market-data");
    private final By canliBaslik = By.cssSelector("span");
    private final By borsaBaslik = By.cssSelector("span");
    
    // Hava durumu
    private final By havaDurumuBilgisi = By.cssSelector(".weather-info");
    private final By sicaklik = By.cssSelector(".temperature, .weather-temp");
    
    // Namaz vakti
    private final By namazVaktiContainer = By.cssSelector(".prayer-time");
    private final By yatsiyaKalanSure = By.cssSelector(".prayer-countdown, .time-to-prayer");
    
    // Borsa verileri
    private final By bistVeri = By.cssSelector(".bist-data, div + span");
    private final By dolarVeri = By.cssSelector(".usd-data, div + span");
    private final By euroVeri = By.cssSelector(".eur-data, div + span");
    private final By altinVeri = By.cssSelector(".gold-data, div + span");
    private final By bitcoinVeri = By.cssSelector(".btc-data, div + span");
    
    // ============= HAMBURGER MENÜ LOCATOR'LARI =============
    // Hamburger menü toggle butonları
    private final By hamburgerMenuIcon = By.cssSelector("button.show-navobile");
    private final By closeMenuIcon = By.cssSelector(".close-menu, .menu-close, button[aria-label*='Kapat']");
    
    // Hamburger menü paneli
    private final By hamburgerMenuPanel = By.cssSelector(".mobile-menu, .navobile");
    
    // Hamburger menü kategorileri
    private final By hamburgerKategoriler = By.cssSelector(".mobile-menu ul li, .navobile ul li");
    
    /**
     * HeaderComponent constructor
     * @param driver WebDriver nesnesi
     */
    public HeaderComponent(WebDriver driver) {
        super(driver);
        logger.info("HeaderComponent oluşturuldu");
    }
    
    /**
     * Header'ın tamamen yüklendiğini kontrol et
     * @return Header yüklendi mi?
     */
    @Step("Header yükleme kontrolü")
    public boolean isHeaderFullyLoaded() {
        try {
            logger.debug("Header yükleme kontrolü başlıyor...");
            
            // Üst header kontrolü
            if (!elementExists(topHeaderContainer)) {
                logger.error("Üst header bulunamadı");
                return false;
            }
            
            // Logo kontrolü
            if (!elementExists(sabahLogo)) {
                logger.error("Sabah logosu bulunamadı");
                return false;
            }
            
            // Orta header kontrolü
            if (!elementExists(middleHeaderContainer)) {
                logger.error("Orta header bulunamadı");
                return false;
            }
            
            // Ana menü kontrolü
            if (!elementExists(mainMenuContainer)) {
                logger.error("Ana menü bulunamadı");
                return false;
            }
            
            logger.info("Header başarıyla yüklendi");
            return true;
            
        } catch (Exception e) {
            logger.error("Header yükleme kontrolü hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Üst header'ın görünür olup olmadığını kontrol et
     * @return Üst header görünür mü?
     */
    @Step("Üst header görünürlük kontrolü")
    public boolean isTopHeaderVisible() {
        try {
            return isVisible(topHeaderContainer);
        } catch (Exception e) {
            logger.error("Üst header görünürlük kontrolü hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Logo'ya tıkla
     */
    @Step("Logo'ya tıkla")
    public void clickLogo() {
        try {
            click(sabahLogo);
            logger.info("Logo'ya tıklandı");
        } catch (Exception e) {
            logger.error("Logo tıklama hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Hamburger menüyü aç
     */
    @Step("Hamburger menüyü aç")
    public void openHamburgerMenu() {
        try {
            if (!isHamburgerMenuOpen()) {
                click(hamburgerMenuIcon);
                waitForVisible(hamburgerMenuPanel);
                logger.info("Hamburger menü açıldı");
            } else {
                logger.info("Hamburger menü zaten açık");
            }
        } catch (Exception e) {
            logger.error("Hamburger menü açma hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Hamburger menüyü kapat
     */
    @Step("Hamburger menüyü kapat")
    public void closeHamburgerMenu() {
        try {
            if (isHamburgerMenuOpen()) {
                click(closeMenuIcon);
                waitForHidden(hamburgerMenuPanel);
                logger.info("Hamburger menü kapatıldı");
            } else {
                logger.info("Hamburger menü zaten kapalı");
            }
        } catch (Exception e) {
            logger.error("Hamburger menü kapatma hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Hamburger menünün açık olup olmadığını kontrol et
     * @return Hamburger menü açık mı?
     */
    @Step("Hamburger menü durumu kontrolü")
    public boolean isHamburgerMenuOpen() {
        try {
            return isVisible(hamburgerMenuPanel);
        } catch (Exception e) {
            logger.error("Hamburger menü durumu kontrolü hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Arama butonuna tıkla
     */
    @Step("Arama butonuna tıkla")
    public void clickSearchButton() {
        try {
            click(aramaButonu);
            logger.info("Arama butonuna tıklandı");
        } catch (Exception e) {
            logger.error("Arama butonu tıklama hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Arama kutusuna metin yaz
     * @param searchText Aranacak metin
     */
    @Step("Arama kutusuna '{0}' yaz")
    public void typeInSearchBox(String searchText) {
        try {
            type(aramaKutusu, searchText);
            logger.info("Arama kutusuna '{}' yazıldı", searchText);
        } catch (Exception e) {
            logger.error("Arama kutusu yazma hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Arama yap
     * @param searchText Aranacak metin
     */
    @Step("'{0}' için arama yap")
    public void search(String searchText) {
        try {
            clickSearchButton();
            wait(1000); // Arama kutusunun açılmasını bekle
            typeInSearchBox(searchText);
            pressKey(Keys.ENTER);
            logger.info("'{}' için arama yapıldı", searchText);
        } catch (Exception e) {
            logger.error("Arama hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Ana menüden kategori seç
     * @param categoryName Kategori adı
     */
    @Step("Ana menüden '{0}' kategorisini seç")
    public void clickMainMenuItem(String categoryName) {
        try {
            By categoryLocator = getCategoryLocator(categoryName);
            if (categoryLocator != null) {
                click(categoryLocator);
                logger.info("Ana menüden '{}' kategorisi seçildi", categoryName);
            } else {
                logger.error("'{}' kategorisi bulunamadı", categoryName);
            }
        } catch (Exception e) {
            logger.error("Ana menü kategori seçme hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Kategori adına göre locator döndür
     * @param categoryName Kategori adı
     * @return Kategori locator'ı
     */
    private By getCategoryLocator(String categoryName) {
        switch (categoryName.toLowerCase()) {
            case "ana sayfa":
            case "anasayfa":
                return anaSayfaLink;
            case "son dakika":
            case "sondakika":
                return sonDakikaLink;
            case "gündem":
            case "gundem":
                return gundemLink;
            case "ekonomi":
                return ekonomiLink;
            case "yaşam":
            case "yasam":
                return yasamLink;
            case "eğitim":
            case "egitim":
                return egitimLink;
            case "dünya":
            case "dunya":
                return dunyaLink;
            case "resmi ilanlar":
            case "resmiilanlar":
                return resmiIlanlarLink;
            case "yazarlar":
                return yazarlarLink;
            default:
                return null;
        }
    }
    
    /**
     * Sosyal medya ikonuna tıkla
     * @param platform Platform adı
     */
    @Step("'{0}' sosyal medya ikonuna tıkla")
    public void clickSocialMediaIcon(String platform) {
        try {
            By iconLocator = getSocialMediaLocator(platform);
            if (iconLocator != null) {
                click(iconLocator);
                logger.info("{} sosyal medya ikonuna tıklandı", platform);
            } else {
                logger.error("'{}' sosyal medya ikonu bulunamadı", platform);
            }
        } catch (Exception e) {
            logger.error("Sosyal medya ikonu tıklama hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Platform adına göre sosyal medya locator'ı döndür
     * @param platform Platform adı
     * @return Sosyal medya locator'ı
     */
    private By getSocialMediaLocator(String platform) {
        switch (platform.toLowerCase()) {
            case "facebook":
            case "fb":
                return facebookIcon;
            case "twitter":
            case "x":
                return twitterIcon;
            case "instagram":
            case "ig":
                return instagramIcon;
            case "youtube":
            case "yt":
                return youtubeIcon;
            default:
                return null;
        }
    }
    
    /**
     * Canlı yayın butonuna tıkla
     */
    @Step("Canlı yayın butonuna tıkla")
    public void clickCanliYayin() {
        try {
            click(canliYayinButton);
            logger.info("Canlı yayın butonuna tıklandı");
        } catch (Exception e) {
            logger.error("Canlı yayın butonu tıklama hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Borsa verisini al
     * @param borsaType Borsa tipi
     * @return Borsa değeri
     */
    @Step("'{0}' borsa verisini al")
    public String getBorsaData(String borsaType) {
        try {
            By borsaLocator = getBorsaLocator(borsaType);
            if (borsaLocator != null) {
                String value = getText(borsaLocator);
                logger.info("{} borsa verisi: {}", borsaType, value);
                return value;
            } else {
                logger.error("'{}' borsa verisi bulunamadı", borsaType);
                return null;
            }
        } catch (Exception e) {
            logger.error("Borsa verisi alma hatası: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Borsa tipine göre locator döndür
     * @param borsaType Borsa tipi
     * @return Borsa locator'ı
     */
    private By getBorsaLocator(String borsaType) {
        switch (borsaType.toLowerCase()) {
            case "bist":
                return bistVeri;
            case "dolar":
            case "usd":
                return dolarVeri;
            case "euro":
            case "eur":
                return euroVeri;
            case "altın":
            case "altin":
            case "gold":
                return altinVeri;
            case "bitcoin":
            case "btc":
                return bitcoinVeri;
            default:
                return null;
        }
    }
    
    /**
     * Mevcut sıcaklık bilgisini al
     * @return Sıcaklık değeri
     */
    @Step("Sıcaklık bilgisini al")
    public String getCurrentTemperature() {
        try {
            String temperature = getText(sicaklik);
            logger.info("Mevcut sıcaklık: {}", temperature);
            return temperature;
        } catch (Exception e) {
            logger.error("Sıcaklık bilgisi alma hatası: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Hamburger menüden kategori seç
     * @param categoryName Kategori adı
     */
    @Step("Hamburger menüden '{0}' kategorisini seç")
    public void selectFromHamburgerMenu(String categoryName) {
        try {
            openHamburgerMenu();
            wait(1000); // Menünün açılmasını bekle
            
            // Kategori elementini bul ve tıkla
            List<WebElement> categories = driver.findElements(hamburgerKategoriler);
            for (WebElement category : categories) {
                if (category.getText().toLowerCase().contains(categoryName.toLowerCase())) {
                    category.click();
                    logger.info("Hamburger menüden '{}' kategorisi seçildi", categoryName);
                    return;
                }
            }
            
            logger.error("Hamburger menüde '{}' kategorisi bulunamadı", categoryName);
            
        } catch (Exception e) {
            logger.error("Hamburger menü kategori seçme hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Klavye tuşuna basma
     * @param key Basılacak tuş
     */
    public void pressKey(Keys key) {
        super.pressKey(key);
    }
}
