<!-- ## TUGAS 5 PENGEMBANGAN APLIKASI MOBILE 


**Nama:** Rifael Eurico Sitorus<br>
**NIM:** 123140077<br>
**Kelas:** RA

[//]: # (## 📸 Screenshot Aplikasi)

[//]: # ()
[//]: # (### Android)

[//]: # (![alt text]&#40;https://github.com/user-attachments/assets/a224bab0-d714-4906-ac49-16da2b68ac30&#41;)

[//]: # ()
[//]: # (### Desktop)

[//]: # (![alt text]&#40;https://github.com/user-attachments/assets/fae9b30d-b351-4dd5-b88d-6c37b219ccf1&#41;)

[//]: # ()

---

## 📸 Screenshot Aplikasi

### Android

|                                        Profile View                                         |                                       Edit Profile                                       |                                          Dark Mode                                           |
|:-------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------:|
| ![Profile](https://github.com/user-attachments/assets/6249505f-7148-406e-babf-0130b5fa1698) | ![Edit](https://github.com/user-attachments/assets/73cd4feb-81d2-44a2-89d9-17cc09a24720) | ![DarkMode](https://github.com/user-attachments/assets/25848955-5138-4efc-861a-c6e7d53bae4c) |


### Desktop

|                                        Profile View                                         |                                       Edit Profile                                       |                                          Dark Mode                                           |
|:-------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------:|
| ![Profile](https://github.com/user-attachments/assets/c2385f6a-dba1-4b44-b9d8-507424b0f824) | ![Edit](https://github.com/user-attachments/assets/5b1ae805-fbb2-4f4a-b38e-fb3017cd56f0) | ![DarkMode](https://github.com/user-attachments/assets/45fc656d-696e-4dcd-a673-d4be273588ab) |



---



## 📋 Deskripsi Proyek

Aplikasi profil pribadi berbasis **Kotlin Multiplatform (KMP)** dengan **Compose Multiplatform**, dikembangkan dari tugas minggu 3. Pada minggu 4 ini ditambahkan implementasi **MVVM Architecture Pattern**, fitur **Edit Profile**, dan **Dark Mode Toggle**.

---

## ✨ Fitur Aplikasi

| Fitur | Deskripsi |
|---|---|
| 👤 Profile View | Menampilkan foto, nama, bio, info kontak, dan daftar skill |
| ✏️ Edit Profile | Form edit nama dan bio dengan validasi dan preview realtime |
| 🌙 Dark Mode | Toggle dark/light mode yang tersimpan di ViewModel |
| 📤 Share Profile | Tombol share profile (UI placeholder) |

---

## 🏗️ Arsitektur: MVVM Pattern

```
┌─────────────────────────────────────────────┐
│                  DATA LAYER                  │
│         data/ProfileModel.kt                 │
│   ProfileData · ProfileUiState               │
└─────────────────────┬───────────────────────┘
                      │
┌─────────────────────▼───────────────────────┐
│               VIEWMODEL LAYER                │
│       viewmodel/ProfileViewModel.kt          │
│   StateFlow<ProfileUiState>                  │
│   toggleDarkMode() · saveProfile()           │
│   openEditMode()  · cancelEdit()             │
└──────────┬──────────────────────┬───────────┘
    state ↓                       ↑ events
┌──────────▼──────────────────────┴───────────┐
│                  UI LAYER                    │
│   App.kt                                     │
│   ├── ui/ProfileScreen.kt                    │
│   └── ui/EditProfileScreen.kt                │
└─────────────────────────────────────────────┘
```

### Alur Data
- **State** mengalir ke bawah: ViewModel → UI
- **Event** mengalir ke atas: UI → ViewModel

---

## 📁 Struktur Folder

```
composeApp/src/commonMain/kotlin/com/yourname/myprofileapp/
│
├── App.kt                        # Entry point, menghubungkan ViewModel ke UI
│
├── data/
│   └── ProfileModel.kt           # Data class: ProfileData, ProfileUiState
│
├── viewmodel/
│   └── ProfileViewModel.kt       # ViewModel dengan StateFlow
│
└── ui/
    ├── ProfileScreen.kt          # Tampilan utama profil
    └── EditProfileScreen.kt      # Form edit profil + LabeledTextField
```
 -->
