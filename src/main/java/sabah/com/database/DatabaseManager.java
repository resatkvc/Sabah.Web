package sabah.com.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.data.TestDataManager;
import sabah.com.performance.PerformanceResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Veritabanı işlemleri için yönetici sınıf
 * Test sonuçlarını ve verilerini veritabanında saklar
 */
public class DatabaseManager {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private static final String DB_URL = TestDataManager.getTestData("db.connection.url");
    private static final String DB_USERNAME = TestDataManager.getTestData("db.username");
    private static final String DB_PASSWORD = TestDataManager.getTestData("db.password");
    private static final String DB_DRIVER = TestDataManager.getTestData("db.driver");
    
    private static Connection connection;
    
    /**
     * Veritabanı bağlantısını başlat
     * @return Bağlantı başarılı mı?
     */
    public static boolean initializeConnection() {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            logger.info("Veritabanı bağlantısı başarılı: {}", DB_URL);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Veritabanı bağlantı hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Veritabanı bağlantısını kapat
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Veritabanı bağlantısı kapatıldı");
            }
        } catch (SQLException e) {
            logger.error("Veritabanı bağlantısı kapatma hatası: {}", e.getMessage());
        }
    }
    
    /**
     * Test sonucunu kaydet
     * @param testResult Test sonucu
     * @return Başarılı mı?
     */
    public static boolean saveTestResult(TestResult testResult) {
        if (connection == null) {
            logger.error("Veritabanı bağlantısı yok");
            return false;
        }
        
        String sql = "INSERT INTO test_results (test_name, test_class, status, duration, error_message, screenshot_path, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, testResult.getTestName());
            pstmt.setString(2, testResult.getTestClass());
            pstmt.setString(3, testResult.getStatus());
            pstmt.setLong(4, testResult.getDuration());
            pstmt.setString(5, testResult.getErrorMessage());
            pstmt.setString(6, testResult.getScreenshotPath());
            pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            
            int rowsAffected = pstmt.executeUpdate();
            logger.debug("Test sonucu kaydedildi: {} satır etkilendi", rowsAffected);
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            logger.error("Test sonucu kaydetme hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Performans sonucunu kaydet
     * @param performanceResult Performans sonucu
     * @return Başarılı mı?
     */
    public static boolean savePerformanceResult(PerformanceResult performanceResult) {
        if (connection == null) {
            logger.error("Veritabanı bağlantısı yok");
            return false;
        }
        
        String sql = "INSERT INTO performance_results (url, dom_ready_time, page_load_time, js_metrics, passed, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, performanceResult.getUrl());
            pstmt.setLong(2, performanceResult.getDomReadyTime());
            pstmt.setLong(3, performanceResult.getPageLoadTime());
            pstmt.setString(4, performanceResult.getJsMetrics());
            pstmt.setBoolean(5, performanceResult.isPassed());
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            
            int rowsAffected = pstmt.executeUpdate();
            logger.debug("Performans sonucu kaydedildi: {} satır etkilendi", rowsAffected);
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            logger.error("Performans sonucu kaydetme hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Test sonuçlarını al
     * @param limit Sonuç sayısı sınırı
     * @return Test sonuçları listesi
     */
    public static List<TestResult> getTestResults(int limit) {
        List<TestResult> results = new ArrayList<>();
        
        if (connection == null) {
            logger.error("Veritabanı bağlantısı yok");
            return results;
        }
        
        String sql = "SELECT * FROM test_results ORDER BY created_at DESC LIMIT ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TestResult result = new TestResult(
                            rs.getString("test_name"),
                            rs.getString("test_class"),
                            rs.getString("status"),
                            rs.getLong("duration"),
                            rs.getString("error_message"),
                            rs.getString("screenshot_path")
                    );
                    results.add(result);
                }
            }
            
        } catch (SQLException e) {
            logger.error("Test sonuçları alma hatası: {}", e.getMessage());
        }
        
        return results;
    }
    
    /**
     * Test istatistiklerini al
     * @return Test istatistikleri
     */
    public static Map<String, Object> getTestStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        if (connection == null) {
            logger.error("Veritabanı bağlantısı yok");
            return statistics;
        }
        
        String sql = "SELECT " +
                     "COUNT(*) as total_tests, " +
                     "SUM(CASE WHEN status = 'PASS' THEN 1 ELSE 0 END) as passed_tests, " +
                     "SUM(CASE WHEN status = 'FAIL' THEN 1 ELSE 0 END) as failed_tests, " +
                     "AVG(duration) as avg_duration " +
                     "FROM test_results";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                statistics.put("totalTests", rs.getLong("total_tests"));
                statistics.put("passedTests", rs.getLong("passed_tests"));
                statistics.put("failedTests", rs.getLong("failed_tests"));
                statistics.put("avgDuration", rs.getDouble("avg_duration"));
                
                long totalTests = rs.getLong("total_tests");
                if (totalTests > 0) {
                    double successRate = (double) rs.getLong("passed_tests") / totalTests * 100;
                    statistics.put("successRate", successRate);
                }
            }
            
        } catch (SQLException e) {
            logger.error("Test istatistikleri alma hatası: {}", e.getMessage());
        }
        
        return statistics;
    }
    
    /**
     * Veritabanı tablolarını oluştur
     * @return Başarılı mı?
     */
    public static boolean createTables() {
        if (connection == null) {
            logger.error("Veritabanı bağlantısı yok");
            return false;
        }
        
        try {
            // Test sonuçları tablosu
            String createTestResultsTable = 
                "CREATE TABLE IF NOT EXISTS test_results (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "test_name VARCHAR(255) NOT NULL, " +
                "test_class VARCHAR(255) NOT NULL, " +
                "status VARCHAR(50) NOT NULL, " +
                "duration BIGINT, " +
                "error_message TEXT, " +
                "screenshot_path VARCHAR(500), " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            
            // Performans sonuçları tablosu
            String createPerformanceResultsTable = 
                "CREATE TABLE IF NOT EXISTS performance_results (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "url VARCHAR(500) NOT NULL, " +
                "dom_ready_time BIGINT, " +
                "page_load_time BIGINT, " +
                "js_metrics TEXT, " +
                "passed BOOLEAN, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(createTestResultsTable);
                stmt.execute(createPerformanceResultsTable);
                logger.info("Veritabanı tabloları oluşturuldu");
                return true;
            }
            
        } catch (SQLException e) {
            logger.error("Tablo oluşturma hatası: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Veritabanı bağlantısını kontrol et
     * @return Bağlantı aktif mi?
     */
    public static boolean isConnectionActive() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
