package com.eltoruz.myprofileapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

// ==========================================
// Routes — sealed class untuk semua screen
// ==========================================
sealed class Screen(val route: String) {
    object NoteList : Screen("note_list")
    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }
    object AddNote : Screen("add_note")
    object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Int) = "edit_note/$noteId"
    }
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
}

// ==========================================
// Bottom Navigation Items
// ==========================================
sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Notes : BottomNavItem("note_list", Icons.Default.Home, "Notes")
    object Favorites : BottomNavItem("favorites", Icons.Default.Favorite, "Favorites")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}
