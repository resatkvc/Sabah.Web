package sabah.com.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.config.ConfigReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Test verilerini yöneten sınıf
 * Excel, JSON, Properties dosyalarından test verilerini okur
 */
public class TestDataManager {
    
    private static final Logger logger = LoggerFactory.getLogger(TestDataManager.class);
    private static Properties testData;
    private static final String TEST_DATA_FILE = "src/test/resources/testdata.properties";
    
    static {
        loadTestData();
    }
    
    /**
     * Test verilerini yükle
     */
    private static void loadTestData() {
        try {
            testData = new Properties();
            FileInputStream fis = new FileInputStream(TEST_DATA_FILE);
            testData.load(fis);
            fis.close();
            logger.info("Test verileri başarıyla yüklendi");
        } catch (IOException e) {
            logger.error("Test verileri yüklenemedi: {}", e.getMessage());
            // Varsayılan verileri oluştur
            createDefaultTestData();
        }
    }
    
    /**
     * Varsayılan test verilerini oluştur
     */
    private static void createDefaultTestData() {
        testData = new Properties();
        
        // Arama anahtar kelimeleri
        testData.setProperty("search.keywords", "gündem,ekonomi,spor,teknoloji");
        testData.setProperty("search.valid", "haber");
        testData.setProperty("search.invalid", "xyz123abc");
        
        // Kategori isimleri
        testData.setProperty("categories.main", "gündem,ekonomi,spor,yaşam,dünya");
        testData.setProperty("categories.sub", "futbol,basketbol,tenis");
        
        // Sosyal medya linkleri
        testData.setProperty("social.facebook", "https://www.facebook.com/sabah");
        testData.setProperty("social.twitter", "https://twitter.com/sabah");
        testData.setProperty("social.instagram", "https://www.instagram.com/sabah");
        testData.setProperty("social.youtube", "https://www.youtube.com/sabah");
        
        // Borsa verileri
        testData.setProperty("borsa.bist", "BIST");
        testData.setProperty("borsa.dolar", "Dolar");
        testData.setProperty("borsa.euro", "Euro");
        testData.setProperty("borsa.altin", "Altın");
        
        // Test kullanıcıları
        testData.setProperty("user.valid.email", "test@example.com");
        testData.setProperty("user.valid.password", "Test123!");
        testData.setProperty("user.invalid.email", "invalid@test.com");
        testData.setProperty("user.invalid.password", "wrong");
        
        logger.info("Varsayılan test verileri oluşturuldu");
    }
    
    /**
     * Test verisini al
     * @param key Veri anahtarı
     * @return Veri değeri
     */
    public static String getTestData(String key) {
        return testData.getProperty(key, "");
    }
    
    /**
     * Test verisini al (varsayılan değerle)
     * @param key Veri anahtarı
     * @param defaultValue Varsayılan değer
     * @return Veri değeri
     */
    public static String getTestData(String key, String defaultValue) {
        return testData.getProperty(key, defaultValue);
    }
    
    /**
     * Arama anahtar kelimelerini al
     * @return Arama anahtar kelimeleri listesi
     */
    public static List<String> getSearchKeywords() {
        String keywords = getTestData("search.keywords");
        return Arrays.asList(keywords.split(","));
    }
    
    /**
     * Ana kategorileri al
     * @return Ana kategoriler listesi
     */
    public static List<String> getMainCategories() {
        String categories = getTestData("categories.main");
        return Arrays.asList(categories.split(","));
    }
    
    /**
     * Alt kategorileri al
     * @return Alt kategoriler listesi
     */
    public static List<String> getSubCategories() {
        String categories = getTestData("categories.sub");
        return Arrays.asList(categories.split(","));
    }
    
    /**
     * Sosyal medya linklerini al
     * @return Sosyal medya linkleri map'i
     */
    public static Map<String, String> getSocialMediaLinks() {
        Map<String, String> links = new HashMap<>();
        links.put("facebook", getTestData("social.facebook"));
        links.put("twitter", getTestData("social.twitter"));
        links.put("instagram", getTestData("social.instagram"));
        links.put("youtube", getTestData("social.youtube"));
        return links;
    }
    
    /**
     * Borsa verilerini al
     * @return Borsa verileri listesi
     */
    public static List<String> getBorsaData() {
        List<String> borsaData = new ArrayList<>();
        borsaData.add(getTestData("borsa.bist"));
        borsaData.add(getTestData("borsa.dolar"));
        borsaData.add(getTestData("borsa.euro"));
        borsaData.add(getTestData("borsa.altin"));
        return borsaData;
    }
    
    /**
     * Geçerli kullanıcı bilgilerini al
     * @return Kullanıcı bilgileri map'i
     */
    public static Map<String, String> getValidUser() {
        Map<String, String> user = new HashMap<>();
        user.put("email", getTestData("user.valid.email"));
        user.put("password", getTestData("user.valid.password"));
        return user;
    }
    
    /**
     * Geçersiz kullanıcı bilgilerini al
     * @return Kullanıcı bilgileri map'i
     */
    public static Map<String, String> getInvalidUser() {
        Map<String, String> user = new HashMap<>();
        user.put("email", getTestData("user.invalid.email"));
        user.put("password", getTestData("user.invalid.password"));
        return user;
    }
    
    /**
     * Rastgele test verisi al
     * @param keyPrefix Veri anahtarı öneki
     * @return Rastgele veri
     */
    public static String getRandomTestData(String keyPrefix) {
        List<String> values = new ArrayList<>();
        for (String key : testData.stringPropertyNames()) {
            if (key.startsWith(keyPrefix)) {
                values.add(testData.getProperty(key));
            }
        }
        
        if (values.isEmpty()) {
            return "";
        }
        
        Random random = new Random();
        return values.get(random.nextInt(values.size()));
    }
    
    /**
     * Test verilerini yeniden yükle
     */
    public static void reloadTestData() {
        loadTestData();
    }
}
