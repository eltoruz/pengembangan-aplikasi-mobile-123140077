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

|                                           Android                                           |                                           Desktop                                           | 
|:-------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|
| ![Android](https://github.com/user-attachments/assets/e1cc4271-571e-4701-8b5e-44b40bb8ced4) | ![Desktop](https://github.com/user-attachments/assets/d2eb8e85-9f19-40ca-a7cc-6f60c6071710) | 



### 2. Favorites Screen (Tab 2)

|                                           Android                                           |                                           Desktop                                           | 
|:-------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|
| ![Android](https://github.com/user-attachments/assets/ba690305-4775-4c9b-9170-24aaad6e7988) | ![Desktop](https://github.com/user-attachments/assets/1664e370-2f7a-4e4e-aaf3-17c518dfb35e) | 



### 3. Profile Screen (Tab 3)

|                                           Android                                           |                                           Desktop                                           | 
|:-------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|
| ![Android](https://github.com/user-attachments/assets/18d738a5-81d3-490c-8b0d-a79c2b60c430) | ![Desktop](https://github.com/user-attachments/assets/5b119e79-77dc-4fb1-91e9-70b173e1ca57) | 



### 4. Note Detail Screen

|                                           Android                                           |                                           Desktop                                           | 
|:-------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|
| ![Android](https://github.com/user-attachments/assets/3888a43e-4da4-451b-9edb-f11b11d12d21) | ![Desktop](https://github.com/user-attachments/assets/1cd6a411-9d1d-408b-b8a2-39d7301d20cc) | 



### 5. Add Note Screen

|                                           Android                                           |                                           Desktop                                           | 
|:-------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|
| ![Android](https://github.com/user-attachments/assets/a66ab81f-e815-4f85-99ec-8fa72d36b976) | ![Desktop](https://github.com/user-attachments/assets/312e244b-bb15-40d6-9283-977646d55254) | 


### 6. Edit Note Screen

|                                           Android                                           |                                           Desktop                                           | 
|:-------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|
| ![Android](https://github.com/user-attachments/assets/4d6afe27-7ac7-49e8-a775-f772e28dc858) | ![Desktop](https://github.com/user-attachments/assets/0f8ab61b-26db-4f33-a2ba-dcc280d84565) | 



### 7. Edit Profile Screen

|                                           Android                                           |                                           Desktop                                           | 
|:-------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|
| ![Android](https://github.com/user-attachments/assets/b44a5bff-f1c9-4db1-8264-3cfb366c7c85) | ![Desktop](https://github.com/user-attachments/assets/1d333bc1-4657-48af-a7ea-8ea22880f881) | 



---

## 🎬 Video Demo (30 Detik)

Video demo menunjukkan semua navigation flows:
1. Navigasi antar tab (Notes → Favorites → Profile)
2. Tambah Note baru (Notes → Add Note → kembali)
3. Lihat detail & edit Note (Notes → Note Detail → Edit Note → kembali)
4. Toggle Favorite dari Note Detail
5. Edit Profile (Profile → Edit Profile → kembali)

🔗 **Link Video Demo:** [VideoDemo](https://github.com/user-attachments/assets/a9a1381e-04bd-4724-8f1f-167b16acd1a7)

---

## 🛠️ Teknologi yang Digunakan

- **Kotlin Multiplatform (KMP)**
- **Compose Multiplatform**
- **Navigation Component** (`androidx.navigation`)
- **Material 3**
- **MVVM Architecture Pattern**
- **StateFlow & ViewModel**
