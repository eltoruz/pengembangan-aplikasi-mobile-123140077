# News Feed Simulator

**Tugas 2 — 123140077**

Aplikasi console Kotlin interaktif yang mensimulasikan news feed menggunakan **Kotlin Flow**, **StateFlow**, dan **Coroutines**. Program berjalan di terminal dengan menu interaktif.

## Fitur

| No | Fitur | Implementasi |
|----|-------|-------------|
| 1 | Simulasi berita real-time | `Flow` emit berita baru setiap 2 detik di background |
| 2 | Filter berdasarkan kategori | Extension function `Flow<News>.filterByCategory()` — user pilih via menu |
| 3 | Transform ke format display | Operator `Flow.map` mengubah `News` ke string tampilan |
| 4 | Counter berita dibaca | `StateFlow` menyimpan & memantau jumlah berita secara reactive |
| 5 | Fetch detail async | `async/await` untuk 3 request paralel via coroutines |

## Teknologi

- **Kotlin** 1.9.22
- **Kotlin Coroutines** 1.7.3 (`kotlinx-coroutines-core`)
- **Gradle** (Kotlin DSL)
- **JDK** 17

## Struktur Proyek

```
tugas2_123140077/
├── build.gradle.kts                        # Konfigurasi build & dependency
├── settings.gradle.kts                     # Nama proyek
├── README.md
└── src/
    └── main/
        └── kotlin/
            └── NewsFeedSimulator.kt        # Source code utama
```

## Cara Menjalankan

### Prasyarat

- **JDK 17** atau lebih baru
- Terminal / Command Prompt

### Langkah

1. **Buka folder proyek**

   ```bash
   cd /path/to/tugas2_123140077
   ```

2. **Jalankan aplikasi**

   ```bash
   ./gradlew run --console=plain
   ```

   > Di Windows: `gradlew.bat run --console=plain`

3. **Gunakan menu interaktif** — ketik pilihan lalu Enter:

   | Input | Aksi |
   |-------|------|
   | `1` | Tampilkan semua berita |
   | `2` | Filter hanya Teknologi |
   | `3` | Filter hanya Olahraga |
   | `4` | Filter hanya Ekonomi |
   | `5` | Filter hanya Hiburan |
   | `6` | Filter hanya Nasional |
   | `r` | Baca detail 3 berita (async) |
   | `q` | Keluar & tampilkan ringkasan |

## Contoh Output

```
========================================
   NEWS FEED SIMULATOR
   Kotlin Flow & Coroutines Demo
========================================

===========================================================
Menu: [1]Semua [2]Teknologi [3]Olahraga [4]Ekonomi
      [5]Hiburan [6]Nasional [r]Baca Detail [q]Keluar
===========================================================
--> Memulai stream topik: Semua...

  00:13:00 | [TEKNOLOGI] Kotlin 2.0 Resmi Dirilis — ...
  00:13:02 | [OLAHRAGA] Indonesia Menang di Piala AFF — ...
  00:13:04 | [EKONOMI] Harga Emas Naik Tajam — ...

4                              <-- user ketik 4

--> Filter aktif: Ekonomi
  00:13:08 | [EKONOMI] Harga Emas Naik Tajam — ...
  00:13:18 | [EKONOMI] Rupiah Menguat Terhadap Dolar — ...

q                              <-- user ketik q

========================================
   RINGKASAN
========================================
  Total berita dibaca: 7
  1. Flow      -> emit berita setiap 2 detik
  2. Filter    -> filter by kategori via menu
  3. Transform -> map News ke format display
  4. StateFlow -> counter berita dibaca
  5. Coroutines-> async fetch detail paralel
========================================
```

## Penjelasan Singkat Kode

### Flow (Fitur 1)
```kotlin
fun newsFeedFlow(): Flow<News> = flow {
    while (true) {
        emit(news)       // kirim berita ke collector
        delay(2000)      // tunggu 2 detik
    }
}
```

### Filter by Kategori (Fitur 2)
```kotlin
fun Flow<News>.filterByCategory(category: String?): Flow<News> {
    if (category == null) return this  // null = tampilkan semua
    return this.filter { it.category.equals(category, ignoreCase = true) }
}
```

### Transform (Fitur 3)
```kotlin
fun Flow<News>.transformToDisplay(): Flow<String> {
    return this.map { news ->
        "${news.timestamp} | [${news.category}] ${news.title} — ${news.content}"
    }
}
```

### StateFlow (Fitur 4)
```kotlin
private val _readNewsCount = MutableStateFlow(0)
val readNewsCount: StateFlow<Int> = _readNewsCount.asStateFlow()
```

### Coroutines Async (Fitur 5)
```kotlin
val d1 = async { fetchNewsDetail(1) }
val d2 = async { fetchNewsDetail(5) }
val d3 = async { fetchNewsDetail(9) }
println(d1.await())  // 3 request paralel, selesai ~1 detik
```
