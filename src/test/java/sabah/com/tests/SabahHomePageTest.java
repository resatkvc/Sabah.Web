package sabah.com.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sabah.com.pages.SabahHomePage;
import sabah.com.utils.DriverManager;

public class SabahHomePageTest {
    
    private SabahHomePage sabahHomePage;

    @BeforeMethod
    public void setUp() {
        System.out.println("=== Test Setup Started ===");
        sabahHomePage = new SabahHomePage();
        System.out.println("=== Test Setup Completed ===");
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("=== Test Teardown Started ===");
        DriverManager.quitDriver();
        System.out.println("=== Test Teardown Completed ===");
    }

    @Test(description = "Verify Sabah.com.tr homepage loads successfully")
    public void testHomePageLoadsSuccessfully() {
        System.out.println("=== Test: Homepage Loads Successfully ===");
        
        // Navigate to homepage
        sabahHomePage.navigateToHomePage();
        
        // Verify URL
        boolean urlVerified = sabahHomePage.verifyCurrentUrl();
        System.out.println("URL verification: " + urlVerified);
        Assert.assertTrue(urlVerified, "Current URL should be Sabah.com.tr homepage");
        
        // Verify page title
        boolean titleVerified = sabahHomePage.verifyPageTitle();
        System.out.println("Title verification: " + titleVerified);
        Assert.assertTrue(titleVerified, "Page title should contain 'Sabah'");
        
        System.out.println("=== Test: Homepage Loads Successfully - PASSED ===");
    }

    @Test(description = "Verify header elements are displayed correctly")
    public void testHeaderElementsDisplayed() {
        System.out.println("=== Test: Header Elements Displayed ===");
        
        // Navigate to homepage
        sabahHomePage.navigateToHomePage();
        
        // Verify header elements
        boolean headerVerified = sabahHomePage.verifyHeaderElements();
        System.out.println("Header verification: " + headerVerified);
        Assert.assertTrue(headerVerified, "All header elements should be displayed");
        
        System.out.println("=== Test: Header Elements Displayed - PASSED ===");
    }

    @Test(description = "Verify logo is displayed and clickable")
    public void testLogoDisplayed() {
        System.out.println("=== Test: Logo Displayed ===");
        
        // Navigate to homepage
        sabahHomePage.navigateToHomePage();
        
        // Verify logo is displayed
        boolean logoDisplayed = sabahHomePage.isLogoDisplayed();
        System.out.println("Logo displayed: " + logoDisplayed);
        Assert.assertTrue(logoDisplayed, "Logo should be displayed");
        
        // Verify logo alt text
        String logoAltText = sabahHomePage.getLogoAltText();
        System.out.println("Logo alt text: " + logoAltText);
        Assert.assertNotNull(logoAltText, "Logo should have alt text");
        
        // Verify logo href
        String logoHref = sabahHomePage.getLogoHref();
        System.out.println("Logo href: " + logoHref);
        Assert.assertTrue(logoHref.contains("sabah.com.tr"), "Logo should link to Sabah homepage");
        
        System.out.println("=== Test: Logo Displayed - PASSED ===");
    }

    @Test(description = "Verify top menu elements are displayed")
    public void testTopMenuElements() {
        System.out.println("=== Test: Top Menu Elements ===");
        
        // Navigate to homepage
        sabahHomePage.navigateToHomePage();
        
        // Verify top menu is displayed
        boolean topMenuDisplayed = sabahHomePage.isTopMenuDisplayed();
        System.out.println("Top menu displayed: " + topMenuDisplayed);
        Assert.assertTrue(topMenuDisplayed, "Top menu should be displayed");
        
        // Verify individual menu items
        boolean sabahSporDisplayed = sabahHomePage.isSabahSporLinkDisplayed();
        System.out.println("Sabah Spor displayed: " + sabahSporDisplayed);
        Assert.assertTrue(sabahSporDisplayed, "Sabah Spor link should be displayed");
        
        boolean sabahGunaydinDisplayed = sabahHomePage.isSabahGunaydinLinkDisplayed();
        System.out.println("Sabah Günaydın displayed: " + sabahGunaydinDisplayed);
        Assert.assertTrue(sabahGunaydinDisplayed, "Sabah Günaydın link should be displayed");
        
        boolean aParaDisplayed = sabahHomePage.isAParaLinkDisplayed();
        System.out.println("A Para displayed: " + aParaDisplayed);
        Assert.assertTrue(aParaDisplayed, "A Para link should be displayed");
        
        boolean rozaDisplayed = sabahHomePage.isRozaLinkDisplayed();
        System.out.println("Roza displayed: " + rozaDisplayed);
        Assert.assertTrue(rozaDisplayed, "Roza link should be displayed");
        
        boolean memurlarDisplayed = sabahHomePage.isMemurlarLinkDisplayed();
        System.out.println("Memurlar displayed: " + memurlarDisplayed);
        Assert.assertTrue(memurlarDisplayed, "Memurlar link should be displayed");
        
        boolean videoDisplayed = sabahHomePage.isVideoLinkDisplayed();
        System.out.println("Video displayed: " + videoDisplayed);
        Assert.assertTrue(videoDisplayed, "Video link should be displayed");
        
        System.out.println("=== Test: Top Menu Elements - PASSED ===");
    }

    @Test(description = "Verify main navigation elements are displayed")
    public void testMainNavigationElements() {
        System.out.println("=== Test: Main Navigation Elements ===");
        
        // Navigate to homepage
        sabahHomePage.navigateToHomePage();
        
        // Verify main navigation is displayed
        boolean mainNavDisplayed = sabahHomePage.isMainNavigationDisplayed();
        System.out.println("Main navigation displayed: " + mainNavDisplayed);
        Assert.assertTrue(mainNavDisplayed, "Main navigation should be displayed");
        
        // Verify individual navigation items
        boolean anaSayfaDisplayed = sabahHomePage.isAnaSayfaLinkDisplayed();
        System.out.println("Ana Sayfa displayed: " + anaSayfaDisplayed);
        Assert.assertTrue(anaSayfaDisplayed, "Ana Sayfa link should be displayed");
        
        boolean sonDakikaDisplayed = sabahHomePage.isSonDakikaLinkDisplayed();
        System.out.println("Son Dakika displayed: " + sonDakikaDisplayed);
        Assert.assertTrue(sonDakikaDisplayed, "Son Dakika link should be displayed");
        
        boolean gundemDisplayed = sabahHomePage.isGundemLinkDisplayed();
        System.out.println("Gündem displayed: " + gundemDisplayed);
        Assert.assertTrue(gundemDisplayed, "Gündem link should be displayed");
        
        boolean ekonomiDisplayed = sabahHomePage.isEkonomiLinkDisplayed();
        System.out.println("Ekonomi displayed: " + ekonomiDisplayed);
        Assert.assertTrue(ekonomiDisplayed, "Ekonomi link should be displayed");
        
        boolean yasamDisplayed = sabahHomePage.isYasamLinkDisplayed();
        System.out.println("Yaşam displayed: " + yasamDisplayed);
        Assert.assertTrue(yasamDisplayed, "Yaşam link should be displayed");
        
        boolean dunyaDisplayed = sabahHomePage.isDunyaLinkDisplayed();
        System.out.println("Dünya displayed: " + dunyaDisplayed);
        Assert.assertTrue(dunyaDisplayed, "Dünya link should be displayed");
        
        System.out.println("=== Test: Main Navigation Elements - PASSED ===");
    }

    @Test(description = "Verify search functionality elements are displayed")
    public void testSearchElements() {
        System.out.println("=== Test: Search Elements ===");
        
        // Navigate to homepage
        sabahHomePage.navigateToHomePage();
        
        // Verify search frame is displayed
        boolean searchFrameDisplayed = sabahHomePage.isSearchFrameDisplayed();
        System.out.println("Search frame displayed: " + searchFrameDisplayed);
        Assert.assertTrue(searchFrameDisplayed, "Search frame should be displayed");
        
        // Verify search input is displayed
        boolean searchInputDisplayed = sabahHomePage.isSearchInputDisplayed();
        System.out.println("Search input displayed: " + searchInputDisplayed);
        Assert.assertTrue(searchInputDisplayed, "Search input should be displayed");
        
        // Verify search button is displayed
        boolean searchButtonDisplayed = sabahHomePage.isSearchButtonDisplayed();
        System.out.println("Search button displayed: " + searchButtonDisplayed);
        Assert.assertTrue(searchButtonDisplayed, "Search button should be displayed");
        
        System.out.println("=== Test: Search Elements - PASSED ===");
    }

    @Test(description = "Verify social media links are displayed")
    public void testSocialMediaLinks() {
        System.out.println("=== Test: Social Media Links ===");
        
        // Navigate to homepage
        sabahHomePage.navigateToHomePage();
        
        // Verify social media links
        boolean facebookDisplayed = sabahHomePage.isFacebookLinkDisplayed();
        System.out.println("Facebook displayed: " + facebookDisplayed);
        Assert.assertTrue(facebookDisplayed, "Facebook link should be displayed");
        
        boolean twitterDisplayed = sabahHomePage.isTwitterLinkDisplayed();
        System.out.println("Twitter displayed: " + twitterDisplayed);
        Assert.assertTrue(twitterDisplayed, "Twitter link should be displayed");
        
        boolean instagramDisplayed = sabahHomePage.isInstagramLinkDisplayed();
        System.out.println("Instagram displayed: " + instagramDisplayed);
        Assert.assertTrue(instagramDisplayed, "Instagram link should be displayed");
        
        boolean youtubeDisplayed = sabahHomePage.isYoutubeLinkDisplayed();
        System.out.println("YouTube displayed: " + youtubeDisplayed);
        Assert.assertTrue(youtubeDisplayed, "YouTube link should be displayed");
        
        System.out.println("=== Test: Social Media Links - PASSED ===");
    }
}
