package sabah.com.performance;

/**
 * Performans test sonuçlarını temsil eden sınıf
 */
public class PerformanceResult {
    
    private final String url;
    private final long domReadyTime;
    private final long pageLoadTime;
    private final String jsMetrics;
    private final boolean passed;
    
    /**
     * Constructor
     * @param url Test edilen URL
     * @param domReadyTime DOM Ready süresi (ms)
     * @param pageLoadTime Sayfa yükleme süresi (ms)
     * @param jsMetrics JavaScript metrikleri
     * @param passed Test geçti mi?
     */
    public PerformanceResult(String url, long domReadyTime, long pageLoadTime, String jsMetrics, boolean passed) {
        this.url = url;
        this.domReadyTime = domReadyTime;
        this.pageLoadTime = pageLoadTime;
        this.jsMetrics = jsMetrics;
        this.passed = passed;
    }
    
    /**
     * Test edilen URL'i al
     * @return URL
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * DOM Ready süresini al
     * @return DOM Ready süresi (ms)
     */
    public long getDomReadyTime() {
        return domReadyTime;
    }
    
    /**
     * Sayfa yükleme süresini al
     * @return Sayfa yükleme süresi (ms)
     */
    public long getPageLoadTime() {
        return pageLoadTime;
    }
    
    /**
     * JavaScript metriklerini al
     * @return JavaScript metrikleri
     */
    public String getJsMetrics() {
        return jsMetrics;
    }
    
    /**
     * Test geçti mi?
     * @return Geçti mi?
     */
    public boolean isPassed() {
        return passed;
    }
    
    /**
     * DOM Ready süresini saniye cinsinden al
     * @return DOM Ready süresi (saniye)
     */
    public double getDomReadyTimeSeconds() {
        return domReadyTime / 1000.0;
    }
    
    /**
     * Sayfa yükleme süresini saniye cinsinden al
     * @return Sayfa yükleme süresi (saniye)
     */
    public double getPageLoadTimeSeconds() {
        return pageLoadTime / 1000.0;
    }
    
    @Override
    public String toString() {
        return String.format("PerformanceResult{url='%s', domReady=%dms, pageLoad=%dms, passed=%s}", 
                url, domReadyTime, pageLoadTime, passed);
    }
}
