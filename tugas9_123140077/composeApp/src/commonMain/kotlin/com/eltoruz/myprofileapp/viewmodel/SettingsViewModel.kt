package com.eltoruz.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import com.eltoruz.myprofileapp.data.SettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel(
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _currentTheme = MutableStateFlow(settingsManager.theme)
    val currentTheme: StateFlow<String> = _currentTheme.asStateFlow()

    private val _currentSortOrder = MutableStateFlow(settingsManager.sortOrder)
    val currentSortOrder: StateFlow<String> = _currentSortOrder.asStateFlow()

    fun changeTheme(theme: String) {
        settingsManager.theme = theme
        _currentTheme.value = theme
    }

    fun changeSortOrder(sortOrder: String) {
        settingsManager.sortOrder = sortOrder
        _currentSortOrder.value = sortOrder
    }
}
