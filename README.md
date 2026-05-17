# kyk-menu-system

Spring Boot ve Thymeleaf kullanılarak geliştirilen KYK Yemekhane Menü Takip Sistemi.

## Özellikler

- Session tabanlı giriş ve çıkış
- `ADMIN` ve `USER` rol yapısı
- Menü ekleme, listeleme, detay, güncelleme ve silme
- Veritabanında BLOB olarak görsel saklama
- Tarih, öğün türü, ana yemek ve kalori aralığına göre filtreleme
- Bootstrap tabanlı Thymeleaf arayüzü
- Başlangıç admin ve user kullanıcıları

## Varsayılan kullanıcılar

- `admin / admin123`
- `user / user123`

## Veritabanı oluşturma

MySQL kullanacaksan:

```sql
CREATE DATABASE kyk_menu_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

PostgreSQL kullanacaksan:

```sql
CREATE DATABASE kyk_menu_system;
```

## application.properties ayarları

Dosya: [application.properties](/Users/efe/Desktop/kykmenu/src/main/resources/application.properties)

Varsayılan yapı MySQL için hazırdır. İstersen ortam değişkenleri ile değiştirebilirsin:

```bash
export DB_URL=jdbc:mysql://localhost:3306/kyk_menu_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
export DB_USERNAME=root
export DB_PASSWORD=your_password
```

PostgreSQL kullanmak istersen `application.properties` içindeki `spring.datasource.*` alanlarını aşağıdaki örneğe göre güncelle:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/kyk_menu_system
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
```

## Çalıştırma

```bash
mvn spring-boot:run
```

## Giriş adresi

- Login sayfası: [http://localhost:8080/login](http://localhost:8080/login)
- Ana giriş yönlendirmesi: [http://localhost:8080/](http://localhost:8080/)

## Proje yapısı

```text
src
└── main
    ├── java/com/efe/kykmenusystem
    │   ├── config
    │   ├── controller
    │   ├── dto
    │   ├── entity
    │   ├── exception
    │   ├── repository
    │   └── service
    └── resources
        ├── static/css
        ├── static/js
        └── templates
```
