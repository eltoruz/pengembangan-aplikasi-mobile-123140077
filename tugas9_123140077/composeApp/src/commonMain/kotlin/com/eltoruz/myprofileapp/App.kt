package com.eltoruz.myprofileapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.eltoruz.myprofileapp.navigation.AppNavigation
import com.eltoruz.myprofileapp.viewmodel.SettingsViewModel
import org.koin.compose.koinInject

@Composable
fun App() {
    val settingsViewModel: SettingsViewModel = koinInject()
    val currentTheme by settingsViewModel.currentTheme.collectAsState()

    val isDarkMode = when (currentTheme) {
        "dark" -> true
        "light" -> false
        else -> isSystemInDarkTheme()
    }

    MaterialTheme(
        colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()
    ) {
        AppNavigation(isDarkMode = isDarkMode)
    }
}