package com.eltoruz.myprofileapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import com.eltoruz.myprofileapp.data.NoteRepository
import com.eltoruz.myprofileapp.data.SettingsManager
import com.eltoruz.myprofileapp.db.DatabaseDriverFactory
import com.eltoruz.myprofileapp.db.DatabaseHelper
import com.eltoruz.myprofileapp.navigation.AppNavigation
import com.eltoruz.myprofileapp.viewmodel.ProfileViewModel
import com.eltoruz.myprofileapp.viewmodel.SettingsViewModel
import com.russhwolf.settings.Settings

@Composable
fun App(driverFactory: DatabaseDriverFactory, settings: Settings) {
    val profileViewModel = remember { ProfileViewModel() }
    val uiState by profileViewModel.uiState.collectAsState()

    // Initialize database and repository
    val database = remember { DatabaseHelper.getDatabase(driverFactory) }
    val noteRepository = remember { NoteRepository(database) }
    val settingsManager = remember { SettingsManager(settings) }

    // Create SettingsViewModel here so theme changes trigger recomposition
    val settingsViewModel = remember { SettingsViewModel(settingsManager) }
    val currentTheme by settingsViewModel.currentTheme.collectAsState()

    // Determine theme based on reactive settings
    val isDarkMode = when (currentTheme) {
        "dark" -> true
        "light" -> false
        else -> isSystemInDarkTheme() // "system" default
    }

    MaterialTheme(
        colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()
    ) {
        AppNavigation(
            profileViewModel = profileViewModel,
            profileUiState = uiState,
            isDarkMode = isDarkMode,
            noteRepository = noteRepository,
            settingsManager = settingsManager,
            settingsViewModel = settingsViewModel
        )
    }
}