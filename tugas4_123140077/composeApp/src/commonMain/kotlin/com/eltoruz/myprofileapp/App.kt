package com.eltoruz.myprofileapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import com.eltoruz.myprofileapp.ui.EditProfileScreen
import com.eltoruz.myprofileapp.ui.ProfileScreen
import com.eltoruz.myprofileapp.viewmodel.ProfileViewModel

@Composable
fun App() {

    val viewModel = remember { ProfileViewModel() }

    val uiState by viewModel.uiState.collectAsState()

    MaterialTheme(
        colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()
    ) {
        if (uiState.isEditMode) {

            EditProfileScreen(
                currentName = uiState.profile.name,
                currentBio = uiState.profile.bio,
                isDarkMode = uiState.isDarkMode,
                onSave = { newName, newBio ->
                    viewModel.saveProfile(newName, newBio)
                },
                onCancel = {
                    viewModel.cancelEdit()
                }
            )
        } else {

            ProfileScreen(
                profile = uiState.profile,
                isDarkMode = uiState.isDarkMode,
                onToggleDarkMode = { viewModel.toggleDarkMode() },
                onEditClick = { viewModel.openEditMode() }
            )
        }
    }
}