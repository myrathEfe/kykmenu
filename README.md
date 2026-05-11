# KYK Menü Sistemi Sunum Notları

## 1. Projenin genel amacı
- Bu uygulama, KYK yemekhanesindeki günlük menüleri takip etmek için yapılmış bir web uygulamasıdır.
- Normal kullanıcı menüleri görüntüleyebilir, filtreleyebilir ve detayına bakabilir.
- `ADMIN` rolündeki kullanıcı yeni menü ekleyebilir, mevcut menüyü güncelleyebilir ve silebilir.
- Veritabanı; kullanıcı bilgilerini, menü kayıtlarını ve menü görsellerini kalıcı olarak saklamak için kullanılır.
- Kısaca: “Öğrenci menüyü görür, admin menüyü yönetir.”

## 2. Proje klasör yapısı
- `config`: Uygulamanın kuralları burada tanımlanır. Session kontrolü, interceptor, ortak model verileri, örnek veri yükleme ve bean tanımları burada devreye girer.
- `controller`: Tarayıcıdan gelen istekleri karşılar. Hangi URL açılacak, hangi sayfa dönecek, hangi servis çağrılacak burada belirlenir.
- `dto`: Formlardan gelen verileri taşır. Özellikle login formu, menü formu ve arama kriterleri burada tutulur.
- `entity`: Veritabanı tablolarının Java karşılığıdır. `users` ve `menus` tablosu bu sınıflarla temsil edilir.
- `exception`: Hata olunca kullanıcıya gösterilecek özel hata akışı burada yönetilir.
- `repository`: Veritabanı ile konuşan katmandır. Kayıt bulma, kaydetme, silme gibi işlemleri yapar.
- `service`: İş mantığının merkezidir. Controller ile repository arasındaki ana kararlar burada verilir.
- `resources`: Uygulamanın ayar, HTML ve statik dosyaları buradadır.
- `templates`: Thymeleaf HTML sayfalarıdır. Kullanıcı ekranda bunları görür.
- `static/css`: Sayfaların görünüşünü ayarlayan CSS dosyası burada.
- `static/js`: Tema toggle gibi küçük arayüz davranışları burada.

## 3. Katmanlı mimari nasıl çalışıyor?
- Akış şu şekildedir: `Controller -> Service -> Repository -> Database`
- Controller kullanıcı isteğini alır.
- Service bu isteğin ne anlama geldiğini işler.
- Repository veritabanına gidip gerekli veriyi çeker ya da yazar.
- Sonuç tekrar controller’a, sonra ekrana döner.

### Menü listeleme örneği
- Kullanıcı `/menus` sayfasını açar.
- `MenuController.listMenus()` çalışır.
- Arama kriterleri `MenuSearchCriteria` içine alınır.
- `MenuService.searchMenus()` filtreleri hazırlar.
- `MenuRepository` veritabanından uygun kayıtları getirir.
- Sonuç `menu-list.html` sayfasına gönderilir.

### Menü ekleme örneği
- Admin formu doldurup gönderir.
- `MenuController.createMenu()` çalışır.
- Form verisi `MenuForm` içine alınır ve doğrulanır.
- `MenuService.create()` bir `Menu` nesnesi oluşturur.
- Repository ile veritabanına kaydedilir.
- Sonra kullanıcı menü listesine yönlendirilir.

### Login örneği
- Kullanıcı `/login` formunu gönderir.
- `AuthController.login()` çalışır.
- `AuthService.authenticate()` kullanıcıyı bulur ve şifreyi kontrol eder.
- Doğruysa session’a kullanıcı bilgileri yazılır.
- Sonra `/dashboard` sayfasına yönlendirilir.

### Resim yükleme ve gösterme örneği
- Admin formdan resim yükler.
- `MenuForm` içinde `MultipartFile imageFile` gelir.
- `MenuService.mapFormToEntity()` dosyayı `byte[] imageData` ve `imageContentType` olarak menüye koyar.
- Menü veritabanına kaydedilir.
- Görüntülemek için `/menus/image/{id}` endpoint’i çalışır.
- `MenuService.getMenuImage()` veritabanındaki binary veriyi okuyup `ResponseEntity<byte[]>` döner.

## 4. Annotation açıklamaları
- `@SpringBootApplication`: Uygulamayı başlatır. `KykMenuSystemApplication` içinde kullanılmış. Sunum cümlesi: “Bu annotation Spring Boot uygulamasının başlangıç noktasıdır.”
- `@Controller`: Sınıfın web isteği karşıladığını söyler. `AuthController`, `MenuController`, `DashboardController`, `HomeController` içinde var.
- `@Service`: İş mantığını taşıyan sınıfları belirtir. `AuthService` ve `MenuService` içinde var.
- `@Entity`: Sınıfın veritabanı tablosu olduğunu söyler. `User` ve `Menu` içinde var.
- `@Table`: Entity’nin hangi tabloya karşılık geldiğini belirtir. `users` ve `menus`.
- `@Id`: Tablodaki birincil anahtarı belirtir.
- `@GeneratedValue`: `id` alanının otomatik artacağını söyler.
- `@Column`: Alanın veritabanındaki özelliklerini belirler. Örneğin `nullable`, `length`, `unique`.
- `@Lob`: Büyük veri tutulacağını söyler. Bu projede resim `byte[]` olarak saklanıyor.
- `@Enumerated(EnumType.STRING)`: Enum değerlerinin veritabanında metin olarak saklanmasını sağlar.
- `@GetMapping`: GET isteğini karşılar. Sayfa açma ve veri gösterme için kullanılır.
- `@PostMapping`: POST isteğini karşılar. Form gönderme, ekleme, güncelleme, silme için kullanılır.
- `@PathVariable`: URL içindeki `id` gibi değeri metoda alır.
- `@RequestParam`: URL parametresini alır. Örneğin `forbidden`, `authRequired`.
- `@ModelAttribute`: Form verisini Java nesnesine bağlar. `LoginRequest`, `MenuForm`, `MenuSearchCriteria`.
- `@Valid`: DTO üzerindeki doğrulamaları çalıştırır.
- `@NotBlank`: Alan boş veya sadece boşluk olamaz. Login ve ana yemek alanında kullanılmış.
- `@NotNull`: Alan null olamaz. Tarih, öğün tipi ve kalori gibi alanlarda kullanılmış.
- `@Min`: Sayısal değerin alt sınırını kontrol eder. Kalori negatif olamaz.
- `@AssertTrue`: Özel doğrulama kuralı yazmak için kullanılır. Resmin sadece `jpeg/png` olması burada kontrol edilir.
- `@Configuration`: Ayar sınıfı olduğunu söyler. `WebConfig`.
- `@Bean`: Spring konteynerine nesne ekler. Burada `BCryptPasswordEncoder` üretiliyor.
- `@Component`: Genel amaçlı Spring bileşeni. `AuthInterceptor`, `DataInitializer`.
- `@ControllerAdvice`: Tüm controller’lara ortak davranış ekler. `CurrentUserAdvice`, `GlobalExceptionHandler`.
- `@ExceptionHandler`: Belirli hataları yakalayıp özel cevap üretir.
- `@RequestMapping("/menus")`: Sınıfın temel URL yolunu belirler.
- `@DateTimeFormat`: Tarih alanının formdan doğru okunmasını sağlar.
- `@Transactional`: Veritabanı işlemlerinin tek işlem olarak güvenli yürütülmesini sağlar.
- `@Autowired`: Bu projede kullanılmamış. Onun yerine constructor injection kullanılmış.
- `@Repository`: Bu projede annotation olarak yazılmamış. Çünkü `JpaRepository` kullanan interface’leri Spring otomatik repository olarak tanıyor.
- `@Size`: Bu projede kullanılmamış.

## 5. Sınıflar ne işe yarıyor?
- `KykMenuSystemApplication`: Uygulamayı başlatır.
- `User`: Kullanıcı tablosunu temsil eder. `id`, `username`, `passwordHash`, `role` alanlarını tutar.
- `Menu`: Menü tablosunu temsil eder. Yemek bilgileri ve resim alanları burada tutulur.
- `Role`: Kullanıcı rol enum’udur. `ADMIN`, `USER`.
- `MealType`: Öğün enum’udur. `SABAH`, `OGLE`, `AKSAM`.
- `UserRepository`: Kullanıcıyı veritabanında bulur. Özellikle `findByUsername()` önemlidir.
- `MenuRepository`: Menü kayıtlarını yönetir. Ayrıca dinamik filtreleme için `JpaSpecificationExecutor` kullanır.
- `LoginRequest`: Login formunun DTO’sudur.
- `MenuForm`: Menü ekleme/güncelleme formunun DTO’sudur.
- `MenuSearchCriteria`: Menü arama filtrelerini taşır.
- `AuthService`: Giriş işlemini ve şifre hash işlemini yönetir.
- `MenuService`: Menü CRUD, arama, bugün menüleri ve resim gösterme mantığını yönetir.
- `AuthController`: Login ve logout akışını yönetir.
- `HomeController`: `/` adresini login ya da dashboard’a yönlendirir.
- `DashboardController`: Dashboard sayfasına istatistik ve bugünkü menüleri gönderir.
- `MenuController`: Menü listeleme, detay, ekleme, güncelleme, silme ve resim endpoint’ini yönetir.
- `SessionConstants`: Session anahtarlarını tek yerde toplar.
- `AuthInterceptor`: Giriş yapmayanları ve yetkisiz kullanıcıları engeller.
- `WebConfig`: Interceptor’ı sisteme ekler ve `BCryptPasswordEncoder` bean’ini üretir.
- `CurrentUserAdvice`: Her sayfaya `authenticated`, `currentUsername`, `currentRole` bilgilerini taşır.
- `DataInitializer`: Uygulama açılınca örnek kullanıcı ve örnek menü ekler.
- `GlobalExceptionHandler`: Hataları yakalayıp `error.html` sayfasına yönlendirir.
- `ResourceNotFoundException`: Kayıt bulunamadığında atılan özel exception’dır.

## 6. Önemli metotlar ne yapıyor?
- `main()` - `KykMenuSystemApplication`: Uygulamayı başlatır. Veritabanına doğrudan gitmez.
- `authenticate()` - `AuthService`: Kullanıcıyı bulur, şifreyi kontrol eder. Veritabanına gider.
- `encodePassword()` - `AuthService`: Şifreyi hash’ler. Veritabanına gitmez.
- `searchMenus()` - `MenuService`: Filtreli menü araması yapar. Veritabanına gider.
- `findTodayMenus()` - `MenuService`: Bugünün menülerini getirir. Veritabanına gider.
- `countAllMenus()` - `MenuService`: Toplam menü sayısını verir. Veritabanına gider.
- `countTodayMenus()` - `MenuService`: Bugünkü menü sayısını verir. Veritabanına gider.
- `findById()` - `MenuService`: Tek bir menüyü bulur. Veritabanına gider.
- `create()` - `MenuService`: Yeni menü oluşturur. Veritabanına gider.
- `update()` - `MenuService`: Mevcut menüyü günceller. Veritabanına gider.
- `delete()` - `MenuService`: Menüyü siler. Veritabanına gider.
- `getMenuImage()` - `MenuService`: Resmi binary olarak döner. Veritabanına gider.
- `loginPage()` - `AuthController`: Login sayfasını açar. Veritabanına gitmez.
- `login()` - `AuthController`: Giriş yapar. `AuthService` üzerinden veritabanına gider.
- `logout()` - `AuthController`: Session’ı kapatır. Veritabanına gitmez.
- `listMenus()` - `MenuController`: Menü listesini gösterir. Veritabanına servis üzerinden gider.
- `newMenuForm()` - `MenuController`: Yeni menü formunu açar. Veritabanına gitmez.
- `createMenu()` - `MenuController`: Menü ekler. Veritabanına gider.
- `menuDetail()` - `MenuController`: Menü detay sayfasını açar. Veritabanına gider.
- `editMenuForm()` - `MenuController`: Güncelleme formunu doldurur. Veritabanına gider.
- `updateMenu()` - `MenuController`: Menü günceller. Veritabanına gider.
- `deleteMenu()` - `MenuController`: Menü siler. Veritabanına gider.
- `getMenuImage()` - `MenuController`: Resim endpoint’i olarak çalışır. Veritabanına servis üzerinden gider.
- `dashboard()` - `DashboardController`: Dashboard verilerini hazırlar. Veritabanına servis üzerinden gider.
- `preHandle()` - `AuthInterceptor`: Kullanıcı yetkili mi diye kontrol eder. Session’a bakar, veritabanına gitmez.
- `run()` - `DataInitializer`: İlk örnek verileri yükler. Veritabanına gider.

## 7. Thymeleaf sayfaları
- `login.html` - URL: `/login`. Kullanıcı giriş yapar. `loginRequest`, hata/uyarı mesajları gelir. Form `POST /login` yapar.
- `dashboard.html` - URL: `/dashboard`. Kullanıcı istatistikleri ve bugünkü menüleri görür. `totalMenuCount`, `todayMenuCount`, `todayMenus`, `formattedTodayDate` gelir.
- `menu-list.html` - URL: `/menus`. Kullanıcı menüleri listeler ve filtreler. `menus`, `mealTypes`, `search` gelir. Filtre formu `GET /menus` yapar.
- `menu-detail.html` - URL: `/menus/{id}`. Tek menünün detayını gösterir. `menu` verisi gelir.
- `menu-form.html` - URL: `/menus/new` veya `/menus/{id}/edit`. Admin menü ekler/günceller. `menuForm`, `mealTypes`, `createMode`, `hasImage` gelir. Form ya `POST /menus` ya da `POST /menus/{id}` yapar.
- `error.html` - Hata olduğunda gösterilir. `statusCode`, `errorTitle`, `errorMessage` gelir.
- `fragments/header.html` - Ortak `<head>` içeriğidir.
- `fragments/navbar.html` - Ortak üst menüdür. Session bilgileri ve tema butonu burada.

## 8. Login ve session sistemi
- Kullanıcı login formunu doldurup gönderir.
- `AuthController.login()` formu alır ve doğrular.
- `AuthService.authenticate()` kullanıcıyı `UserRepository.findByUsername()` ile bulur.
- Şifre kontrolü `BCryptPasswordEncoder.matches()` ile yapılır.
- Doğruysa session’a `currentUserId`, `currentUsername`, `currentUserRole` yazılır.
- Login olmayan kullanıcıyı `AuthInterceptor` engeller ve `/login?authRequired` adresine yollar.
- Admin olmayan kullanıcı admin sayfasına giderse `/dashboard?forbidden` yönlendirmesi alır.
- Logout’ta `session.invalidate()` çalışır ve oturum kapanır.

## 9. Resimlerin BLOB olarak saklanması
- Resim dosya olarak klasöre kaydedilmiyor.
- Resmin içeriği `byte[] imageData` olarak veritabanında tutuluyor.
- Resmin tipi `imageContentType` alanında tutuluyor. Örneğin `image/jpeg`.
- Menü eklenirken `MultipartFile` içindeki byte verisi okunup entity’ye yazılıyor.
- Ekranda göstermek için `/menus/image/{id}` endpoint’i çağrılıyor.
- Bu endpoint resmi uygun `Content-Type` ile geri döndürüyor.
- BLOB kullanma sebebi: dosya yönetimini ayrı yapmadan resmi doğrudan veritabanında tutmak ve kaydın kendi verisiyle birlikte saklamak.

## 10. Veritabanı tabloları
- `users` tablosu: `id`, `username`, `passwordHash`, `role`. Kullanıcı giriş bilgileri ve yetki seviyesi burada tutulur.
- `menus` tablosu: `id`, `menu_date`, `mealType`, `soup`, `mainDish`, `sideDish`, `dessertOrDrink`, `calories`, `description`, `imageData`, `imageContentType`.
- Bu projede `users` ile `menus` arasında doğrudan bir ilişki tanımlanmamış.
- Yani menüyü kimin eklediği gibi bir foreign key yapısı yok.

## 11. Sunumda anlatabileceğin kısa konuşma metni
“Bu projede Spring Boot ve Thymeleaf kullanarak bir KYK yemek menüsü takip sistemi geliştirdim. Projede katmanlı mimari kullandım. Controller katmanı kullanıcıdan gelen istekleri alıyor, service katmanı iş mantığını yönetiyor, repository katmanı ise veritabanı işlemlerini yapıyor. Sistemde iki rol var: user ve admin. User menüleri görüntüleyebiliyor, admin ise menü ekleme, güncelleme ve silme işlemlerini yapabiliyor. Giriş işlemini session tabanlı kurdum. Menü görsellerini dosya olarak değil, veritabanında BLOB şeklinde tuttum. Böylece menü verisi ve görseli tek yapıda saklanmış oldu. Ayrıca arama, filtreleme, validasyon ve hata yönetimi gibi temel web uygulaması özelliklerini de ekledim.”

## 12. Hocadan gelebilecek 15 soru ve kısa cevaplar
1. Katmanlı mimari neden kullandın?  
Kodun düzenli, anlaşılır ve yönetilebilir olması için kullandım.

2. Controller ile Service farkı nedir?  
Controller isteği karşılar, Service iş mantığını yürütür.

3. Repository ne yapıyor?  
Veritabanına erişip kayıtları bulma, kaydetme ve silme işini yapıyor.

4. Thymeleaf ne işe yarıyor?  
Spring ile çalışan sunucu taraflı HTML üretim motorudur.

5. Session nedir?  
Kullanıcının giriş bilgisini sunucu tarafında saklayan oturum yapısıdır.

6. BLOB nedir?  
Veritabanında büyük binary veri saklamak için kullanılan yapıdır. Bu projede resimler için kullanıldı.

7. Şifreyi düz metin mi tuttun?  
Hayır, `BCryptPasswordEncoder` ile hash’lenmiş şekilde tuttum.

8. Login kontrolünü nasıl yaptın?  
`AuthInterceptor` ile session kontrolü yaparak.

9. Admin yetkisini nasıl ayırdın?  
Session’daki `Role` bilgisini kontrol ederek.

10. CRUD işlemleri nasıl yapılıyor?  
Controller isteği alıyor, service işliyor, repository veritabanına uyguluyor.

11. DTO neden kullandın?  
Formdan gelen veriyi entity’den ayrı ve daha güvenli şekilde taşımak için.

12. Dynamic search nasıl çalışıyor?  
`MenuSearchCriteria` ile gelen filtrelere göre `Specification` oluşturuluyor.

13. Resmi neden klasöre değil veritabanına koydun?  
Tek yerde tutmak ve ek dosya yönetimi yapmamak için.

14. Veritabanı bağlantısı nerede yapılıyor?  
`application.properties` içinde `spring.datasource.*` ayarlarıyla.

15. Hata olursa kullanıcı ne görüyor?  
`GlobalExceptionHandler` devreye giriyor ve `error.html` sayfası gösteriliyor.

## 13. Atladığın ama bilmen gereken önemli noktalar
- Bu projede `@Autowired` yerine constructor injection kullanılmış. Bu daha temiz ve önerilen yöntemdir.
- `JpaSpecificationExecutor` sayesinde arama alanları esnek şekilde birleştirilebiliyor.
- `CurrentUserAdvice` ortak kullanıcı bilgilerini bütün sayfalara taşıdığı için her controller’da tekrar tekrar session yazılmamış.
- `DataInitializer` sunum için çok kullanışlıdır; çünkü uygulama açılır açılmaz örnek kullanıcılar ve örnek menüler hazır gelir.
- `server.port=8081` olduğu için uygulama varsayılan `8080` yerine `8081` portunda çalışır.
- `spring.jpa.hibernate.ddl-auto=update` ayarı tabloyu tamamen silmez; var olan yapıyı güncellemeye çalışır.
- Bu proje Spring Security kullanmadan, daha basit bir session yapısıyla giriş sistemi kuruyor. Bu da öğrenci projesi için anlaşılır bir çözüm olmuş.

İstersen bir sonraki mesajda bunu daha da kısaltıp sana doğrudan “slaytta okuyabileceğin sunum özeti” haline de çevirebilirim.
