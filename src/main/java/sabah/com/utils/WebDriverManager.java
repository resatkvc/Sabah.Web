package sabah.com.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import sabah.com.config.ConfigReader;

public class DriverManager {
    private static final Logger logger = Logger.getLogger(DriverManager.class);
    private static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    private static void initializeDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        
        switch (browser) {
            case "chrome":
                setupChromeDriver();
                break;
            case "firefox":
                setupFirefoxDriver();
                break;
            case "edge":
                setupEdgeDriver();
                break;
            default:
                logger.warn("Unsupported browser: " + browser + ". Using Chrome as default.");
                setupChromeDriver();
                break;
        }

        // Configure driver settings
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(ConfigReader.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(ConfigReader.getPageLoadTimeout()));
        
        logger.info("WebDriver initialized successfully with browser: " + browser);
    }

    private static void setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        driver = new ChromeDriver(options);
    }

    private static void setupFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        driver = new FirefoxDriver(options);
    }

    private static void setupEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        driver = new EdgeDriver(options);
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            logger.info("WebDriver closed successfully");
        }
    }
}
