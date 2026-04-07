## TUGAS 5 — PENGEMBANGAN APLIKASI MOBILE

**Nama:** Rifael Eurico Sitorus<br>
**NIM:** 123140077<br>
**Kelas:** RA

---

## � Deskripsi Proyek

Aplikasi **Notes + Profile** berbasis **Kotlin Multiplatform (KMP)** dengan **Compose Multiplatform**. Pada tugas 5 ini diimplementasikan **Multi-Screen Navigation** menggunakan **Navigation Component**, termasuk:

- **Bottom Navigation** dengan 3 tab (Notes, Favorites, Profile)
- **CRUD Notes** (Create, Read, Update, Delete)
- **Favorite Notes** filtering
- **Profile View & Edit Profile**
- **Argument passing** antar screen (noteId)
- **Back navigation** yang konsisten

---

## ✨ Fitur Aplikasi

| Fitur | Deskripsi |
|---|---|
| 📝 Notes List | Menampilkan daftar catatan dengan opsi favorite & delete |
| ⭐ Favorites | Menampilkan catatan yang ditandai sebagai favorit |
| 👤 Profile | Menampilkan profil pengguna dengan toggle dark mode |
| ➕ Add Note | Form tambah catatan baru (judul & konten) |
| 📖 Note Detail | Detail catatan dengan opsi edit, delete, & toggle favorite |
| ✏️ Edit Note | Form edit judul & konten catatan |
| ✏️ Edit Profile | Form edit nama & bio profil |
| 🌙 Dark Mode | Toggle dark/light mode |

---

## 🏗️ Arsitektur & Struktur Folder

```
composeApp/src/commonMain/kotlin/com/eltoruz/myprofileapp/
│
├── App.kt                          # Entry point, tema & navigasi
│
├── data/
│   ├── NoteModel.kt                # Data class: Note
│   └── ProfileModel.kt             # Data class: ProfileData, ProfileUiState
│
├── viewmodel/
│   ├── NoteViewModel.kt            # ViewModel CRUD Notes + Favorites
│   └── ProfileViewModel.kt         # ViewModel Profile + Dark Mode
│
├── navigation/
│   ├── Screen.kt                   # Sealed class routes & BottomNavItem
│   └── AppNavigation.kt            # NavHost, Bottom Navigation, routing
│
├── screens/
│   ├── NoteListScreen.kt           # Tab 1: Daftar Notes
│   ├── FavoritesScreen.kt          # Tab 2: Daftar Favorites
│   ├── ProfileScreen.kt            # Tab 3: Tampilan Profile
│   ├── NoteDetailScreen.kt         # Detail Note (argument: noteId)
│   ├── AddNoteScreen.kt            # Form tambah Note
│   ├── EditNoteScreen.kt           # Form edit Note (argument: noteId)
│   └── EditProfileScreen.kt        # Form edit Profile
│
└── components/
    ├── ProfileComponents.kt        # Komponen reusable: ProfileHeader, InfoItem, SkillCard, SkillChip
    ├── NoteComponents.kt           # Komponen reusable: NoteCard, FavoriteNoteCard
    └── FormComponents.kt           # Komponen reusable: LabeledTextField
```

---

## 🗺️ Navigation Flow Diagram

```
┌─────────────────────────────────────────────────────────┐
│                   BOTTOM NAVIGATION                      │
│                                                          │
│   ┌──────────┐    ┌──────────┐    ┌──────────┐          │
│   │  Notes   │    │Favorites │    │ Profile  │          │
│   │  (Tab 1) │    │ (Tab 2)  │    │ (Tab 3)  │          │
│   └────┬─────┘    └────┬─────┘    └────┬─────┘          │
│        │               │               │                 │
└────────┼───────────────┼───────────────┼─────────────────┘
         │               │               │
         ▼               │               ▼
   ┌───────────┐         │        ┌──────────────┐
   │ Add Note  │         │        │ Edit Profile │
   │  (FAB +)  │         │        │              │
   └───────────┘         │        └──────────────┘
         │               │
         │               │
         ▼               ▼
   ┌─────────────────────────┐
   │      Note Detail        │
   │   (argument: noteId)    │
   └───────────┬─────────────┘
               │
               ▼
   ┌─────────────────────────┐
   │       Edit Note         │
   │   (argument: noteId)    │
   └─────────────────────────┘
```

### Penjelasan Navigation Flow:
1. **Notes → Note Detail** — Klik catatan untuk melihat detail (passing `noteId`)
2. **Notes → Add Note** — Klik FAB (+) untuk tambah catatan baru
3. **Favorites → Note Detail** — Klik catatan favorit untuk melihat detail (passing `noteId`)
4. **Note Detail → Edit Note** — Klik tombol edit untuk mengedit catatan (passing `noteId`)
5. **Profile → Edit Profile** — Klik tombol edit untuk mengedit profil
6. **Back Navigation** — Semua screen non-tab mendukung kembali ke screen sebelumnya

---

## 📸 Screenshot Setiap Screen

### 1. Notes List Screen (Tab 1 — Home)
![Notes List Screen](masukkan link disini)

### 2. Favorites Screen (Tab 2)
![Favorites Screen](masukkan link disini)

### 3. Profile Screen (Tab 3)
![Profile Screen](masukkan link disini)

### 4. Note Detail Screen
![Note Detail Screen](masukkan link disini)

### 5. Add Note Screen
![Add Note Screen](masukkan link disini)

### 6. Edit Note Screen
![Edit Note Screen](masukkan link disini)

### 7. Edit Profile Screen
![Edit Profile Screen](masukkan link disini)

---

## 🎬 Video Demo (30 Detik)

Video demo menunjukkan semua navigation flows:
1. Navigasi antar tab (Notes → Favorites → Profile)
2. Tambah Note baru (Notes → Add Note → kembali)
3. Lihat detail & edit Note (Notes → Note Detail → Edit Note → kembali)
4. Toggle Favorite dari Note Detail
5. Edit Profile (Profile → Edit Profile → kembali)

🔗 **Link Video Demo:** [masukkan link disini](masukkan link disini)

---

## 🛠️ Teknologi yang Digunakan

- **Kotlin Multiplatform (KMP)**
- **Compose Multiplatform**
- **Navigation Component** (`androidx.navigation`)
- **Material 3**
- **MVVM Architecture Pattern**
- **StateFlow & ViewModel**
