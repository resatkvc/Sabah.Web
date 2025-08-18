package sabah.com.performance;

/**
 * Yük test sonuçlarını temsil eden sınıf
 */
public class LoadTestResult {
    
    private final long totalRequests;
    private final long successfulRequests;
    private final long failedRequests;
    private final double avgResponseTime;
    private final double successRate;
    private final boolean passed;
    
    /**
     * Constructor
     * @param totalRequests Toplam istek sayısı
     * @param successfulRequests Başarılı istek sayısı
     * @param failedRequests Başarısız istek sayısı
     * @param avgResponseTime Ortalama yanıt süresi (ms)
     * @param successRate Başarı oranı (0.0 - 1.0)
     * @param passed Test geçti mi?
     */
    public LoadTestResult(long totalRequests, long successfulRequests, long failedRequests, 
                         double avgResponseTime, double successRate, boolean passed) {
        this.totalRequests = totalRequests;
        this.successfulRequests = successfulRequests;
        this.failedRequests = failedRequests;
        this.avgResponseTime = avgResponseTime;
        this.successRate = successRate;
        this.passed = passed;
    }
    
    /**
     * Toplam istek sayısını al
     * @return Toplam istek sayısı
     */
    public long getTotalRequests() {
        return totalRequests;
    }
    
    /**
     * Başarılı istek sayısını al
     * @return Başarılı istek sayısı
     */
    public long getSuccessfulRequests() {
        return successfulRequests;
    }
    
    /**
     * Başarısız istek sayısını al
     * @return Başarısız istek sayısı
     */
    public long getFailedRequests() {
        return failedRequests;
    }
    
    /**
     * Ortalama yanıt süresini al
     * @return Ortalama yanıt süresi (ms)
     */
    public double getAvgResponseTime() {
        return avgResponseTime;
    }
    
    /**
     * Başarı oranını al
     * @return Başarı oranı (0.0 - 1.0)
     */
    public double getSuccessRate() {
        return successRate;
    }
    
    /**
     * Test geçti mi?
     * @return Geçti mi?
     */
    public boolean isPassed() {
        return passed;
    }
    
    /**
     * Başarı oranını yüzde olarak al
     * @return Başarı oranı (%)
     */
    public double getSuccessRatePercentage() {
        return successRate * 100;
    }
    
    /**
     * Ortalama yanıt süresini saniye cinsinden al
     * @return Ortalama yanıt süresi (saniye)
     */
    public double getAvgResponseTimeSeconds() {
        return avgResponseTime / 1000.0;
    }
    
    /**
     * Hata oranını al
     * @return Hata oranı (0.0 - 1.0)
     */
    public double getErrorRate() {
        return totalRequests > 0 ? (double) failedRequests / totalRequests : 0.0;
    }
    
    /**
     * Hata oranını yüzde olarak al
     * @return Hata oranı (%)
     */
    public double getErrorRatePercentage() {
        return getErrorRate() * 100;
    }
    
    @Override
    public String toString() {
        return String.format("LoadTestResult{total=%d, success=%d, failed=%d, avgTime=%.2fms, successRate=%.2f%%, passed=%s}", 
                totalRequests, successfulRequests, failedRequests, avgResponseTime, getSuccessRatePercentage(), passed);
    }
}
