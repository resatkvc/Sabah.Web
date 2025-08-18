package sabah.com.api;

import java.util.Map;

/**
 * API yanıtlarını temsil eden sınıf
 */
public class ApiResponse {
    
    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;
    private final long responseTime;
    
    /**
     * Constructor
     * @param statusCode HTTP durum kodu
     * @param body Yanıt gövdesi
     * @param headers Yanıt header'ları
     */
    public ApiResponse(int statusCode, String body, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
        this.responseTime = System.currentTimeMillis();
    }
    
    /**
     * HTTP durum kodunu al
     * @return Durum kodu
     */
    public int getStatusCode() {
        return statusCode;
    }
    
    /**
     * Yanıt gövdesini al
     * @return Yanıt gövdesi
     */
    public String getBody() {
        return body;
    }
    
    /**
     * Yanıt header'larını al
     * @return Header'lar
     */
    public Map<String, String> getHeaders() {
        return headers;
    }
    
    /**
     * Yanıt süresini al
     * @return Yanıt süresi (ms)
     */
    public long getResponseTime() {
        return responseTime;
    }
    
    /**
     * Başarılı yanıt mı?
     * @return Başarılı mı?
     */
    public boolean isSuccess() {
        return statusCode >= 200 && statusCode < 300;
    }
    
    /**
     * Belirli bir header'ı al
     * @param headerName Header adı
     * @return Header değeri
     */
    public String getHeader(String headerName) {
        return headers.get(headerName);
    }
    
    /**
     * JSON yanıtını parse et
     * @return JSON string
     */
    public String getJsonBody() {
        return body;
    }
    
    /**
     * Yanıtın boş olup olmadığını kontrol et
     * @return Boş mu?
     */
    public boolean isEmpty() {
        return body == null || body.trim().isEmpty();
    }
    
    @Override
    public String toString() {
        return String.format("ApiResponse{statusCode=%d, bodyLength=%d, headers=%d}", 
                statusCode, body != null ? body.length() : 0, headers.size());
    }
}
