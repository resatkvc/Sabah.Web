# Sabah.com Web Otomasyon Test Projesi

## 📋 Proje Hakkında

Bu proje, Sabah.com web sitesinin otomatik test senaryolarını içeren kapsamlı bir web otomasyon test projesidir. Selenium WebDriver, TestNG ve Allure framework'leri kullanılarak geliştirilmiştir.

## 🚀 Teknolojiler

- **Java 21** - Programlama dili
- **Maven** - Proje yönetimi ve bağımlılık yönetimi
- **Selenium WebDriver 4.18.1** - Web otomasyon framework'ü
- **WebDriverManager 5.7.0** - Driver yönetimi
- **TestNG** - Test framework'ü
- **Allure** - Test raporlama
- **SLF4J & Logback** - Loglama
- **Apache Commons Configuration** - Konfigürasyon yönetimi

## 📁 Proje Yapısı

```
Sabah.Web/
├── src/
│   ├── main/
│   │   ├── java/sabah/com/
│   │   │   ├── base/          # Temel test sınıfları
│   │   │   ├── components/    # Yeniden kullanılabilir bileşenler
│   │   │   ├── config/        # Konfigürasyon dosyaları
│   │   │   ├── pages/         # Page Object Model sınıfları
│   │   │   └── utils/         # Yardımcı sınıflar
│   │   └── resources/         # Kaynak dosyaları
│   └── test/
│       ├── java/sabah/com/
│       │   └── tests/         # Test sınıfları
│       └── resources/         # Test kaynakları
├── target/                    # Derleme çıktıları
├── logs/                      # Log dosyaları
├── pom.xml                    # Maven konfigürasyonu
├── testng.xml                 # TestNG konfigürasyonu
└── README.md                  # Bu dosya
```

## 🛠️ Kurulum

### Gereksinimler

- Java 21 veya üzeri
- Maven 3.6 veya üzeri
- Git

### Adımlar

1. **Projeyi klonlayın:**
   ```bash
   git clone [repository-url]
   cd Sabah.Web
   ```

2. **Bağımlılıkları yükleyin:**
   ```bash
   mvn clean install
   ```

3. **WebDriver'ları otomatik olarak yükleyin:**
   ```bash
   # WebDriverManager otomatik olarak gerekli driver'ları indirecektir
   mvn test -Dtest=HomePageTest#testHomePageLoading
   ```

## 🧪 Testleri Çalıştırma

### Tüm Testleri Çalıştırma
```bash
mvn clean test
```

### Belirli Test Gruplarını Çalıştırma

**Smoke Testleri:**
```bash
mvn test -Dgroups=smoke
```

**Regresyon Testleri:**
```bash
mvn test -Dgroups=regression
```

### Belirli Test Sınıfını Çalıştırma
```bash
mvn test -Dtest=HomePageTest
```

### TestNG XML Dosyası ile Çalıştırma
```bash
mvn test -DsuiteXmlFile=testng.xml
```

## 📊 Test Raporları

### Allure Raporu Oluşturma
```bash
mvn allure:report
```

### Allure Raporu Görüntüleme
```bash
mvn allure:serve
```

### HTML Raporu Oluşturma
```bash
mvn test allure:report
```

## 🔧 Konfigürasyon

### TestNG Konfigürasyonu (`testng.xml`)

- **Tarayıcı:** Chrome (varsayılan)
- **Headless Mode:** false (varsayılan)
- **Paralel Çalışma:** Aktif (thread-count=1)
- **Test Grupları:** smoke, regression

### Parametreler

Test çalıştırırken aşağıdaki parametreleri kullanabilirsiniz:

```bash
# Farklı tarayıcı ile çalıştırma
mvn test -Dbrowser=firefox

# Headless modda çalıştırma
mvn test -Dheadless=true

# Paralel thread sayısını değiştirme
mvn test -DthreadCount=2
```

## 📝 Test Senaryoları

### Ana Sayfa Testleri (`HomePageTest`)

- ✅ Ana sayfa yükleme kontrolü
- ✅ Header elementlerinin görünürlüğü
- ✅ Hamburger menü işlevselliği
- ✅ Logo tıklama testi
- ✅ Sayfa başlığı doğrulama
- ✅ Arama fonksiyonu testi
- ✅ Ana menü kategorileri testi
- ✅ Sosyal medya linkleri testi
- ✅ Sayfa scroll testi
- ✅ Sayfa yenileme testi
- ✅ Sayfa element sayıları testi
- ✅ Borsa verileri testi
- ✅ Hava durumu testi

### Test Grupları

- **Smoke Tests:** Kritik işlevsellik testleri
- **Regression Tests:** Kapsamlı regresyon testleri

## 🐛 Hata Ayıklama

### Log Dosyaları

Test çalıştırma sırasında oluşan loglar `logs/` dizininde saklanır.

### Screenshot'lar

Test başarısız olduğunda otomatik olarak ekran görüntüsü alınır ve Allure raporuna eklenir.

### Debug Modu

Debug modunda çalıştırmak için:
```bash
mvn test -Ddebug=true
```

## 📈 CI/CD Entegrasyonu

### GitHub Actions

Proje GitHub Actions ile entegre edilebilir. Örnek workflow:

```yaml
name: Sabah.com Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3
    - name: Set up JDK 21
      uses: actions/setup-java@v3
      with:
        java-version: '21'
    - name: Run Tests
      run: mvn clean test
    - name: Generate Allure Report
      run: mvn allure:report
```

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/AmazingFeature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add some AmazingFeature'`)
4. Branch'inizi push edin (`git push origin feature/AmazingFeature`)
5. Pull Request oluşturun

## 📋 Test Yazma Kuralları

### Naming Convention

- Test sınıfları: `*Test.java`
- Test metodları: `test*` ile başlamalı
- Page sınıfları: `*Page.java`
- Component sınıfları: `*Component.java`

### Test Anotasyonları

```java
@Test(priority = 1, groups = {"smoke", "regression"})
@Severity(SeverityLevel.CRITICAL)
@Story("Test Hikayesi")
@Description("Test açıklaması")
```

### Assertion Kullanımı

```java
Assert.assertTrue(condition, "Hata mesajı");
Assert.assertEquals(actual, expected, "Hata mesajı");
```

## 🔒 Güvenlik

- Hassas bilgiler (API anahtarları, şifreler) environment variable olarak saklanmalı
- Test verileri production verilerinden ayrı tutulmalı
- Test ortamı production ortamından izole edilmeli

## 📞 İletişim

Proje ile ilgili sorularınız için:
- **E-posta:** [your-email@domain.com]
- **GitHub Issues:** [repository-issues-url]

## 📄 Lisans

Bu proje [MIT License](LICENSE) altında lisanslanmıştır.

---

## 🎯 Gelecek Planları

- [ ] API testleri ekleme
- [ ] Mobile test desteği
- [ ] Performance testleri
- [ ] Cross-browser testleri genişletme
- [ ] Docker container desteği
- [ ] Cloud test execution (Sauce Labs, BrowserStack)

## 📚 Faydalı Linkler

- [Selenium WebDriver Documentation](https://www.selenium.dev/documentation/webdriver/)
- [WebDriverManager Documentation](https://github.com/bonigarcia/webdrivermanager)
- [TestNG Documentation](https://testng.org/doc/)
- [Allure Framework](https://docs.qameta.io/allure/)
- [Maven Documentation](https://maven.apache.org/guides/)

---

**Not:** Bu README dosyası proje tamamlandığında güncellenecektir.
