Bu Spring Boot + Thymeleaf projesini sunumda anlatabilmem için bana öğretmen gibi açıkla.

Kodda değişiklik yapma. Sadece projeyi analiz et ve açıklama dokümanı hazırla.

İstediğim açıklama seviyesi:
- Çok teknik ve akademik olmasın.
- Öğrenci sunumunda anlatabileceğim kadar net olsun.
- Kısa ama anlaşılır olsun.
- Ezber cümle değil, gerçekten kodun ne yaptığını açıklasın.

Aşağıdaki başlıklara göre açıkla:

1. Projenin genel amacı
- Bu uygulama ne yapıyor?
- Kullanıcı sistemde neler yapabiliyor?
- Admin kullanıcı neler yapabiliyor?
- Veritabanı ne için kullanılıyor?

2. Proje klasör yapısı
Her klasörü tek tek açıkla:
- config
- controller
- dto
- entity
- exception
- repository
- service
- resources
- templates
- static/css
- static/js

Her biri için:
- Bu klasörün görevi nedir?
- İçindeki dosyalar genel olarak ne işe yarıyor?
- Proje çalışırken bu klasör hangi aşamada devreye giriyor?

3. Katmanlı mimari nasıl çalışıyor?
Şu akışı sade şekilde anlat:
Controller → Service → Repository → Database

Örnek olarak:
- Menü listeleme işlemi nasıl çalışıyor?
- Menü ekleme işlemi nasıl çalışıyor?
- Login işlemi nasıl çalışıyor?
- Resim yükleme ve veritabanından görüntüleme işlemi nasıl çalışıyor?

4. Annotation açıklamaları
Projede geçen annotation’ları bul ve açıkla.

Örnek olarak şunlar varsa açıkla:
- @SpringBootApplication
- @Controller
- @Service
- @Repository
- @Entity
- @Table
- @Id
- @GeneratedValue
- @Column
- @Lob
- @Enumerated
- @Autowired veya constructor injection
- @GetMapping
- @PostMapping
- @PathVariable
- @RequestParam
- @ModelAttribute
- @Valid
- @NotBlank
- @NotNull
- @Size
- @Min

Her annotation için:
- Ne işe yarıyor?
- Bu projede nerede kullanılmış?
- Basit bir cümleyle sunumda nasıl anlatılır?

5. Sınıflar ne işe yarıyor?
Projede bulunan önemli sınıfları tek tek açıkla.

Özellikle:
- Application main class
- Entity sınıfları
- Repository interface’leri
- Service sınıfları
- Controller sınıfları
- DTO sınıfları
- Config sınıfları

Her sınıf için:
- Görevi nedir?
- İçindeki önemli metotlar ne yapar?
- Diğer katmanlarla nasıl iletişim kurar?

6. Fonksiyon/metot açıklamaları
Önemli metotları kısa kısa açıkla.

Her metot için:
- Metot adı
- Hangi sınıfta?
- Ne işe yarıyor?
- Hangi sayfa veya işlem için kullanılıyor?
- Veritabanına gidiyor mu, gitmiyor mu?

7. Thymeleaf sayfaları
templates klasöründeki HTML dosyalarını açıkla.

Her sayfa için:
- Hangi URL ile açılıyor?
- Kullanıcı bu sayfada ne yapıyor?
- Controller’dan hangi veriler geliyor?
- Form varsa hangi endpoint’e post ediyor?

8. Login ve session sistemi
Login akışını özellikle açıkla:
- Kullanıcı giriş yapınca ne oluyor?
- Şifre kontrolü nerede yapılıyor?
- Session’a hangi bilgiler yazılıyor?
- Login olmayan kullanıcı nasıl engelleniyor?
- Logout nasıl çalışıyor?

9. Resimlerin BLOB olarak saklanması
Bu kısmı sunumda anlatabileceğim şekilde açıkla:
- Resim dosya olarak değil veritabanında nasıl tutuluyor?
- image_data ve image_content_type ne işe yarıyor?
- Resim yüklenirken ne oluyor?
- Resim ekranda gösterilirken hangi endpoint çalışıyor?
- Neden dosya yolu yerine BLOB kullanılmış olabilir?

10. Veritabanı tabloları
Entity’lerden yola çıkarak tabloları açıkla:
- users tablosu
- menus tablosu

Her tablo için:
- Hangi alanlar var?
- Bu alanlar ne işe yarıyor?
- İlişki varsa belirt.

11. Sunumda anlatabileceğim kısa proje akışı
Bana 1-2 dakikada anlatabileceğim kısa bir konuşma metni hazırla.

Örnek tarz:
“Bu projede Spring Boot ve Thymeleaf kullanarak KYK menü takip sistemi geliştirdim. Projede katmanlı mimari kullandım...”

12. Hocadan gelebilecek sorular
Bu projeyle ilgili hocanın sorabileceği 15 soru ve kısa cevaplarını hazırla.

Özellikle şu konular olsun:
- Neden katmanlı mimari?
- Controller ile Service farkı nedir?
- Repository ne yapıyor?
- Thymeleaf ne işe yarıyor?
- BLOB nedir?
- Session nedir?
- Annotation nedir?
- Veritabanı bağlantısı nerede?
- CRUD işlemleri nasıl yapılıyor?

Çıktı formatı:
- Markdown formatında yaz.
- Başlıkları düzenli kullan.
- Çok uzun paragraflar yazma.
- Kopyalayıp çalışabileceğim/sunumda kullanabileceğim kadar net olsun.
- Kodda değişiklik yapma.
