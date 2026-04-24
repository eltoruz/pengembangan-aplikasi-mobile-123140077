# 📝 Notes App — Tugas 8

**Tugas 8 - Pengembangan Aplikasi Mobile**  
**Nama:** Rifael Eurico Sitorus  
**NIM:** 123140077  
**Kelas:** RA

---

## 📋 Deskripsi

Aplikasi **Notes** berbasis **Kotlin Multiplatform (KMP)** dengan **Compose Multiplatform**. Upgrade dari Tugas 7 dengan penambahan fitur **Platform-Specific Features**:

- **Koin Dependency Injection** untuk seluruh app
- **expect/actual** — `DeviceInfo`, `NetworkMonitor`, `BatteryInfo`
- **Device Info** ditampilkan di Settings screen
- **Network Status Indicator** di main screen
- **Battery Info** (bonus) ditampilkan di Settings screen
- Fitur Tugas 7 tetap berjalan: SQLDelight CRUD, Search, Settings, Favorites

---

## 🏗️ Arsitektur & Pattern

Aplikasi menggunakan **MVVM + Repository + Koin DI** dengan **expect/actual** pattern:

```
UI Layer (Compose Screens)
    ↓ koinInject()
ViewModel Layer (Koin-managed ViewModels)
    ↓ Constructor Injection
Repository Layer (NoteRepository, SettingsManager)
    ↓
Data Layer (SQLDelight Database + Multiplatform Settings)

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
    NoteViewModel(NoteRepository, SettingsManager)
    SettingsViewModel(SettingsManager)
    ProfileViewModel

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
│   │   └── AppModule.kt                # Koin modules + expect platformModule
│   ├── platform/
│   │   ├── DeviceInfo.kt               # expect class
│   │   ├── NetworkMonitor.kt           # expect class
│   │   └── BatteryInfo.kt              # expect class (bonus)
│   ├── data/
│   │   ├── NoteModel.kt
│   │   ├── NoteRepository.kt
│   │   ├── ProfileModel.kt
│   │   └── SettingsManager.kt
│   ├── db/
│   │   └── DatabaseDriverFactory.kt    # expect class
│   ├── viewmodel/
│   │   ├── NoteViewModel.kt
│   │   ├── SettingsViewModel.kt
│   │   └── ProfileViewModel.kt
│   ├── navigation/
│   │   ├── Screen.kt
│   │   └── AppNavigation.kt
│   ├── screens/
│   │   ├── NoteListScreen.kt
│   │   ├── FavoritesScreen.kt
│   │   ├── SettingsScreen.kt           # + Device Info & Battery Info cards
│   │   ├── ProfileScreen.kt
│   │   ├── NoteDetailScreen.kt
│   │   ├── AddNoteScreen.kt
│   │   ├── EditNoteScreen.kt
│   │   └── EditProfileScreen.kt
│   └── components/
│       ├── NoteComponents.kt
│       ├── ProfileComponents.kt
│       ├── FormComponents.kt
│       └── NetworkStatusIndicator.kt   # Network offline banner
│
├── androidMain/kotlin/com/eltoruz/myprofileapp/
│   ├── MainActivity.kt
│   ├── MyApp.kt                        # Application class + Koin init
│   ├── Platform.android.kt
│   ├── di/
│   │   └── PlatformModule.android.kt   # actual platformModule (with Context)
│   ├── db/
│   │   └── DatabaseDriverFactory.android.kt
│   └── platform/
│       ├── DeviceInfo.android.kt       # Build.MODEL, Build.VERSION
│       ├── NetworkMonitor.android.kt   # ConnectivityManager
│       └── BatteryInfo.android.kt      # BatteryManager
│
├── iosMain/kotlin/com/eltoruz/myprofileapp/
│   ├── MainViewController.kt
│   ├── Platform.ios.kt
│   ├── di/
│   │   └── PlatformModule.ios.kt
│   ├── db/
│   │   └── DatabaseDriverFactory.ios.kt
│   └── platform/
│       ├── DeviceInfo.ios.kt           # UIDevice
│       ├── NetworkMonitor.ios.kt       # stub
│       └── BatteryInfo.ios.kt          # UIDevice.batteryLevel
│
└── jvmMain/kotlin/com/eltoruz/myprofileapp/
    ├── main.kt
    ├── Platform.jvm.kt
    ├── di/
    │   └── PlatformModule.jvm.kt
    ├── db/
    │   └── DatabaseDriverFactory.jvm.kt
    └── platform/
        ├── DeviceInfo.jvm.kt           # System.getProperty
        ├── NetworkMonitor.jvm.kt       # InetAddress.isReachable
        └── BatteryInfo.jvm.kt          # stub
```

---

## 🛠️ Tech Stack

| Teknologi | Kegunaan |
|-----------|----------|
| Kotlin Multiplatform | Shared codebase (Android, iOS, JVM) |
| Compose Multiplatform | UI Framework |
| **Koin** | **Dependency Injection** |
| SQLDelight | Local database (offline-first) |
| Multiplatform Settings | DataStore alternative (theme, sort order) |
| Navigation Compose | Navigasi antar screen |
| Material 3 | Design system |
| Kotlinx DateTime | Timestamp management |
| StateFlow + ViewModel | Reactive state management |

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

## 📸 Screenshot

### 1. Settings Screen (Device Info & Battery Info)
![DeviceInfo](https://github.com/user-attachments/assets/a65c01ab-be5f-4a6f-9942-c1e3dad1b801)

### 2. Network Status Indicator (Offline)
![NetworkStatus](https://github.com/user-attachments/assets/41988e51-6a4d-447b-946a-e43d3e7c5b2b)

### 3. Notes List Screen (Online)
![NotesListScreen](https://github.com/user-attachments/assets/e920bdb3-e7e8-4ca5-853c-e65327db8a29)

---

## 🎥 Video Demo (45 Detik)

> Video demo menunjukkan:
> 1. **Koin DI** — Semua dependencies di-inject otomatis
> 2. **Device Info** — Informasi perangkat di Settings
> 3. **Network Status** — Toggle airplane mode, banner muncul/hilang
> 4. **Battery Info** — Level baterai dan status charging
> 5. **CRUD** — Fitur Notes tetap berjalan

🔗 **Link Video Demo:** [VideoDemo](https://drive.google.com/file/d/1R9d7Z8M2H4lNEf-1j3rhqQeMcre2mvdc/view?usp=sharing)


---

## 🚀 Cara Menjalankan

1. Clone repository
2. Buka project di Android Studio
3. Sync Gradle
4. Run di emulator/device Android (min SDK 24)
