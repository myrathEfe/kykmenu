Mevcut Spring Boot + Thymeleaf KYK Menü Sistemi projesinde UI/UX düzeltmesi yap. Mevcut backend yapısını, entity/repository/service/controller akışını bozma. Sadece gerekli controller/model attribute, Thymeleaf, CSS ve JS düzenlemelerini yap.

Amaç:
Ekran tasarımını modern, lacivert ağırlıklı, temiz ve profesyonel bir dashboard görünümüne çekmek. Yeşil tema tamamen kaldırılacak.

1. Genel tema
- Tüm sayfalardaki yeşil renkleri kaldır.
- Ana tema lacivert / koyu mavi tonlarında olsun.
- Önerilen renkler:
  - Primary: #0B1F4D
  - Secondary: #1E3A8A
  - Accent: #2563EB
  - Background light: #F8FAFC
  - Background dark: #07172A
  - Card: #FFFFFF
  - Text dark: #0F172A
  - Text muted: #64748B
- Navbar, butonlar, kartlar, linkler, hover efektleri bu temaya uygun olsun.
- Bootstrap yapısını bozma, sadece custom CSS ile override et.

2. Tema butonu
- Sağ üstteki mevcut kötü görünen tema butonunu tamamen düzelt.
- Buton küçük, modern, yatay switch/toggle şeklinde olsun.
- İçinde güneş ve ay ikonu olsun.
- “Tema” yazısı görünmesin.
- Light/dark mode gerçekten çalışsın.
- Seçilen tema localStorage’da saklansın.
- Sayfa yenilenince seçilen tema korunmalı.
- Dark mode’da navbar, body, card, input, table, button, modal gibi alanlar düzgün görünmeli.
- Tema toggle tüm sayfalarda aynı yerde ve aynı tasarımda olmalı.

3. Dashboard tarih başlığı düzeltmesi
Dashboard sayfasında şu an “null Günü Yemek Listesi” yazıyor. Bunu düzelt.
Sebep muhtemelen controller’dan tarih attribute’u gelmiyor veya Thymeleaf yanlış değişken kullanıyor.

İstenen çıktı:
“08/05/26 Günü Yemek Listesi”

Kurallar:
- Tarih dinamik olmalı.
- LocalDate.now() kullanılabilir.
- Format dd/MM/yy olacak.
- Controller içinde formattedTodayDate gibi bir model attribute gönder.
- Thymeleaf’te null göstermeyecek şekilde kullan.
- Eğer tarih gelmezse fallback olarak bugünün tarihi JS ile değil, mümkünse backend’den gelsin.

4. Dashboard hero/header alanı
Mevcut hero alanını silme, layout’u bozma.
Sadece metinleri düzenle.

- “ÖĞRENCİ PANELİ” yazısı görünmesin.
- Ana başlık:
  “[BUGÜNÜN TARİHİ] Günü Yemek Listesi”
- Alt açıklama şu olabilir:
  “Bugünkü menüleri kontrol edebilir, geçmiş ve gelecek kayıtları filtreleyebilirsiniz.”
- Sağdaki “Menülere Git” butonu modern lacivert buton olsun.

5. Bugünün Menüleri kartları
“Bugünün Menüleri” bölümündeki yemekler photo card formatında gösterilsin.
Resimler şu an farklı boyutlarda görünüyor, bunu düzelt.

Her kart:
- Aynı genişlikte ve aynı yükseklikte olmalı.
- Resim alanı sabit yükseklikte olmalı. Örn: 220px.
- Resimler object-fit: cover ile kırpılmalı.
- Resim taşmamalı, kartı büyütmemeli.
- Kartların köşeleri yuvarlak olmalı.
- Shadow ve hover animasyonu olmalı.
- Grid responsive olmalı:
  - Desktop: 3 kart yan yana
  - Tablet: 2 kart
  - Mobil: 1 kart

Kart içinde:
- Üstte yemek resmi
- Resmin üzerinde küçük öğün etiketi: SABAH / ÖĞLE / AKŞAM
- Altta yemek adı
- Çorba
- Ana yemek
- Yardımcı yemek
- Tatlı / içecek
- Kalori

6. Resim gösterme
Resimler database’de tutuluyor:
- image_data longblob
- image_content_type varchar(100)

Mevcut image endpoint varsa onu kullan:
GET /menus/image/{id}

Yoksa oluştur:
- Veritabanından imageData ve imageContentType çek.
- ResponseEntity<byte[]> dön.
- Content-Type doğru set edilsin.
- Resim yoksa placeholder göster.

Thymeleaf içinde resim:
<img th:src="@{/menus/image/{id}(id=${menu.id})}" ...>

7. Diğer sayfalar
Aynı lacivert tema şuralara da uygulansın:
- login
- dashboard
- menüler listesi
- yeni menü
- menü düzenleme
- detay sayfası

8. Teknik sınırlar
- Mevcut proje mimarisini bozma.
- Gereksiz büyük refactor yapma.
- Sadece gerekli dosyaları değiştir.
- Kod okunabilir olsun.
- CSS mümkünse tek ana dosyada toplansın.
- JS mümkünse tek ana dosyada toplansın.
- Thymeleaf fragment/navbar yapısı varsa tema toggle oraya eklensin.

9. Kontrol listesi
İş bitince şunları doğrula:
- Yeşil ton kalmadı mı?
- Tema toggle güzel görünüyor mu?
- Tema toggle çalışıyor mu?
- localStorage ile tema korunuyor mu?
- “null Günü Yemek Listesi” düzeldi mi?
- Tarih dd/MM/yy formatında mı?
- Menü resimleri aynı boyutta mı?
- Kartlar responsive mi?
- Dark mode’da tüm sayfalar okunabilir mi?