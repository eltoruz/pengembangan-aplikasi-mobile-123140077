# � Notes App — Tugas 7

**Tugas 7 - Pengembangan Aplikasi Mobile**  
**Nama:** Rifael Eurico Sitorus  
**NIM:** 123140077  
**Kelas:** RA

---

## 📋 Deskripsi

Aplikasi **Notes** berbasis **Kotlin Multiplatform (KMP)** dengan **Compose Multiplatform**. Fitur utama:

- **SQLDelight** database untuk penyimpanan lokal (offline-first)
- **CRUD Operations** — Create, Read, Update, Delete
- **Search** — Pencarian catatan berdasarkan judul/konten
- **Settings** — Tema (Light/Dark/System) & Urutan catatan via DataStore
- **Favorites** — Tandai catatan sebagai favorit
- **UI States** — Loading, Empty, Content

---

## �️ Database Schema

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

### Queries

| Query | Deskripsi |
|-------|-----------|
| `selectAll` | Semua notes, urut `updated_at DESC` |
| `selectAllByTitle` | Semua notes, urut `title ASC` |
| `selectAllByCreatedAt` | Semua notes, urut `created_at DESC` |
| `selectById` | Note berdasarkan ID |
| `insert` | Tambah note baru |
| `update` | Update title, content, updated_at |
| `delete` | Hapus note berdasarkan ID |
| `search` | Cari berdasarkan title/content (`LIKE`) |
| `toggleFavorite` | Toggle status favorite |
| `selectFavorites` | Semua notes favorit |

---

## 🏗️ Arsitektur & Pattern

Aplikasi menggunakan **MVVM + Repository Pattern** dengan **offline-first** architecture:

```
UI Layer (Compose Screens)
    ↓
ViewModel Layer (StateFlow + Sealed Class UiState)
    ↓
Repository Layer (NoteRepository)
    ↓
Data Layer (SQLDelight Database + Multiplatform Settings)
```

### Struktur File

```
composeApp/src/commonMain/kotlin/com/eltoruz/myprofileapp/
├── App.kt                          # Entry point, theme management
├── data/
│   ├── NoteModel.kt                # Data class: Note
│   ├── NoteRepository.kt           # Repository: CRUD + Search + Favorites
│   ├── ProfileModel.kt             # Data class: ProfileData, ProfileUiState
│   └── SettingsManager.kt          # Multiplatform Settings: theme & sort order
├── db/
│   └── DatabaseDriverFactory.kt    # expect/actual SQLDelight driver
├── viewmodel/
│   ├── NoteViewModel.kt            # ViewModel: CRUD, search, sort, favorites
│   ├── SettingsViewModel.kt        # ViewModel: theme & sort order settings
│   └── ProfileViewModel.kt         # ViewModel: profile & dark mode
├── navigation/
│   ├── Screen.kt                   # Sealed class routes & BottomNavItem
│   └── AppNavigation.kt            # NavHost, Bottom Navigation, routing
├── screens/
│   ├── NoteListScreen.kt           # Tab 1: Daftar Notes + Search bar
│   ├── FavoritesScreen.kt          # Tab 2: Daftar Favorites
│   ├── SettingsScreen.kt           # Tab 3: Settings (theme, sort order)
│   ├── ProfileScreen.kt            # Tab 4: Tampilan Profile
│   ├── NoteDetailScreen.kt         # Detail Note (argument: noteId)
│   ├── AddNoteScreen.kt            # Form tambah Note
│   ├── EditNoteScreen.kt           # Form edit Note (argument: noteId)
│   └── EditProfileScreen.kt        # Form edit Profile
└── components/
    ├── NoteComponents.kt           # NoteCard, FavoriteNoteCard
    ├── ProfileComponents.kt        # ProfileHeader, InfoItem, SkillCard
    └── FormComponents.kt           # LabeledTextField
```

---

## 🛠️ Tech Stack

| Teknologi | Kegunaan |
|-----------|----------|
| Kotlin Multiplatform | Shared codebase (Android, iOS, JVM) |
| Compose Multiplatform | UI Framework |
| SQLDelight | Local database (offline-first) |
| Multiplatform Settings | DataStore alternative (theme, sort order) |
| Navigation Compose | Navigasi antar screen |
| Material 3 | Design system |
| Kotlinx DateTime | Timestamp management |
| StateFlow + ViewModel | Reactive state management |

---

## ✨ Fitur Lengkap

### SQLDelight Setup (20%)
- ✅ Schema `NoteEntity` dengan 6 kolom
- ✅ 10 queries untuk semua operasi
- ✅ Platform drivers: Android, iOS, JVM

### CRUD Operations (25%)
- ✅ **Create** — Tambah catatan baru via `AddNoteScreen`
- ✅ **Read** — Daftar catatan & detail view
- ✅ **Update** — Edit catatan via `EditNoteScreen`
- ✅ **Delete** — Hapus dengan konfirmasi dialog

### DataStore Settings (15%)
- ✅ **Tema** — Light / Dark / System
- ✅ **Urutan catatan** — Terakhir Diubah / Tanggal Dibuat / Judul (A-Z)
- ✅ Settings tersimpan persisten via `multiplatform-settings`

### Search Feature (15%)
- ✅ Search bar di `NoteListScreen`
- ✅ Pencarian berdasarkan judul **dan** konten (`LIKE` query)
- ✅ Clear button & empty search state

### UI/UX (15%)
- ✅ **Loading state** — CircularProgressIndicator
- ✅ **Empty state** — Ikon & pesan informatif
- ✅ **Content state** — LazyColumn dengan NoteCard
- ✅ Bottom Navigation (Notes, Favorites, Settings, Profile)

### Code Quality (10%)
- ✅ MVVM Architecture
- ✅ Repository Pattern
- ✅ Clean package structure (`data/`, `viewmodel/`, `screens/`, `components/`)
- ✅ UI State sealed class (`Loading`, `Empty`, `Content`)

---

## 📸 Screenshot Semua Screens

### 1. Notes List Screen (dengan Search)
![NotesListScreen](https://github.com/user-attachments/assets/f6ed25e8-2c21-4d7b-be25-61ac9e129ecd)

### 2. Add Note Screen
![AddNoteScreen](https://github.com/user-attachments/assets/bec90491-6258-454f-8efd-7707937c7327)

### 3. Note Detail Screen
![NoteDetailScreen](https://github.com/user-attachments/assets/fc05c3cd-3111-4d11-a506-a45a254c17da)

### 4. Edit Note Screen
![EditNoteScreen](https://github.com/user-attachments/assets/320d652a-5a44-4b4c-9b55-ead93a9874d3)

### 5. Favorites Screen
![FavoritesScreen](https://github.com/user-attachments/assets/0453d5ab-68bb-4221-a65a-f84fad7ba8a3)

### 6. Settings Screen (Theme & Sort Order)
![SettingsScreen](https://github.com/user-attachments/assets/67ddabf9-a663-4450-9bfb-9b9c4dae118a)

### 7. Profile Screen
![ProfileScreen](https://github.com/user-attachments/assets/ff014e1b-5277-4174-bf97-45c79b2e1b27)

---

## 🎥 Video Demo (45 Detik)

> Video demo menunjukkan:
> 1. CRUD — Tambah, lihat, edit, hapus catatan
> 2. Search — Cari catatan berdasarkan judul/konten
> 3. Settings — Ganti tema & urutan catatan
> 4. Offline mode — Kill & restart app, data tetap ada

🔗 **Link Video Demo:** [VideoDemo](https://drive.google.com/file/d/1YRwcVZFvZpYDestKG2f0uLjSp8-U8t9S/view?usp=sharing)

---

## 🚀 Cara Menjalankan

1. Clone repository
3. Buka project di Android Studio
4. Sync Gradle
5. Run di emulator/device Android (min SDK 24)
