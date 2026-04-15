# 📰 News Reader App

**Tugas 6 - Pengembangan Aplikasi Mobile**  
**NIM: 123140077**  
**Branch: week-6**

---

## 📋 Deskripsi

Aplikasi News Reader yang dibangun menggunakan **Compose Multiplatform (KMP)** dengan **Kotlin**. Aplikasi ini mengambil data berita dari internet dan menampilkannya dalam bentuk list yang interaktif.

---

## 🔗 API yang Digunakan

| API | URL | Deskripsi |
|-----|-----|-----------|
| JSONPlaceholder | `https://jsonplaceholder.typicode.com/posts` | Mengambil daftar artikel (title, body) |
| JSONPlaceholder | `https://jsonplaceholder.typicode.com/posts/{id}` | Mengambil detail artikel |
| Picsum Photos | `https://picsum.photos/id/{id}/600/400` | Gambar placeholder untuk artikel |

---

## 🏗️ Arsitektur & Pattern

Aplikasi menggunakan **MVVM + Repository Pattern**:

```
UI Layer (Compose Screens)
    ↓
ViewModel Layer (StateFlow)
    ↓
Repository Layer (NewsRepository)
    ↓
Network Layer (Ktor Client → JSONPlaceholder API)
```

### Struktur File
```
composeApp/src/commonMain/kotlin/com/eltoruz/newsreader/
├── data/
│   ├── Article.kt          # Data models (@Serializable)
│   ├── HttpClientFactory.kt # Ktor Client setup
│   ├── NewsRepository.kt   # Repository pattern
│   └── UiState.kt          # Sealed class (Loading/Success/Error)
├── viewmodel/
│   ├── NewsListViewModel.kt  # ViewModel untuk list
│   └── NewsDetailViewModel.kt # ViewModel untuk detail
├── ui/
│   ├── ArticleCard.kt      # Card composable
│   ├── NewsListScreen.kt   # List screen + pull-to-refresh
│   └── NewsDetailScreen.kt # Detail screen
└── App.kt                  # Navigation (NavHost)
```

---

## 🛠️ Teknologi yang Digunakan

| Teknologi | Kegunaan |
|-----------|----------|
| Kotlin Multiplatform | Shared codebase |
| Compose Multiplatform | UI Framework |
| Ktor Client | HTTP networking |
| Kotlinx Serialization | JSON parsing |
| Coil 3 | Image loading |
| Navigation Compose | Navigasi antar screen |
| Material 3 | Design system |

---

## ✨ Fitur

1. ✅ **Fetch berita dari API** - Menggunakan Ktor Client ke JSONPlaceholder
2. ✅ **List artikel** - Menampilkan title, description, dan image
3. ✅ **Detail screen** - Tampilan lengkap saat artikel di-klik
4. ✅ **Pull to refresh** - Menggunakan `PullToRefreshBox`
5. ✅ **Loading, Success, Error states** - Menggunakan `UiState` sealed class
6. ✅ **Repository pattern** - `NewsRepository` untuk abstraksi API calls

---

## 📸 Screenshot Semua States

### Loading State
> Tampil saat aplikasi sedang memuat data dari API

![Android](https://github.com/user-attachments/assets/99dcce44-dc8f-46b0-80f1-a49db84cca46)

### Success State  
> Tampil saat data berhasil dimuat - menampilkan list artikel

![Android](https://github.com/user-attachments/assets/c09f836a-0530-4dfd-8125-79341542e763)

### Error State
> Tampil saat gagal memuat data (misal: airplane mode) - dengan tombol Retry

![Android](https://github.com/user-attachments/assets/dbbc742e-2923-4b03-92c3-6e3e0dcf08a2)

### Detail Screen
> Tampil saat artikel di-klik - menampilkan gambar, judul, dan isi lengkap
![Android](https://github.com/user-attachments/assets/69227d10-6c36-4b58-8e9a-bacc243124de)

### Pull to Refresh
> Gesture tarik ke bawah untuk memuat ulang data

![Android](https://github.com/user-attachments/assets/f35a53c6-ca7a-436a-bb8e-1c74222a9cb3)

---

## 🎥 Video Demo

> Video demo 30 detik menunjukkan: loading → success → klik artikel → back → pull to refresh → airplane mode → error → retry

🔗 **Link Video Demo:** [VideoDemo](https://drive.google.com/file/d/1_u-R6yAly9jLAiZ1Kjh04U4jna32IbQ2/view?usp=sharing)

---

## 🚀 Cara Menjalankan

1. Clone repository
2. Buka project di Android Studio
3. Sync Gradle
4. Run di emulator/device Android (min SDK 24)

```bash
./gradlew :composeApp:assembleDebug
```
