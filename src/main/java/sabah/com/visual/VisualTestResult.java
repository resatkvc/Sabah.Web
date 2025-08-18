package sabah.com.visual;

/**
 * Görsel test sonuçlarını temsil eden sınıf
 */
public class VisualTestResult {
    
    private final boolean passed;
    private final double similarity;
    private final String message;
    private final String currentScreenshotPath;
    private final String baselineScreenshotPath;
    
    /**
     * Constructor
     * @param passed Test geçti mi?
     * @param similarity Benzerlik oranı
     * @param message Sonuç mesajı
     * @param currentScreenshotPath Mevcut screenshot yolu
     * @param baselineScreenshotPath Baseline screenshot yolu
     */
    public VisualTestResult(boolean passed, double similarity, String message, 
                           String currentScreenshotPath, String baselineScreenshotPath) {
        this.passed = passed;
        this.similarity = similarity;
        this.message = message;
        this.currentScreenshotPath = currentScreenshotPath;
        this.baselineScreenshotPath = baselineScreenshotPath;
    }
    
    /**
     * Test geçti mi?
     * @return Geçti mi?
     */
    public boolean isPassed() {
        return passed;
    }
    
    /**
     * Benzerlik oranını al
     * @return Benzerlik oranı (0.0 - 1.0)
     */
    public double getSimilarity() {
        return similarity;
    }
    
    /**
     * Sonuç mesajını al
     * @return Mesaj
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Mevcut screenshot yolunu al
     * @return Dosya yolu
     */
    public String getCurrentScreenshotPath() {
        return currentScreenshotPath;
    }
    
    /**
     * Baseline screenshot yolunu al
     * @return Dosya yolu
     */
    public String getBaselineScreenshotPath() {
        return baselineScreenshotPath;
    }
    
    /**
     * Benzerlik yüzdesini al
     * @return Yüzde
     */
    public double getSimilarityPercentage() {
        return similarity * 100;
    }
    
    @Override
    public String toString() {
        return String.format("VisualTestResult{passed=%s, similarity=%.2f, message='%s'}", 
                passed, similarity, message);
    }
}
