package com.eltoruz.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import com.eltoruz.myprofileapp.data.ProfileData
import com.eltoruz.myprofileapp.data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(ProfileUiState())


    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()


    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }


    fun openEditMode() {
        _uiState.update { it.copy(isEditMode = true) }
    }

    fun cancelEdit() {
        _uiState.update { it.copy(isEditMode = false) }
    }


    fun saveProfile(newName: String, newBio: String) {
        _uiState.update { currentState ->
            currentState.copy(
                profile = currentState.profile.copy(
                    name = newName.trim(),
                    bio = newBio.trim()
                ),
                isEditMode = false
            )
        }
    }
}