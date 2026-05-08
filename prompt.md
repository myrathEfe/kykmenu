Spring Boot ve Thymeleaf kullanarak katmanlı mimariye uygun bir web uygulaması geliştir.

Proje konusu: KYK Yemekhane Menü Takip Sistemi

Bu proje, öğrencilerin KYK yemekhanesindeki günlük menüleri görüntüleyebileceği; yetkili kullanıcıların menü kayıtlarını ekleyip güncelleyip silebileceği bir web uygulaması olacaktır.

Teknolojiler:
- Java 17 veya üzeri
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- MySQL veya PostgreSQL
- Bootstrap
- Maven
- Session tabanlı login sistemi

Katmanlı mimari zorunludur:
- Entity
- Repository
- Service
- Controller
- DTO kullanılabiliyorsa kullan
- Thymeleaf templates
- Static css/js dosyaları

Temel gereksinimler:

1. Login sistemi:
- Kullanıcı adı ve şifre ile giriş yapılmalı.
- Kullanıcı bilgileri veritabanında tutulmalı.
- Hatalı girişte kullanıcıya anlaşılır hata mesajı gösterilmeli.
- Başarılı girişten sonra session oluşturulmalı.
- Login olmayan kullanıcılar yönetim sayfalarına erişememeli.
- Logout işlemi olmalı.
- En az iki rol olsun:
  - ADMIN: Menü ekleme, silme, güncelleme yapabilir.
  - USER: Menüleri sadece görüntüleyebilir.

2. Menü CRUD işlemleri:
Aşağıdaki alanlara sahip Menu entity oluştur:
- id
- tarih
- ogunTuru: Sabah / Öğle / Akşam
- corba
- anaYemek
- yardimciYemek
- tatliVeyaIcecek
- kalori
- aciklama
- imageData: byte[]
- imageContentType: String

CRUD işlemleri:
- Menü ekleme
- Menü listeleme
- Menü detay görüntüleme
- Menü güncelleme
- Menü silme

3. Resim yükleme:
- Menü eklerken veya güncellerken yemek görseli yüklenebilmeli.
- Resim dosya sisteminde değil, doğrudan veritabanında BLOB olarak saklanmalı.
- Entity içinde byte[] imageData kullanılmalı.
- Resim arayüzde görüntülenebilmeli.
- Bunun için özel bir endpoint yaz:
  GET /menus/image/{id}
- Bu endpoint veritabanındaki binary veriyi okuyup uygun content-type ile dönmeli.

4. Arama sistemi:
Kullanıcılar menüler üzerinde arama yapabilmeli.
Arama alanları:
- Tarihe göre arama
- Öğün türüne göre arama
- Ana yemek adına göre arama
- Kalori aralığına göre arama

Arama dinamik ve kullanıcı dostu olsun.
Listeleme sayfasında filtre formu bulunsun.
Arama sonuçları tablo veya kart yapısında gösterilsin.

5. Thymeleaf arayüz:
Aşağıdaki sayfaları oluştur:
- login.html
- dashboard.html
- menu-list.html
- menu-detail.html
- menu-form.html
- error.html
- fragments/header.html
- fragments/navbar.html

Arayüz sade ve öğrenci projesine uygun olsun.
Bootstrap kullan.
Form validasyon mesajları gösterilsin.
Boş alanlar için kullanıcı uyarı alsın.

6. Validasyon:
- Tarih boş olamaz.
- Öğün türü boş olamaz.
- Ana yemek boş olamaz.
- Kalori negatif olamaz.
- Resim opsiyonel olabilir ama varsa image/jpeg veya image/png olmalı.

7. Veritabanı:
- MySQL veya PostgreSQL kullanılabilir.
- application.properties dosyasını hazırla.
- Gerekli entity ilişkilerini kur.
- Başlangıç için örnek admin ve user verisi eklenebilecek bir data initializer yaz.
- Örnek kullanıcılar:
  admin / admin123
  user / user123

8. Güvenlik:
Spring Security kullanmak zorunlu değil. Basit session tabanlı authentication yapılabilir.
Ancak kod temiz ve anlaşılır olmalı.
Login kontrolü için interceptor veya controller seviyesinde kontrol yapılabilir.

9. Kod kalitesi:
- Controller içinde iş mantığı yazma.
- İş mantığı Service katmanında olsun.
- Repository sadece veritabanı işlemleri için kullanılsın.
- Entity sınıfları temiz olsun.
- Gereksiz karmaşık yapı kurma.
- Öğrenci projesi seviyesinde ama düzenli ve sürdürülebilir olsun.

10. Teslim edilecek çıktı:
Bana tüm proje dosya yapısını oluştur.
Her dosyanın içeriğini eksiksiz ver.
Proje çalıştırma adımlarını da yaz:
- Veritabanı oluşturma
- application.properties ayarları
- mvn spring-boot:run komutu
- Tarayıcıdan giriş adresi

Proje adı:
kyk-menu-system

Kodları eksiksiz, çalıştırılabilir ve açıklamalı şekilde oluştur.
