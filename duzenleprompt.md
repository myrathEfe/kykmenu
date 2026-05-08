Mevcut Spring Boot + Thymeleaf KYK Menü Sistemi projesinde SADECE aşağıdaki 3 problemi düzelt. Backend mimarisini, CRUD akışını, entity/repository/service yapısını ve çalışan sayfaları bozma. Büyük refactor yapma.

1. Menü kartına / resme tıklayınca yanlış detay sayfasına gitme sorunu
- Dashboard’daki “Bugünün Menüleri” kartlarında resme veya karta tıklanınca doğru menü detayına gitmeli.
- Her kart kendi menu.id değerini kullanmalı.
- Thymeleaf içinde link şu mantıkta olmalı:
  th:href="@{/menus/{id}(id=${menu.id})}"
- Resim endpoint’i ile detay endpoint’i karışmamalı.
- Resim için:
  th:src="@{/menus/image/{id}(id=${menu.id})}"
- Detay linki için:
  th:href="@{/menus/{id}(id=${menu.id})}"
- Döngü içinde yanlış index, sabit id, todayMenus[0], first item veya hatalı değişken kullanımı varsa düzelt.
- Kartın tamamı link yapılacaksa her kart kendi menu.id’sine göre yönlenmeli.

2. Hover sırasında resimlerin titreme / zıplama problemi
- Menü kartlarında mouse hover olunca tüm resimler titrememeli.
- Hover efekti sadece kartın kendisine hafif uygulanmalı.
- Resimlere ayrı ayrı scale/transform verme.
- Aynı anda hem card hem image üzerinde transform varsa kaldır.
- Kullanılacak güvenli hover:
  .menu-card {
    transition: transform 0.2s ease, box-shadow 0.2s ease;
    overflow: hidden;
  }

  .menu-card:hover {
    transform: translateY(-4px);
    box-shadow: 0 16px 36px rgba(15, 23, 42, 0.16);
  }

  .menu-card img {
    width: 100%;
    height: 220px;
    object-fit: cover;
    display: block;
    transform: none;
    transition: none;
  }

  .menu-card:hover img {
    transform: none;
  }

- Eğer a etiketi, image-wrapper veya card içinde çakışan hover transform varsa temizle.
- Hover sırasında layout shift olmamalı.
- Kart yüksekliği sabit ve stabil olmalı.

3. Dark mode ve lacivert tema renklerini düzelt
Şu an dark mode’da bazı alanlar soluk, okunması zor veya renkler uyumsuz görünüyor. Bunu düzelt.

Genel kurallar:
- Yeşil ton kullanılmasın.
- Ana tema lacivert / koyu mavi olsun.
- Dark mode’da yazılar soluk kalmasın.
- Form inputları, tablo, kart, navbar, butonlar, filtre alanı ve login sayfası okunabilir olsun.
- Placeholder yazıları çok koyu kalmasın.
- Disabled gibi görünmeyen butonlar normal okunabilir olsun.

CSS değişkenleri kullan:
:root {
  --bg: #F8FAFC;
  --surface: #FFFFFF;
  --surface-2: #F1F5F9;
  --primary: #0B1F4D;
  --primary-2: #1E3A8A;
  --accent: #2563EB;
  --text: #0F172A;
  --muted: #64748B;
  --border: #DBEAFE;
}

body.dark-mode {
  --bg: #07172A;
  --surface: #0F2138;
  --surface-2: #142B47;
  --primary: #60A5FA;
  --primary-2: #3B82F6;
  --accent: #60A5FA;
  --text: #F8FAFC;
  --muted: #CBD5E1;
  --border: #274566;
}

Uygula:
- body background: var(--bg)
- card/form/table/filter area background: var(--surface)
- text color: var(--text)
- secondary text: var(--muted)
- border: var(--border)
- primary buttons: lacivert/mavi tonları
- danger buttons kırmızı kalabilir
- success/green class varsa laciverte çevir

Özellikle şu sayfaları kontrol et:
- dashboard
- menus/list
- menus/create veya new menu
- menus/edit
- menus/detail
- login

4. Login ve form ekranları
- Dark mode’da input içleri okunabilir olmalı.
- Label yazıları açık ve net olmalı.
- Placeholder rengi görünür olmalı.
- Sarımsı/soluk input background varsa kaldır.
- Form kartı koyu lacivert ise yazılar beyaz/açık gri olmalı.

5. Menü listesi tablosu
- Dark mode’da tablo başlıkları, satırlar ve butonlar okunabilir olmalı.
- Filtre alanındaki inputlar çok koyu yazı ile kaybolmamalı.
- “Yeni Menü Ekle”, “Ara”, “Detay”, “Düzenle”, “Sil” butonları okunabilir olmalı.

6. Tema toggle
- Mevcut çalışan toggle bozulmasın.
- Sadece renk uyumu gerekiyorsa düzelt.
- localStorage davranışı korunmalı.

Kontrol et:
- Dashboard kartlarına tıklayınca doğru menü detayına gidiyor mu?
- Resme tıklayınca image endpoint’e değil detay sayfasına gidiyor mu?
- Hover sırasında resimler titriyor mu?
- Dark mode’da tüm sayfalarda yazılar okunuyor mu?
- Yeşil ton kaldı mı?
- Light mode bozuldu mu?

Sadece bu sorunları düzelt. Gereksiz yeni özellik ekleme.