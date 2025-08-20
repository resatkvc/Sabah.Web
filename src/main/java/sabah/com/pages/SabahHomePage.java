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

    // Top Menu Elements
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

    @FindBy(css = "nav.menuTop a[href='/video']")
    private WebElement videoLink;

    // Main Navigation Elements
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

    @FindBy(css = "nav.menu a[href='/dunya']")
    private WebElement dunyaLink;

    // Search Elements
    @FindBy(css = "div.searchFrame")
    private WebElement searchFrame;

    @FindBy(css = "input#txtHeaderSearch")
    private WebElement searchInput;

    @FindBy(css = "a#btnHeaderSearch")
    private WebElement searchButton;

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

    public boolean isVideoLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menuTop a[href='/video']"));
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

    public boolean isDunyaLinkDisplayed() {
        return isElementDisplayed(By.cssSelector("nav.menu a[href='/dunya']"));
    }

    // Search Verification Methods
    public boolean isSearchFrameDisplayed() {
        return isElementDisplayed(By.cssSelector("div.searchFrame"));
    }

    public boolean isSearchInputDisplayed() {
        return isElementDisplayed(By.cssSelector("input#txtHeaderSearch"));
    }

    public boolean isSearchButtonDisplayed() {
        return isElementDisplayed(By.cssSelector("a#btnHeaderSearch"));
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
        
        boolean searchDisplayed = isSearchFrameDisplayed();
        System.out.println("Search frame displayed: " + searchDisplayed);
        
        System.out.println("=== Header Verification Completed ===");
        
        return headerDisplayed && logoDisplayed && topMenuDisplayed && mainNavDisplayed && searchDisplayed;
    }
}
