package sabah.com.components.common;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
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
    private final String topHeaderContainer = "header.header";
    
    // Logo
    private final String sabahLogo = ".logo a[href='https://www.sabah.com.tr'], .logo img[alt*='Son Dakika Haberleri']";
    
    // Diğer siteler linkleri
    private final String sabahSporLink = "a[href='/spor-haberleri']";
    private final String gunaydınLink = "a[href='/magazin-haberleri']";
    private final String finansLink = "a[href='/finans']";
    private final String rozaLink = "a[href='/roza']";
    private final String memurlarLink = "a[href='/memurlar']";
    private final String kobiLink = "a[href='/kobi']";
    private final String videoLink = "a[href='/video']";
    
    // Sosyal medya ikonları
    private final String socialMediaIcons = ".mediaShare ul li a";
    private final String facebookIcon = "a.face[href*='facebook.com']";
    private final String twitterIcon = "a.twt[href*='x.com']";
    private final String instagramIcon = "a.inst[href*='instagram.com']";
    private final String youtubeIcon = "a.ytb[href*='youtube.com']";
    
    // Canlı yayın butonu
    private final String canliYayinButton = ".menuDrop a:has-text('CANLI YAYIN')";
    
    // ============= ORTA HEADER LOCATOR'LARI =============
    // Orta header container
    private final String middleHeaderContainer = "nav.menu";
    
    // Ana menü
    private final String mainMenuContainer = "nav.menu ul";
    private final String anaSayfaLink = "a[href='https://www.sabah.com.tr']";
    private final String sonDakikaLink = "a[href='/son-dakika-haberleri']";
    private final String gundemLink = "a[href='/gundem']";
    private final String ekonomiLink = "a[href='/ekonomi']";
    private final String yasamLink = "a[href='/yasam']";
    private final String egitimLink = "a[href='/egitim']";
    private final String dunyaLink = "a[href='/dunya']";
    private final String resmiIlanlarLink = "a[href='/resmi-ilan']";
    private final String yazarlarLink = "a[href='/yazarlar']";
    
    // Sağ taraf elementleri
    private final String vavTvLink = "a.vavtv[href*='vavtv']";
    private final String ziraatKupasiLink = "a.icon.zk[href*='turkiye-kupasi']";
    private final String aramaButonu = ".search i.fa-search";
    
    // ============= ALT HEADER LOCATOR'LARI =============
    // Alt header container
    private final String bottomHeaderContainer = ".searchFrame";
    
    // Arama kutusu
    private final String aramaKutusu = "#txtHeaderSearch";
    private final String aramaButonuAlt = "#btnHeaderSearch";
    
    // Lokasyon seçici
    private final String lokasyonSecici = ".location-selector, select.city-selector";
    private final String istanbulButonu = "button:has-text('İSTANBUL'), .location:has-text('İSTANBUL')";
    
    // Canlı borsa ticker
    private final String borsaTicker = ".stock-ticker, .market-data";
    private final String canliBaslik = "span:has-text('CANLI')";
    private final String borsaBaslik = "span:has-text('BORSA')";
    
    // Hava durumu
    private final String havaDurumuBilgisi = ".weather-info";
    private final String sicaklik = ".temperature, .weather-temp";
    
    // Namaz vakti
    private final String namazVaktiContainer = ".prayer-time";
    private final String yatsiyaKalanSure = ".prayer-countdown, .time-to-prayer";
    
    // Borsa verileri
    private final String bistVeri = ".bist-data, div:has-text('Bist') + span";
    private final String dolarVeri = ".usd-data, div:has-text('Dolar') + span";
    private final String euroVeri = ".eur-data, div:has-text('Euro') + span";
    private final String altinVeri = ".gold-data, div:has-text('Altın') + span";
    private final String bitcoinVeri = ".btc-data, div:has-text('Bitcoin') + span";
    
    // ============= HAMBURGER MENÜ LOCATOR'LARI =============
    // Hamburger menü toggle butonları
    private final String hamburgerMenuIcon = "button.show-navobile";
    private final String closeMenuIcon = ".close-menu, .menu-close, button[aria-label*='Kapat']";
    
    // Hamburger menü paneli
    private final String hamburgerMenuPanel = "nav.menu";
    private final String menuOverlay = ".menu-overlay, .backdrop";
    
    // Ana menü öğeleri (desktop-none class'ı olanlar mobil menüde görünür)
    private final String menuItemHaber = "li.desktop-none a[href='/']";
    private final String menuItemSpor = "li.desktop-none a[href='/spor-haberleri']";
    private final String menuItemEkonomi = "li.desktop-none a[href='/ekonomi']";
    private final String menuItemResmiIlanlar = "li.desktop-none a[href='/resmi-ilan']";
    private final String menuItemFinans = "li.desktop-none a[href='/finans']";
    private final String menuItemApara = "li.desktop-none a[href='/apara']";
    private final String menuItemTarim = "li.desktop-none a[href='/tarim']";
    private final String menuItemEglence = "li.desktop-none .subOpen:has-text('Eğlence')";
    private final String menuItemDiger = "li.desktop-none .subOpen:has-text('Diğer')";
    private final String menuItemGaleri = "li.desktop-none .subOpen:has-text('Galeri')";
    private final String menuItemSabahTv = "li.desktop-none .subOpen:has-text('Sabah TV')";
    private final String menuItemVideoGaleri = "li.desktop-none .subOpen:has-text('Video Galeri')";
    private final String menuItemEkler = "li.desktop-none .subOpen:has-text('Ekler')";
    private final String menuItemSansOyunlari = "li.desktop-none .subOpen:has-text('Şans Oyunları')";
    private final String menuItemYazarlar = "li.desktop-none a[href='/yazarlar']";
    
    // Alt menüler (hover ile açılan)
    private final String subMenuContainer = ".subMenu";
    private final String eglenceSubMenu = ".subMenu ul li a[href='/oyun'], .subMenu ul li a[href='/bilmeceler'], .subMenu ul li a[href='/bulmaca-coz']";
    private final String digerSubMenu = ".subMenu ul li a[href='/teknokulis'], .subMenu ul li a[href='/kultur-sanat'], .subMenu ul li a[href='/medya']";
    private final String galeriSubMenu = ".subMenu ul li a[href*='/galeri/']";
    private final String sabahTvSubMenu = ".subMenu ul li a[href*='/video/']";
    private final String videoGaleriSubMenu = ".subMenu ul li a[href*='/video-galeri/']";
    private final String eklerSubMenu = ".subMenu ul li a[href='/cumartesi'], .subMenu ul li a[href='/pazar']";
    private final String sansOyunlariSubMenu = ".subMenu ul li a[href*='-sonuclari']";
    
    // Canlı yayın linkleri
    private final String ziraatKupasiCanliLink = "a:has-text('Ziraat Türkiye Kupası')";
    private final String ahaberCanliLink = "a[href='/video/canli-yayin/ahaber']";
    private final String aparaCanliLink = "a[href='/apara/canli-yayin']";
    private final String asporCanliLink = "a[href='/video/canli-yayin/aspor']";
    private final String atvCanliLink = "a[href='/video/canli-yayin/atv']";
    private final String a2CanliLink = "a[href='/video/canli-yayin/a2tv']";
    private final String vavTvCanliLink = "a[href='/video/canli-yayin/vavtv']";
    
    /**
     * HeaderComponent constructor
     * @param page Playwright page nesnesi
     */
    public HeaderComponent(Page page) {
        super(page);
        logger.info("HeaderComponent oluşturuldu");
    }
    
    // ============= ÜST HEADER METODLARI =============
    
    /**
     * Üst header'ın görünür olup olmadığını kontrol et
     */
    @Step("Üst header görünürlük kontrolü")
    public boolean isTopHeaderVisible() {
        return isVisible(page.locator(topHeaderContainer));
    }
    
    /**
     * Diğer sitelere git
     * @param siteName Site adı (SabahSpor, Günaydın, Finans vb.)
     */
    @Step("'{0}' sitesine git")
    public void goToOtherSite(String siteName) {
        switch (siteName.toLowerCase()) {
            case "sabahspor":
                click(page.locator(sabahSporLink));
                break;
            case "günaydın":
            case "gunaydin":
                click(page.locator(gunaydınLink));
                break;
            case "finans":
                click(page.locator(finansLink));
                break;
            case "roza":
                click(page.locator(rozaLink));
                break;
            case "memurlar":
                click(page.locator(memurlarLink));
                break;
            default:
                throw new IllegalArgumentException("Geçersiz site adı: " + siteName);
        }
        logger.info("{} sitesine gidildi", siteName);
    }
    
    /**
     * Sosyal medya ikonuna tıkla
     * @param platform Platform adı (Facebook, Twitter, Instagram, YouTube)
     */
    @Step("'{0}' sosyal medya ikonuna tıkla")
    public void clickSocialMediaIcon(String platform) {
        switch (platform.toLowerCase()) {
            case "facebook":
                click(page.locator(facebookIcon));
                break;
            case "twitter":
            case "x":
                click(page.locator(twitterIcon));
                break;
            case "instagram":
                click(page.locator(instagramIcon));
                break;
            case "youtube":
                click(page.locator(youtubeIcon));
                break;
            default:
                throw new IllegalArgumentException("Geçersiz platform: " + platform);
        }
        logger.info("{} sosyal medya ikonuna tıklandı", platform);
    }
    
    /**
     * Canlı yayın butonuna tıkla
     */
    @Step("Canlı yayın butonuna tıkla")
    public void clickCanliYayin() {
        click(page.locator(canliYayinButton));
        logger.info("Canlı yayın butonuna tıklandı");
    }
    
    // ============= ORTA HEADER METODLARI =============
    
    /**
     * Logo'ya tıklayarak ana sayfaya dön
     */
    @Step("Logo'ya tıklayarak ana sayfaya dön")
    public void clickLogo() {
        click(page.locator(sabahLogo));
        logger.info("Logo'ya tıklandı - Ana sayfaya dönüldü");
    }
    
    /**
     * Ana menüden bir linke tıkla
     * @param menuItem Menü öğesi (Son Dakika, Gündem, Ekonomi vb.)
     */
    @Step("Ana menüden '{0}' linkine tıkla")
    public void clickMainMenuItem(String menuItem) {
        String locator = getMainMenuLocator(menuItem);
        click(page.locator(locator));
        logger.info("Ana menüden {} linkine tıklandı", menuItem);
    }
    
    /**
     * Arama butonuna tıkla
     */
    @Step("Arama butonuna tıkla")
    public void clickSearchButton() {
        click(page.locator(aramaButonu));
        logger.info("Arama butonuna tıklandı");
    }
    
    /**
     * Arama kutusuna metin yaz
     * @param aramaMetni Aranacak metin
     */
    @Step("Arama kutusuna '{0}' yaz")
    public void typeSearchText(String aramaMetni) {
        type(page.locator(aramaKutusu), aramaMetni);
        logger.info("Arama kutusuna '{}' yazıldı", aramaMetni);
    }
    
    /**
     * Arama butonuna tıklayarak arama yap
     * @param aramaMetni Aranacak metin
     */
    @Step("'{0}' için arama yap")
    public void performSearch(String aramaMetni) {
        typeSearchText(aramaMetni);
        click(page.locator(aramaButonuAlt));
        logger.info("'{}' için arama yapıldı", aramaMetni);
    }
    
    /**
     * VAV TV linkine tıkla
     */
    @Step("VAV TV linkine tıkla")
    public void clickVavTv() {
        click(page.locator(vavTvLink));
        logger.info("VAV TV linkine tıklandı");
    }
    
    // ============= ALT HEADER METODLARI =============
    
    /**
     * Mevcut sıcaklık değerini al
     * @return Sıcaklık değeri
     */
    @Step("Mevcut sıcaklık değerini al")
    public String getCurrentTemperature() {
        String temp = getText(page.locator(sicaklik));
        logger.info("Mevcut sıcaklık: {}", temp);
        return temp;
    }
    
    /**
     * Borsa verilerini al
     * @param veriTipi Veri tipi (Bist, Dolar, Euro, Altın, Bitcoin)
     * @return Borsa değeri
     */
    @Step("'{0}' borsa verisini al")
    public String getBorsaData(String veriTipi) {
        String locator;
        switch (veriTipi.toLowerCase()) {
            case "bist":
                locator = bistVeri;
                break;
            case "dolar":
                locator = dolarVeri;
                break;
            case "euro":
                locator = euroVeri;
                break;
            case "altın":
            case "altin":
                locator = altinVeri;
                break;
            case "bitcoin":
                locator = bitcoinVeri;
                break;
            default:
                throw new IllegalArgumentException("Geçersiz veri tipi: " + veriTipi);
        }
        
        String value = getText(page.locator(locator));
        logger.info("{} değeri: {}", veriTipi, value);
        return value;
    }
    
    /**
     * Yatsı namazına kalan süreyi al
     * @return Kalan süre
     */
    @Step("Yatsı namazına kalan süreyi al")
    public String getYatsiyaKalanSure() {
        String sure = getText(page.locator(yatsiyaKalanSure));
        logger.info("Yatsıya kalan süre: {}", sure);
        return sure;
    }
    
    // ============= HAMBURGER MENÜ METODLARI =============
    
    /**
     * Hamburger menüyü aç
     */
    @Step("Hamburger menüyü aç")
    public void openHamburgerMenu() {
        if (!isHamburgerMenuOpen()) {
            click(page.locator(hamburgerMenuIcon));
            waitForVisible(page.locator(hamburgerMenuPanel));
            logger.info("Hamburger menü açıldı");
        } else {
            logger.info("Hamburger menü zaten açık");
        }
    }
    
    /**
     * Hamburger menüyü kapat
     */
    @Step("Hamburger menüyü kapat")
    public void closeHamburgerMenu() {
        if (isHamburgerMenuOpen()) {
            click(page.locator(closeMenuIcon));
            waitForHidden(page.locator(hamburgerMenuPanel));
            logger.info("Hamburger menü kapatıldı");
        } else {
            logger.info("Hamburger menü zaten kapalı");
        }
    }
    
    /**
     * Hamburger menünün açık olup olmadığını kontrol et
     * @return Menü açık mı?
     */
    @Step("Hamburger menü durumu kontrolü")
    public boolean isHamburgerMenuOpen() {
        boolean isOpen = isVisible(page.locator(hamburgerMenuPanel));
        logger.debug("Hamburger menü durumu: {}", isOpen ? "Açık" : "Kapalı");
        return isOpen;
    }
    
    /**
     * Hamburger menüden ana kategori seç
     * @param kategori Kategori adı (Haber, Spor, Ekonomi vb.)
     */
    @Step("Hamburger menüden '{0}' kategorisini seç")
    public void selectFromHamburgerMenu(String kategori) {
        openHamburgerMenu();
        String locator = getHamburgerMenuItemLocator(kategori);
        click(page.locator(locator));
        logger.info("Hamburger menüden {} kategorisi seçildi", kategori);
    }
    
    /**
     * Hamburger menüde hover yaparak alt menüyü aç
     * @param anaKategori Ana kategori (Haber, Spor, Ekonomi)
     */
    @Step("'{0}' kategorisi üzerine hover yap")
    public void hoverOnHamburgerMenuItem(String anaKategori) {
        openHamburgerMenu();
        String locator = getHamburgerMenuItemLocator(anaKategori);
        hover(page.locator(locator));
        wait(500); // Alt menünün açılması için kısa bekleme
        logger.info("{} kategorisi üzerine hover yapıldı", anaKategori);
    }
    
    /**
     * Alt menüden seçim yap
     * @param anaKategori Ana kategori
     * @param altKategori Alt kategori
     */
    @Step("'{0}' > '{1}' alt kategorisini seç")
    public void selectSubMenuItem(String anaKategori, String altKategori) {
        hoverOnHamburgerMenuItem(anaKategori);
        
        // Alt menünün açılmasını bekle
        String subMenuLocator = getSubMenuLocator(anaKategori);
        waitForVisible(page.locator(subMenuLocator));
        
        // Alt kategoriye tıkla
        String altKategoriLocator = subMenuLocator + " a:has-text('" + altKategori + "')";
        click(page.locator(altKategoriLocator));
        
        logger.info("{} > {} seçildi", anaKategori, altKategori);
    }
    
    /**
     * Canlı yayın linklerinden birine tıkla
     * @param kanalAdi Kanal adı (AHaber, APara, Aspor vb.)
     */
    @Step("'{0}' canlı yayın linkine tıkla")
    public void clickCanliYayinLink(String kanalAdi) {
        openHamburgerMenu();
        
        String locator;
        switch (kanalAdi.toLowerCase()) {
            case "ahaber":
                locator = ahaberCanliLink;
                break;
            case "apara":
                locator = aparaCanliLink;
                break;
            case "aspor":
                locator = asporCanliLink;
                break;
            case "atv":
                locator = atvCanliLink;
                break;
            case "a2":
                locator = a2CanliLink;
                break;
            case "vavtv":
                locator = vavTvCanliLink;
                break;
            case "ziraat":
                locator = ziraatKupasiCanliLink;
                break;
            default:
                throw new IllegalArgumentException("Geçersiz kanal adı: " + kanalAdi);
        }
        
        click(page.locator(locator));
        logger.info("{} canlı yayın linkine tıklandı", kanalAdi);
    }
    
    /**
     * Hamburger menüdeki tüm ana kategorileri listele
     * @return Kategori listesi
     */
    @Step("Hamburger menüdeki kategorileri listele")
    public List<String> getHamburgerMenuCategories() {
        openHamburgerMenu();
        
        List<String> categories = new ArrayList<>();
        Locator menuItems = page.locator(hamburgerMenuPanel + " > ul > li > a");
        
        int count = menuItems.count();
        for (int i = 0; i < count; i++) {
            String text = menuItems.nth(i).textContent();
            if (text != null && !text.trim().isEmpty()) {
                categories.add(text.trim());
            }
        }
        
        logger.info("Hamburger menüde {} kategori bulundu", categories.size());
        return categories;
    }
    
    /**
     * ESC tuşu ile hamburger menüyü kapat
     */
    @Step("ESC tuşu ile hamburger menüyü kapat")
    public void closeHamburgerMenuWithEsc() {
        if (isHamburgerMenuOpen()) {
            pressKey("Escape");
            waitForHidden(page.locator(hamburgerMenuPanel));
            logger.info("Hamburger menü ESC tuşu ile kapatıldı");
        }
    }
    
    /**
     * Overlay'e tıklayarak hamburger menüyü kapat
     */
    @Step("Overlay'e tıklayarak hamburger menüyü kapat")
    public void closeHamburgerMenuByClickingOverlay() {
        if (isHamburgerMenuOpen()) {
            click(page.locator(menuOverlay));
            waitForHidden(page.locator(hamburgerMenuPanel));
            logger.info("Hamburger menü overlay tıklanarak kapatıldı");
        }
    }
    
    // ============= YARDIMCI METODLAR =============
    
    /**
     * Ana menü locator'ını döndür
     */
    private String getMainMenuLocator(String menuItem) {
        switch (menuItem.toUpperCase()) {
            case "ANA SAYFA":
                return anaSayfaLink;
            case "SON DAKİKA":
            case "SON DAKIKA":
                return sonDakikaLink;
            case "GÜNDEM":
            case "GUNDEM":
                return gundemLink;
            case "EKONOMİ":
            case "EKONOMI":
                return ekonomiLink;
            case "YAŞAM":
            case "YASAM":
                return yasamLink;
            case "EĞİTİM":
            case "EGITIM":
                return egitimLink;
            case "DÜNYA":
            case "DUNYA":
                return dunyaLink;
            case "RESMİ İLANLAR":
            case "RESMI ILANLAR":
                return resmiIlanlarLink;
            case "YAZARLAR":
                return yazarlarLink;
            default:
                throw new IllegalArgumentException("Geçersiz menü öğesi: " + menuItem);
        }
    }
    
    /**
     * Hamburger menü item locator'ını döndür
     */
    private String getHamburgerMenuItemLocator(String kategori) {
        switch (kategori.toLowerCase()) {
            case "haber":
                return menuItemHaber;
            case "spor":
                return menuItemSpor;
            case "ekonomi":
                return menuItemEkonomi;
            case "resmi ilanlar":
            case "resmi i̇lanlar":
                return menuItemResmiIlanlar;
            case "finans":
                return menuItemFinans;
            case "apara":
                return menuItemApara;
            case "tarım":
            case "tarim":
                return menuItemTarim;
            case "eğlence":
            case "eglence":
                return menuItemEglence;
            case "diğer":
            case "diger":
                return menuItemDiger;
            case "galeri":
                return menuItemGaleri;
            case "sabah tv":
            case "sabahtv":
                return menuItemSabahTv;
            case "video galeri":
            case "videogaleri":
                return menuItemVideoGaleri;
            case "ekler":
                return menuItemEkler;
            case "şans oyunları":
            case "sans oyunlari":
                return menuItemSansOyunlari;
            case "yazarlar":
                return menuItemYazarlar;
            default:
                return hamburgerMenuPanel + " a:has-text('" + kategori + "')";
        }
    }
    
    /**
     * Alt menü locator'ını döndür
     */
    private String getSubMenuLocator(String anaKategori) {
        switch (anaKategori.toLowerCase()) {
            case "eğlence":
            case "eglence":
                return eglenceSubMenu;
            case "diğer":
            case "diger":
                return digerSubMenu;
            case "galeri":
                return galeriSubMenu;
            case "sabah tv":
            case "sabahtv":
                return sabahTvSubMenu;
            case "video galeri":
            case "videogaleri":
                return videoGaleriSubMenu;
            case "ekler":
                return eklerSubMenu;
            case "şans oyunları":
            case "sans oyunlari":
                return sansOyunlariSubMenu;
            default:
                return subMenuContainer;
        }
    }
    
    /**
     * Header'ın tamamen yüklendiğini doğrula
     */
    @Step("Header yükleme kontrolü")
    public boolean isHeaderFullyLoaded() {
        try {
            // Ana header container kontrolü
            waitForVisible(page.locator(topHeaderContainer), SHORT_TIMEOUT);
            
            // Logo kontrolü
            waitForVisible(page.locator(sabahLogo), SHORT_TIMEOUT);
            
            // Ana menü kontrolü
            waitForVisible(page.locator(middleHeaderContainer), SHORT_TIMEOUT);
            
            // Arama frame kontrolü
            waitForVisible(page.locator(bottomHeaderContainer), SHORT_TIMEOUT);
            
            logger.info("Header tamamen yüklendi");
            return true;
            
        } catch (Exception e) {
            logger.error("Header yükleme hatası: {}", e.getMessage());
            return false;
        }
    }
}
