# 🧪 Notes App — Tugas 10

**Tugas 10 - Pengembangan Aplikasi Mobile**  
**Nama:** Rifael Eurico Sitorus  
**NIM:** 123140077  
**Kelas:** RA

---

## 📋 Deskripsi

Aplikasi **Notes** berbasis **Kotlin Multiplatform (KMP)** dengan **Compose Multiplatform**. Upgrade dari Tugas 9 dengan penambahan **Testing & Dependency Injection** yang lengkap:

- **Koin DI** — 3 modules terpisah: `dataModule`, `networkModule`, `viewModelModule`
- **Unit Test** — kotlin.test + MockK untuk NoteViewModel (10 test cases)
- **Flow Test** — Turbine untuk testing Kotlin Flow (6 test cases)
- **UI Test** — Compose Test + TestTags untuk NoteListScreen (6 test cases)
- **Model Test** — Unit test untuk Note data class (9 test cases)
- Fitur Tugas 9 tetap berjalan: AI Chat (Gemini), SQLDelight, Search, Favorites, Settings

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
    ├── NetworkMonitor  → ConnectivityManager / NWPathMonitor / InetAddress
    └── BatteryInfo     → BatteryManager / UIDevice.batteryLevel / stub
```

### Koin DI Graph (3 Modules)

```
dataModule:
    Settings → SettingsManager
    DatabaseDriverFactory → NotesDatabase → NoteRepository
    DeviceInfo

networkModule:
    HttpClient (Ktor + ContentNegotiation + JSON)
    GeminiService(HttpClient)
    AIRepositoryImpl(GeminiService) → AIRepository

viewModelModule:
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
│   │   └── AppModule.kt               # dataModule + networkModule + viewModelModule
│   ├── utils/
│   │   └── TestTags.kt                # Test tag constants untuk UI testing
│   ├── platform/
│   │   ├── DeviceInfo.kt
│   │   ├── NetworkMonitor.kt
│   │   └── BatteryInfo.kt
│   ├── data/
│   │   ├── ApiConfig.kt
│   │   ├── GeminiModels.kt
│   │   ├── GeminiService.kt
│   │   ├── AIRepository.kt
│   │   ├── AIError.kt
│   │   ├── NoteModel.kt
│   │   ├── NoteRepository.kt
│   │   ├── ProfileModel.kt
│   │   └── SettingsManager.kt
│   ├── viewmodel/
│   │   ├── ChatViewModel.kt
│   │   ├── NoteViewModel.kt
│   │   ├── SettingsViewModel.kt
│   │   └── ProfileViewModel.kt
│   ├── navigation/
│   │   ├── Screen.kt
│   │   └── AppNavigation.kt
│   ├── screens/
│   │   ├── ChatScreen.kt
│   │   ├── NoteListScreen.kt          # + testTag pada NOTES_LIST, EMPTY_STATE, LOADING_STATE
│   │   ├── FavoritesScreen.kt
│   │   ├── SettingsScreen.kt
│   │   ├── ProfileScreen.kt
│   │   ├── NoteDetailScreen.kt
│   │   ├── AddNoteScreen.kt
│   │   ├── EditNoteScreen.kt
│   │   └── EditProfileScreen.kt
│   └── components/
│       ├── NoteComponents.kt          # NoteCard + modifier parameter untuk testTag
│       ├── ProfileComponents.kt
│       ├── FormComponents.kt
│       └── NetworkStatusIndicator.kt
│
├── commonTest/kotlin/com/eltoruz/myprofileapp/
│   ├── ComposeAppCommonTest.kt
│   ├── NoteTest.kt                    # 9 unit test Note model
│   ├── NoteViewModelTest.kt           # 10 unit test ViewModel (MockK)
│   ├── NoteFlowTest.kt                # 6 Flow test (Turbine)
│   └── KoinModuleTest.kt              # 3 Koin DI test
│
├── androidInstrumentedTest/kotlin/com/eltoruz/myprofileapp/
│   └── NoteListScreenTest.kt          # 6 Compose UI test
│
├── androidMain/kotlin/com/eltoruz/myprofileapp/
│   ├── MainActivity.kt
│   ├── MyApp.kt
│   ├── di/PlatformModule.android.kt
│   ├── db/DatabaseDriverFactory.android.kt
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
| **Koin 3.5.3** | **Dependency Injection (3 modules)** |
| **kotlin.test** | **Unit testing framework** |
| **MockK 1.13.9** | **Mocking library untuk ViewModel test** |
| **Turbine 1.0.0** | **Kotlin Flow testing** |
| **Compose Test** | **UI testing dengan test tags** |
| **kotlinx-coroutines-test** | **runTest coroutine support** |
| Ktor Client | HTTP Client untuk AI API calls |
| kotlinx.serialization | JSON serialization/deserialization |
| Google Gemini 2.0 Flash | AI/LLM untuk chat assistant |
| SQLDelight | Local database (offline-first) |
| Multiplatform Settings | DataStore alternative |
| Navigation Compose | Navigasi antar screen |
| Material 3 | Design system |

---

## 🧪 Testing — Detail Implementasi

### Dependency Injection (Koin) — 20%

Koin DI diimplementasikan dengan **3 modules terpisah** sesuai layer arsitektur:

```kotlin
val dataModule = module {
    single { Settings() }
    single { DatabaseHelper.getDatabase(get()) }
    single { NoteRepository(get()) }
    single { SettingsManager(get()) }
    single { DeviceInfo() }
}

val networkModule = module {
    single { HttpClient { ... } }
    single { GeminiService(get()) }
    single<AIRepository> { AIRepositoryImpl(get()) }
}

val viewModelModule = module {
    single { NoteViewModel(get(), get()) }
    single { SettingsViewModel(get()) }
    single { ProfileViewModel() }
    single { ChatViewModel(get()) }
}
```

### Unit Test — Note Model (9 test cases) — 20%

| # | Test Case | Keterangan |
|---|-----------|------------|
| 1 | `noteHasCorrectDefaultValues` | Validasi default value Note() |
| 2 | `noteEquality` | Data class equality check |
| 3 | `noteInequalityOnDifferentId` | Inequality dengan ID berbeda |
| 4 | `noteCopyModifiesCorrectly` | copy() mengubah field yang tepat |
| 5 | `noteWithLongTitleIsValid` | Title 255 karakter diperbolehkan |
| 6 | `noteWithEmptyContentIsAllowed` | Empty content valid |
| 7 | `noteTimestampIsPreserved` | Timestamp tersimpan dengan benar |
| 8 | `noteFavoriteToggleThroughCopy` | Toggle favorite via copy |
| 9 | `noteToStringContainsRelevantData` | toString output valid |

### Unit Test — NoteViewModel (10 test cases, MockK) — 20%

| # | Test Case | Keterangan |
|---|-----------|------------|
| 1 | `initialUiStateIsLoading` | State awal adalah Loading |
| 2 | `uiStateEmitsContentWhenNotesExist` | State Content saat ada notes |
| 3 | `uiStateEmitsEmptyWhenNoNotes` | State Empty saat tidak ada notes |
| 4 | `addNoteCallsRepositoryInsert` | addNote() memanggil repository |
| 5 | `deleteNoteCallsRepositoryDelete` | deleteNote() memanggil repository |
| 6 | `updateNoteCallsRepositoryUpdate` | updateNote() memanggil repository |
| 7 | `toggleFavoriteCallsRepositoryToggle` | toggleFavorite() memanggil repository |
| 8 | `searchQueryUpdatesStateFlow` | Search query flow terupdate |
| 9 | `getNoteByIdReturnsNullWhenNotFound` | getNoteById() null jika tidak ditemukan |
| 10 | `addNoteTrimsTitleAndContent` | addNote() trim whitespace input |

### Flow Test — Turbine (6 test cases) — 15%

| # | Test Case | Keterangan |
|---|-----------|------------|
| 1 | `notesUiStateFlowEmitsLoadingThenContent` | Flow: Loading → Content |
| 2 | `notesUiStateFlowEmitsLoadingThenEmpty` | Flow: Loading → Empty |
| 3 | `searchQueryFlowEmitsCorrectSequence` | Search query flow sequence |
| 4 | `favoriteNotesFlowEmitsCorrectList` | Favorites flow dengan data benar |
| 5 | `notesUiStateReflectsCorrectNoteData` | Data note pada state akurat |
| 6 | `sortOrderFlowEmitsDefaultThenUpdated` | Sort order flow: default → updated |

### UI Test — Compose Test + TestTags (6 test cases) — 15%

| # | Test Case | Keterangan |
|---|-----------|------------|
| 1 | `emptyState_showsEmptyMessage` | Tag EMPTY_STATE + teks "Belum ada catatan" tampil |
| 2 | `loadingState_showsLoadingIndicator` | Tag LOADING_STATE + teks "Memuat catatan..." tampil |
| 3 | `contentState_showsNotesList` | Tag NOTES_LIST + judul notes tampil |
| 4 | `searchInput_isDisplayed` | Tag SEARCH_INPUT selalu tampil |
| 5 | `emptySearchResult_showsSearchNotFoundMessage` | Teks "Tidak ditemukan" saat query tidak cocok |
| 6 | `contentState_showsCorrectNoteCount` | Jumlah catatan ditampilkan dengan benar |

### Koin DI Test (3 test cases)

| # | Test Case | Keterangan |
|---|-----------|------------|
| 1 | `noteViewModelIsInjectedCorrectly` | NoteViewModel bisa di-inject |
| 2 | `noteRepositoryIsInjectedCorrectly` | NoteRepository bisa di-inject |
| 3 | `settingsManagerIsInjectedCorrectly` | SettingsManager bisa di-inject |

---

## 📊 Test Results & Coverage

**Total Test Cases: 30 tests passing** — tersebar di:
- `NoteTest.kt` — 9 tests ✅ (Note data model)
- `NoteViewModelTest.kt` — 10 tests ✅ (MockK)
- `NoteFlowTest.kt` — 6 tests ✅ (Turbine)
- `KoinModuleTest.kt` — 3 tests ✅ (Koin DI)
- `ComposeAppCommonTest.kt` — 1 test ✅
- `NoteListScreenTest.kt` — 6 tests ✅ (Compose UI, androidInstrumentedTest)

### Test Report Screenshot

> Didapatkan dari: `composeApp/build/reports/tests/testDebugUnitTest/index.html`

![Test Results](https://github.com/user-attachments/assets/a2988bae-0400-4170-a43f-7ec1ec34161d)

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
> 1. **Koin DI** — Menunjukkan struktur module di AppModule.kt (`dataModule`, `networkModule`, `viewModelModule`)
> 2. **Unit Tests** — Menjalankan semua commonTest di Android Studio (Run > All Tests)
> 3. **Test Results** — Menunjukkan hasil test hijau (passed) di panel Test Results
> 4. **Flow Tests** — Menyorot Turbine test cases yang berjalan
> 5. **UI Tests** — Menjalankan androidInstrumentedTest di emulator dan menampilkan hasil

🔗 **Link Video Demo:** [VideoDemo](https://drive.google.com/file/d/1jrGIF75Whowkt7wDE1qEjrlvXcAcO2vD/view?usp=sharing)

---

## 🚀 Cara Menjalankan

1. Clone repository
2. Buka project di Android Studio
3. Sync Gradle
4. Jalankan **unit test**: klik kanan `commonTest` → Run Tests
5. Jalankan **UI test**: jalankan emulator → klik kanan `androidInstrumentedTest` → Run Tests
6. Run di emulator/device Android (min SDK 24)
