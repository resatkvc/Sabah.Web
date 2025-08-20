package sabah.com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import sabah.com.config.ConfigReader;

public class SabahHomePage extends BasePage {

    // Header Elements
    @FindBy(css = "header.header")
    private WebElement headerElement;

    @FindBy(css = "div.logo a[title='Son Dakika Haberleri']")
    private WebElement logoLink;

    @FindBy(css = "div.logo img[alt='Son Dakika Haberleri']")
    private WebElement logoImage;

    // Top Menu Elements (nav.menuTop)
    @FindBy(css = "nav.menuTop")
    private WebElement topMenu;

    @FindBy(css = "nav.menuTop a[href='/spor-haberleri']")
    private WebElement sabahSporLink;

    @FindBy(css = "nav.menuTop a[href='/magazin-haberleri']")
    private WebElement sabahGunaydinLink;

    @FindBy(css = "nav.menuTop a[href='/finans']")
    private WebElement aParaLink;

    @FindBy(css = "nav.menuTop a[href='/roza']")
    private WebElement rozaLink;

    @FindBy(css = "nav.menuTop a[href='/memurlar']")
    private WebElement memurlarLink;

    @FindBy(css = "nav.menuTop a[href='/kobi']")
    private WebElement halkbankKobiLink;

    @FindBy(css = "nav.menuTop a[href='/video']")
    private WebElement videoLink;

    @FindBy(css = "nav.menuTop a[href='javascript:;']")
    private WebElement canliYayinLink;

    // Main Navigation Elements (nav.menu.navobile-desktop-only)
    @FindBy(css = "nav.menu.navobile-desktop-only")
    private WebElement mainNavigation;

    @FindBy(css = "nav.menu a[href='https://www.sabah.com.tr'][title='Anasayfa']")
    private WebElement anaSayfaLink;

    @FindBy(css = "nav.menu a[href='/son-dakika-haberleri']")
    private WebElement sonDakikaLink;

    @FindBy(css = "nav.menu a[href='/gundem']")
    private WebElement gundemLink;

    @FindBy(css = "nav.menu a[href='/ekonomi']")
    private WebElement ekonomiLink;

    @FindBy(css = "nav.menu a[href='/yasam']")
    private WebElement yasamLink;

    @FindBy(css = "nav.menu a[href='/egitim']")
    private WebElement egitimLink;

    @FindBy(css = "nav.menu a[href='/dunya']")
    private WebElement dunyaLink;

    @FindBy(css = "nav.menu a[href='/resmi-ilan']")
    private WebElement resmiIlanlarLink;

    @FindBy(css = "nav.menu a[href='/yazarlar']")
    private WebElement yazarlarLink;

    // Social Media Elements
    @FindBy(css = "a.face[href*='facebook.com/sabah']")
    private WebElement facebookLink;

    @FindBy(css = "a.twt[href*='x.com/sabah']")
    private WebElement twitterLink;

    @FindBy(css = "a.inst[href*='instagram.com/sabah']")
    private WebElement instagramLink;

    @FindBy(css = "a.ytb[href*='youtube.com/@sabah']")
    private WebElement youtubeLink;

    public SabahHomePage() {
        super();
        PageFactory.initElements(driver, this);
    }

    public void navigateToHomePage() {
        navigateTo(ConfigReader.getBaseUrl());
    }

    // Header Verification Methods
    public boolean isHeaderDisplayed() {
        return isElementDisplayed(By.cssSelector("header.header"));
    }

    public boolean isLogoDisplayed() {
        return isElementDisplayed(By.cssSelector("div.logo img[alt='Son Dakika Haberleri']"));
    }

    public String getLogoAltText() {
        return getElementText(By.cssSelector("div.logo img[alt='Son Dakika Haberleri']"));
    }

    public String getLogoHref() {
        return driver.findElement(By.cssSelector("div.logo a[title='Son Dakika Haberleri']")).getAttribute("href");
    }

    // Top Menu Verification Methods
    public boolean isTopMenuDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menuTop"));
    }

    public boolean isSabahSporLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menuTop a[href='/spor-haberleri']"));
    }

    public boolean isSabahGunaydinLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menuTop a[href='/magazin-haberleri']"));
    }

    public boolean isAParaLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menuTop a[href='/finans']"));
    }

    public boolean isRozaLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menuTop a[href='/roza']"));
    }

    public boolean isMemurlarLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menuTop a[href='/memurlar']"));
    }

    public boolean isHalkbankKobiLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menuTop a[href='/kobi']"));
    }

    public boolean isVideoLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menuTop a[href='/video']"));
    }

    public boolean isCanliYayinLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menuTop a[href='javascript:;']"));
    }

    // Main Navigation Verification Methods
    public boolean isMainNavigationDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menu.navobile-desktop-only"));
    }

    public boolean isAnaSayfaLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menu a[href='https://www.sabah.com.tr'][title='Anasayfa']"));
    }

    public boolean isSonDakikaLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menu a[href='/son-dakika-haberleri']"));
    }

    public boolean isGundemLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menu a[href='/gundem']"));
    }

    public boolean isEkonomiLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menu a[href='/ekonomi']"));
    }

    public boolean isYasamLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menu a[href='/yasam']"));
    }

    public boolean isEgitimLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menu a[href='/egitim']"));
    }

    public boolean isDunyaLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menu a[href='/dunya']"));
    }

    public boolean isResmiIlanlarLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menu a[href='/resmi-ilan']"));
    }

    public boolean isYazarlarLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menu a[href='/yazarlar']"));
    }

    // Social Media Verification Methods
    public boolean isFacebookLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("a.face[href*='facebook.com/sabah']"));
    }

    public boolean isTwitterLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("a.twt[href*='x.com/sabah']"));
    }

    public boolean isInstagramLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("a.inst[href*='instagram.com/sabah']"));
    }

    public boolean isYoutubeLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("a.ytb[href*='youtube.com/@sabah']"));
    }

    // URL and Title Verification
    public boolean verifyCurrentUrl() {
        String currentUrl = getCurrentUrl();
        return currentUrl.equals(ConfigReader.getBaseUrl()) || currentUrl.equals(ConfigReader.getBaseUrl() + "/");
    }

    public boolean verifyPageTitle() {
        String title = getPageTitle();
        return title.contains("Sabah") || title.contains("sabah.com.tr");
    }

    // Comprehensive Header Verification
    public boolean verifyHeaderElements() {
        System.out.println("=== Header Verification Started ===");
        
        boolean headerDisplayed = isHeaderDisplayed();
        System.out.println("Header displayed: " + headerDisplayed);
        
        boolean logoDisplayed = isLogoDisplayed();
        System.out.println("Logo displayed: " + logoDisplayed);
        
        boolean topMenuDisplayed = isTopMenuDisplayed();
        System.out.println("Top menu displayed: " + topMenuDisplayed);
        
        boolean mainNavDisplayed = isMainNavigationDisplayed();
        System.out.println("Main navigation displayed: " + mainNavDisplayed);
        
        System.out.println("=== Header Verification Completed ===");
        
        // Return true if all required elements are displayed
        return headerDisplayed && logoDisplayed && topMenuDisplayed && mainNavDisplayed;
    }

    // Comprehensive Top Menu Verification
    public boolean verifyTopMenuElements() {
        System.out.println("=== Top Menu Verification Started ===");
        
        boolean sabahSporDisplayed = isSabahSporLinkDisplayed();
        System.out.println("Sabah Spor displayed: " + sabahSporDisplayed);
        
        boolean sabahGunaydinDisplayed = isSabahGunaydinLinkDisplayed();
        System.out.println("Sabah Günaydın displayed: " + sabahGunaydinDisplayed);
        
        boolean aParaDisplayed = isAParaLinkDisplayed();
        System.out.println("A Para displayed: " + aParaDisplayed);
        
        boolean rozaDisplayed = isRozaLinkDisplayed();
        System.out.println("Roza displayed: " + rozaDisplayed);
        
        boolean memurlarDisplayed = isMemurlarLinkDisplayed();
        System.out.println("Memurlar displayed: " + memurlarDisplayed);
        
        boolean halkbankKobiDisplayed = isHalkbankKobiLinkDisplayed();
        System.out.println("Halkbank KOBİ displayed: " + halkbankKobiDisplayed);
        
        boolean videoDisplayed = isVideoLinkDisplayed();
        System.out.println("Video displayed: " + videoDisplayed);
        
        boolean canliYayinDisplayed = isCanliYayinLinkDisplayed();
        System.out.println("Canlı Yayın displayed: " + canliYayinDisplayed);
        
        System.out.println("=== Top Menu Verification Completed ===");
        
        return sabahSporDisplayed && sabahGunaydinDisplayed && aParaDisplayed && 
               rozaDisplayed && memurlarDisplayed && halkbankKobiDisplayed && 
               videoDisplayed && canliYayinDisplayed;
    }

    // Comprehensive Main Navigation Verification
    public boolean verifyMainNavigationElements() {
        System.out.println("=== Main Navigation Verification Started ===");
        
        boolean anaSayfaDisplayed = isAnaSayfaLinkDisplayed();
        System.out.println("Ana Sayfa displayed: " + anaSayfaDisplayed);
        
        boolean sonDakikaDisplayed = isSonDakikaLinkDisplayed();
        System.out.println("Son Dakika displayed: " + sonDakikaDisplayed);
        
        boolean gundemDisplayed = isGundemLinkDisplayed();
        System.out.println("Gündem displayed: " + gundemDisplayed);
        
        boolean ekonomiDisplayed = isEkonomiLinkDisplayed();
        System.out.println("Ekonomi displayed: " + ekonomiDisplayed);
        
        boolean yasamDisplayed = isYasamLinkDisplayed();
        System.out.println("Yaşam displayed: " + yasamDisplayed);
        
        boolean egitimDisplayed = isEgitimLinkDisplayed();
        System.out.println("Eğitim displayed: " + egitimDisplayed);
        
        boolean dunyaDisplayed = isDunyaLinkDisplayed();
        System.out.println("Dünya displayed: " + dunyaDisplayed);
        
        boolean resmiIlanlarDisplayed = isResmiIlanlarLinkDisplayed();
        System.out.println("Resmi İlanlar displayed: " + resmiIlanlarDisplayed);
        
        boolean yazarlarDisplayed = isYazarlarLinkDisplayed();
        System.out.println("Yazarlar displayed: " + yazarlarDisplayed);
        
        System.out.println("=== Main Navigation Verification Completed ===");
        
        return anaSayfaDisplayed && sonDakikaDisplayed && gundemDisplayed && 
               ekonomiDisplayed && yasamDisplayed && egitimDisplayed && 
               dunyaDisplayed && resmiIlanlarDisplayed && yazarlarDisplayed;
    }

    // Comprehensive Social Media Verification
    public boolean verifySocialMediaElements() {
        System.out.println("=== Social Media Verification Started ===");
        
        boolean facebookDisplayed = isFacebookLinkDisplayed();
        System.out.println("Facebook displayed: " + facebookDisplayed);
        
        boolean twitterDisplayed = isTwitterLinkDisplayed();
        System.out.println("Twitter displayed: " + twitterDisplayed);
        
        boolean instagramDisplayed = isInstagramLinkDisplayed();
        System.out.println("Instagram displayed: " + instagramDisplayed);
        
        boolean youtubeDisplayed = isYoutubeLinkDisplayed();
        System.out.println("YouTube displayed: " + youtubeDisplayed);
        
        System.out.println("=== Social Media Verification Completed ===");
        
        return facebookDisplayed && twitterDisplayed && instagramDisplayed && youtubeDisplayed;
    }
}
