package sabah.com.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Uygulama özelliklerini yönetmek için konfigürasyon okuyucu sınıfı
 */
public class ConfigReader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static PropertiesConfiguration config;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    
    static {
        try {
            loadConfig();
        } catch (ConfigurationException e) {
            logger.error("Konfigürasyon dosyası yüklenemedi", e);
            throw new RuntimeException("Konfigürasyon yüklenemedi", e);
        }
    }
    
    /**
     * Properties dosyasından konfigürasyonu yükle
     */
    private static void loadConfig() throws ConfigurationException {
        File configFile = new File(CONFIG_FILE_PATH);
        if (!configFile.exists()) {
            throw new ConfigurationException("Konfigürasyon dosyası bulunamadı: " + CONFIG_FILE_PATH);
        }
        
        config = new PropertiesConfiguration(configFile);
        config.setAutoSave(true);
        logger.info("Konfigürasyon başarıyla yüklendi: {}", CONFIG_FILE_PATH);
    }
    
    /**
     * String özellik değerini al
     */
    public static String getProperty(String key) {
        String value = config.getString(key);
        if (value == null) {
            logger.warn("Özellik bulunamadı: {}", key);
        }
        return value;
    }
    
    /**
     * Varsayılan değer ile string özellik al
     */
    public static String getProperty(String key, String defaultValue) {
        return config.getString(key, defaultValue);
    }
    
    /**
     * Integer özellik değerini al
     */
    public static int getIntProperty(String key) {
        return config.getInt(key);
    }
    
    /**
     * Varsayılan değer ile integer özellik al
     */
    public static int getIntProperty(String key, int defaultValue) {
        return config.getInt(key, defaultValue);
    }
    
    /**
     * Boolean özellik değerini al
     */
    public static boolean getBooleanProperty(String key) {
        return config.getBoolean(key);
    }
    
    /**
     * Varsayılan değer ile boolean özellik al
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        return config.getBoolean(key, defaultValue);
    }
    
    /**
     * Long özellik değerini al
     */
    public static long getLongProperty(String key) {
        return config.getLong(key);
    }
    
    /**
     * Varsayılan değer ile long özellik al
     */
    public static long getLongProperty(String key, long defaultValue) {
        return config.getLong(key, defaultValue);
    }
    
    /**
     * Özellik değerini ayarla
     */
    public static void setProperty(String key, Object value) {
        config.setProperty(key, value);
        logger.debug("Özellik ayarlandı: {} = {}", key, value);
    }
    
    /**
     * Özelliğin var olup olmadığını kontrol et
     */
    public static boolean containsKey(String key) {
        return config.containsKey(key);
    }
    
    // Konfigürasyon anahtarları sabitler olarak
    public static final String BASE_URL = "base.url";
    public static final String BROWSER_TYPE = "browser.type";
    public static final String BROWSER_HEADLESS = "browser.headless";
    public static final String BROWSER_SLOW_MOTION = "browser.slow.motion";
    public static final String BROWSER_TIMEOUT = "browser.timeout";
    public static final String BROWSER_MAXIMIZE = "browser.maximize";
    public static final String VIEWPORT_WIDTH = "viewport.width";
    public static final String VIEWPORT_HEIGHT = "viewport.height";
    public static final String SCREENSHOT_ON_FAILURE = "screenshot.on.failure";
    public static final String SCREENSHOT_PATH = "screenshot.path";
    public static final String TEST_RETRY_COUNT = "test.retry.count";
    public static final String WAIT_TIMEOUT_DEFAULT = "wait.timeout.default";
    public static final String WAIT_TIMEOUT_LONG = "wait.timeout.long";
    public static final String WAIT_TIMEOUT_SHORT = "wait.timeout.short";
    public static final String PAGE_LOAD_TIMEOUT = "page.load.timeout";
    public static final String PAGE_LOAD_WAIT_UNTIL = "page.load.wait.until";
    public static final String PAGE_LOAD_NETWORK_IDLE_TIMEOUT = "page.load.network.idle.timeout";
    public static final String TEST_ENVIRONMENT = "test.environment";
    public static final String PARALLEL_TESTS = "parallel.tests";
    public static final String PARALLEL_THREAD_COUNT = "parallel.thread.count";
    public static final String VIDEO_RECORD = "video.record";
    public static final String VIDEO_RECORD_DIR = "video.record.dir";
    public static final String TRACING_ENABLED = "tracing.enabled";
    public static final String REPORT_DIR = "report.dir";
    public static final String REPORT_HISTORY_DIR = "report.history.dir";
}
