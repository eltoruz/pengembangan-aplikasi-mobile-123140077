package com.eltoruz.myprofileapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import com.eltoruz.myprofileapp.navigation.AppNavigation
import com.eltoruz.myprofileapp.viewmodel.ProfileViewModel

@Composable
fun App() {
    val viewModel = remember { ProfileViewModel() }
    val uiState by viewModel.uiState.collectAsState()

    MaterialTheme(
        colorScheme = if (uiState.isDarkMode) darkColorScheme() else lightColorScheme()
    ) {
        AppNavigation(
            profileViewModel = viewModel,
            profileUiState = uiState,
            isDarkMode = uiState.isDarkMode
        )
    }
}