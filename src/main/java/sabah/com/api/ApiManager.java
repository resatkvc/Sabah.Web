package sabah.com.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sabah.com.config.ConfigReader;
import sabah.com.data.TestDataManager;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.HashMap;

/**
 * API testleri için yönetici sınıf
 * HTTP istekleri ve API entegrasyonları için kullanılır
 */
public class ApiManager {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiManager.class);
    private static HttpClient httpClient;
    private static final String API_BASE_URL = TestDataManager.getTestData("api.base.url");
    private static final int API_TIMEOUT = Integer.parseInt(TestDataManager.getTestData("api.timeout", "5000"));
    private static final int RETRY_COUNT = Integer.parseInt(TestDataManager.getTestData("api.retry.count", "3"));
    
    static {
        initializeHttpClient();
    }
    
    /**
     * HTTP Client'ı başlat
     */
    private static void initializeHttpClient() {
        httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(API_TIMEOUT))
                .build();
        logger.info("HTTP Client başlatıldı - Timeout: {}ms", API_TIMEOUT);
    }
    
    /**
     * GET isteği gönder
     * @param endpoint API endpoint'i
     * @return ApiResponse nesnesi
     */
    public static ApiResponse get(String endpoint) {
        return sendRequest("GET", endpoint, null, null);
    }
    
    /**
     * GET isteği gönder (header'larla)
     * @param endpoint API endpoint'i
     * @param headers Header'lar
     * @return ApiResponse nesnesi
     */
    public static ApiResponse get(String endpoint, Map<String, String> headers) {
        return sendRequest("GET", endpoint, null, headers);
    }
    
    /**
     * POST isteği gönder
     * @param endpoint API endpoint'i
     * @param body Request body
     * @return ApiResponse nesnesi
     */
    public static ApiResponse post(String endpoint, String body) {
        return sendRequest("POST", endpoint, body, null);
    }
    
    /**
     * POST isteği gönder (header'larla)
     * @param endpoint API endpoint'i
     * @param body Request body
     * @param headers Header'lar
     * @return ApiResponse nesnesi
     */
    public static ApiResponse post(String endpoint, String body, Map<String, String> headers) {
        return sendRequest("POST", endpoint, body, headers);
    }
    
    /**
     * PUT isteği gönder
     * @param endpoint API endpoint'i
     * @param body Request body
     * @return ApiResponse nesnesi
     */
    public static ApiResponse put(String endpoint, String body) {
        return sendRequest("PUT", endpoint, body, null);
    }
    
    /**
     * DELETE isteği gönder
     * @param endpoint API endpoint'i
     * @return ApiResponse nesnesi
     */
    public static ApiResponse delete(String endpoint) {
        return sendRequest("DELETE", endpoint, null, null);
    }
    
    /**
     * HTTP isteği gönder
     * @param method HTTP metodu
     * @param endpoint API endpoint'i
     * @param body Request body
     * @param headers Header'lar
     * @return ApiResponse nesnesi
     */
    private static ApiResponse sendRequest(String method, String endpoint, String body, Map<String, String> headers) {
        String fullUrl = API_BASE_URL + endpoint;
        logger.debug("API isteği gönderiliyor: {} {}", method, fullUrl);
        
        for (int attempt = 1; attempt <= RETRY_COUNT; attempt++) {
            try {
                HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                        .uri(URI.create(fullUrl))
                        .timeout(Duration.ofMillis(API_TIMEOUT));
                
                // Header'ları ekle
                if (headers != null) {
                    headers.forEach(requestBuilder::header);
                }
                
                // Method ve body'yi ayarla
                HttpRequest request;
                switch (method.toUpperCase()) {
                    case "GET":
                        request = requestBuilder.GET().build();
                        break;
                    case "POST":
                        request = requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body != null ? body : "")).build();
                        break;
                    case "PUT":
                        request = requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(body != null ? body : "")).build();
                        break;
                    case "DELETE":
                        request = requestBuilder.DELETE().build();
                        break;
                    default:
                        throw new IllegalArgumentException("Desteklenmeyen HTTP metodu: " + method);
                }
                
                // İsteği gönder
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                
                // Headers'ı Map<String, String> formatına dönüştür
                Map<String, String> headersMap = new HashMap<>();
                response.headers().map().forEach((key, values) -> {
                    if (!values.isEmpty()) {
                        headersMap.put(key, values.get(0)); // İlk değeri al
                    }
                });
                
                ApiResponse apiResponse = new ApiResponse(
                        response.statusCode(),
                        response.body(),
                        headersMap
                );
                
                logger.debug("API yanıtı alındı: Status={}, Body length={}", 
                        apiResponse.getStatusCode(), 
                        apiResponse.getBody().length());
                
                return apiResponse;
                
            } catch (IOException | InterruptedException e) {
                logger.warn("API isteği başarısız (Deneme {}/{}): {}", attempt, RETRY_COUNT, e.getMessage());
                
                if (attempt == RETRY_COUNT) {
                    logger.error("API isteği {} deneme sonrası başarısız: {}", RETRY_COUNT, e.getMessage());
                    return new ApiResponse(0, "", new HashMap<>());
                }
                
                // Kısa bekleme sonra tekrar dene
                try {
                    Thread.sleep(1000 * attempt);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        
        return new ApiResponse(0, "", new HashMap<>());
    }
    
    /**
     * API sağlık kontrolü
     * @return API çalışıyor mu?
     */
    public static boolean isApiHealthy() {
        ApiResponse response = get("/health");
        return response.getStatusCode() == 200;
    }
    
    /**
     * Haber API'sinden veri al
     * @param category Haber kategorisi
     * @return API yanıtı
     */
    public static ApiResponse getNewsByCategory(String category) {
        return get("/news/category/" + category);
    }
    
    /**
     * Arama API'si
     * @param keyword Arama anahtar kelimesi
     * @return API yanıtı
     */
    public static ApiResponse searchNews(String keyword) {
        return get("/news/search?q=" + keyword);
    }
    
    /**
     * Borsa verilerini al
     * @return API yanıtı
     */
    public static ApiResponse getBorsaData() {
        return get("/borsa/live");
    }
    
    /**
     * Hava durumu verilerini al
     * @param city Şehir adı
     * @return API yanıtı
     */
    public static ApiResponse getWeatherData(String city) {
        return get("/weather/" + city);
    }
    
    /**
     * HTTP Client'ı yeniden başlat
     */
    public static void restartHttpClient() {
        initializeHttpClient();
    }
}
