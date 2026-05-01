# 🤖 Notes App — Tugas 9

**Tugas 9 - Pengembangan Aplikasi Mobile**  
**Nama:** Rifael Eurico Sitorus  
**NIM:** 123140077  
**Kelas:** RA

---

## 📋 Deskripsi

Aplikasi **Notes** berbasis **Kotlin Multiplatform (KMP)** dengan **Compose Multiplatform**. Upgrade dari Tugas 8 dengan penambahan fitur **AI Chat Assistant** menggunakan **Google Gemini API**:

- **AI Chat (NotesAI)** — Asisten cerdas berbasis Gemini 2.0 Flash
- **Multi-turn Conversation** — Konteks percakapan dipertahankan
- **Prompt Engineering** — System prompt yang well-designed untuk asisten Notes
- **Error Handling** — Sealed class `AIError`, retry with exponential backoff
- **Loading States** — Typing indicator animation saat AI memproses
- Fitur Tugas 8 tetap berjalan: Koin DI, Platform-Specific Features, SQLDelight CRUD, Search, Settings, Favorites

---

## 🏗️ Arsitektur & Pattern

Aplikasi menggunakan **MVVM + Repository + Koin DI** dengan **Ktor HTTP Client**:

```
UI Layer (Compose Screens)
    ↓ koinInject()
ViewModel Layer (Koin-managed ViewModels)
    ↓ Constructor Injection
Repository Layer (NoteRepository, AIRepository, SettingsManager)
    ↓
Data Layer (SQLDelight Database + Multiplatform Settings)
Service Layer (GeminiService → Ktor HttpClient → Gemini API)

Platform Layer (expect/actual)
    ├── DeviceInfo      → Build.MODEL / UIDevice / System.getProperty
    ├── NetworkMonitor   → ConnectivityManager / NWPathMonitor / InetAddress
    └── BatteryInfo      → BatteryManager / UIDevice.batteryLevel / stub
```

### Koin DI Graph

```
commonModule:
    Settings → SettingsManager
    DatabaseDriverFactory → NotesDatabase → NoteRepository
    DeviceInfo
    HttpClient (Ktor + ContentNegotiation + JSON)
    GeminiService(HttpClient)
    AIRepositoryImpl(GeminiService) → AIRepository
    NoteViewModel(NoteRepository, SettingsManager)
    SettingsViewModel(SettingsManager)
    ProfileViewModel
    ChatViewModel(AIRepository)

platformModule (per platform):
    DatabaseDriverFactory (Android: with Context)
    NetworkMonitor (Android: ConnectivityManager)
    BatteryInfo (Android: BatteryManager)
```

### Struktur File

```
composeApp/src/
├── commonMain/kotlin/com/eltoruz/myprofileapp/
│   ├── App.kt
│   ├── di/
│   │   └── AppModule.kt                # Koin modules + HttpClient + AI DI
│   ├── platform/
│   │   ├── DeviceInfo.kt               # expect class
│   │   ├── NetworkMonitor.kt           # expect class
│   │   └── BatteryInfo.kt              # expect class
│   ├── data/
│   │   ├── ApiConfig.kt               # Gemini API key config
│   │   ├── GeminiModels.kt            # Request/Response DTOs (@Serializable)
│   │   ├── GeminiService.kt           # Ktor HTTP calls to Gemini API
│   │   ├── AIRepository.kt            # AIRepository interface + impl + system prompt
│   │   ├── AIError.kt                 # Sealed class errors + retry with backoff
│   │   ├── NoteModel.kt
│   │   ├── NoteRepository.kt
│   │   ├── ProfileModel.kt
│   │   └── SettingsManager.kt
│   ├── db/
│   │   └── DatabaseDriverFactory.kt
│   ├── viewmodel/
│   │   ├── ChatViewModel.kt           # AI Chat state management
│   │   ├── NoteViewModel.kt
│   │   ├── SettingsViewModel.kt
│   │   └── ProfileViewModel.kt
│   ├── navigation/
│   │   ├── Screen.kt                  # + AiChat route
│   │   └── AppNavigation.kt           # + ChatScreen composable
│   ├── screens/
│   │   ├── ChatScreen.kt              # AI Chat UI (bubbles, typing indicator)
│   │   ├── NoteListScreen.kt
│   │   ├── FavoritesScreen.kt
│   │   ├── SettingsScreen.kt
│   │   ├── ProfileScreen.kt
│   │   ├── NoteDetailScreen.kt
│   │   ├── AddNoteScreen.kt
│   │   ├── EditNoteScreen.kt
│   │   └── EditProfileScreen.kt
│   └── components/
│       ├── NoteComponents.kt
│       ├── ProfileComponents.kt
│       ├── FormComponents.kt
│       └── NetworkStatusIndicator.kt
│
├── androidMain/kotlin/com/eltoruz/myprofileapp/
│   ├── MainActivity.kt
│   ├── MyApp.kt
│   ├── Platform.android.kt
│   ├── di/
│   │   └── PlatformModule.android.kt
│   ├── db/
│   │   └── DatabaseDriverFactory.android.kt
│   └── platform/
│       ├── DeviceInfo.android.kt
│       ├── NetworkMonitor.android.kt
│       └── BatteryInfo.android.kt
│
├── iosMain/ ...
└── jvmMain/ ...
```

---

## 🛠️ Tech Stack

| Teknologi | Kegunaan |
|-----------|----------|
| Kotlin Multiplatform | Shared codebase (Android, iOS, JVM) |
| Compose Multiplatform | UI Framework |
| Koin | Dependency Injection |
| **Ktor Client** | **HTTP Client untuk AI API calls** |
| **kotlinx.serialization** | **JSON serialization/deserialization** |
| **Google Gemini 2.0 Flash** | **AI/LLM untuk chat assistant** |
| SQLDelight | Local database (offline-first) |
| Multiplatform Settings | DataStore alternative (theme, sort order) |
| Navigation Compose | Navigasi antar screen |
| Material 3 | Design system |
| Kotlinx DateTime | Timestamp management |
| StateFlow + ViewModel | Reactive state management |

---

## 🤖 Fitur AI — NotesAI Chat Assistant

### System Prompt Design

NotesAI menggunakan system prompt yang dirancang khusus sebagai asisten catatan:
- Membantu pengguna mengelola catatan
- Menjawab pertanyaan umum dengan ramah dan informatif
- Memberikan ide untuk catatan baru
- Merangkum atau memperbaiki teks
- Berkomunikasi dalam Bahasa Indonesia

### Error Handling

Menggunakan sealed class `AIError` untuk menangani:
- **401 Unauthorized** — API key invalid
- **429 Rate Limited** — Terlalu banyak request, retry otomatis
- **500+ Server Error** — Retry dengan exponential backoff
- **Network Error** — Tidak ada koneksi internet
- **Parse Error** — Gagal membaca response

### Multi-turn Conversation

Riwayat percakapan disimpan di `ChatViewModel` dan dikirim ke Gemini API setiap request, sehingga AI memiliki konteks penuh dari percakapan sebelumnya.

---

## 🗄️ Database Schema

### Tabel: `NoteEntity`

```sql
CREATE TABLE NoteEntity (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    created_at INTEGER NOT NULL,
    updated_at INTEGER NOT NULL,
    is_favorite INTEGER NOT NULL DEFAULT 0
);
```

---

## 🎥 Video Demo (45 Detik)

> Video demo menunjukkan:
> 1. **AI Chat** — Membuka tab AI Chat, mengirim pesan, menerima respons AI
> 2. **Multi-turn** — Mengirim pesan lanjutan dengan konteks percakapan
> 3. **Loading State** — Typing indicator saat AI memproses
> 4. **Clear Chat** — Menghapus riwayat percakapan
> 5. **Error Handling** — Menampilkan snackbar jika terjadi error

🔗 **Link Video Demo:** *(akan ditambahkan)*

---

## 🚀 Cara Menjalankan

1. Clone repository
2. Buka project di Android Studio
3. Sync Gradle
4. Run di emulator/device Android (min SDK 24)
