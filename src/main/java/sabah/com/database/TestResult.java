package sabah.com.database;

/**
 * Test sonuçlarını temsil eden sınıf
 */
public class TestResult {
    
    private final String testName;
    private final String testClass;
    private final String status;
    private final long duration;
    private final String errorMessage;
    private final String screenshotPath;
    
    /**
     * Constructor
     * @param testName Test adı
     * @param testClass Test sınıfı
     * @param status Test durumu (PASS/FAIL)
     * @param duration Test süresi (ms)
     * @param errorMessage Hata mesajı
     * @param screenshotPath Screenshot yolu
     */
    public TestResult(String testName, String testClass, String status, 
                     long duration, String errorMessage, String screenshotPath) {
        this.testName = testName;
        this.testClass = testClass;
        this.status = status;
        this.duration = duration;
        this.errorMessage = errorMessage;
        this.screenshotPath = screenshotPath;
    }
    
    /**
     * Test adını al
     * @return Test adı
     */
    public String getTestName() {
        return testName;
    }
    
    /**
     * Test sınıfını al
     * @return Test sınıfı
     */
    public String getTestClass() {
        return testClass;
    }
    
    /**
     * Test durumunu al
     * @return Test durumu
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * Test süresini al
     * @return Test süresi (ms)
     */
    public long getDuration() {
        return duration;
    }
    
    /**
     * Hata mesajını al
     * @return Hata mesajı
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    
    /**
     * Screenshot yolunu al
     * @return Screenshot yolu
     */
    public String getScreenshotPath() {
        return screenshotPath;
    }
    
    /**
     * Test geçti mi?
     * @return Geçti mi?
     */
    public boolean isPassed() {
        return "PASS".equalsIgnoreCase(status);
    }
    
    /**
     * Test süresini saniye cinsinden al
     * @return Test süresi (saniye)
     */
    public double getDurationSeconds() {
        return duration / 1000.0;
    }
    
    @Override
    public String toString() {
        return String.format("TestResult{name='%s', class='%s', status='%s', duration=%dms, passed=%s}", 
                testName, testClass, status, duration, isPassed());
    }
}
