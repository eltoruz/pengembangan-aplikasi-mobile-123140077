package com.eltoruz.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltoruz.myprofileapp.data.AIRepository
import com.eltoruz.myprofileapp.data.Content
import com.eltoruz.myprofileapp.data.Part
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private var nextId = 0L

data class ChatMessage(
    val id: String = "msg_${nextId++}",
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
)

data class ChatUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ChatViewModel(
    private val aiRepository: AIRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val conversationHistory = mutableListOf<Content>()

    fun sendMessage(message: String) {
        if (message.isBlank()) return

        _uiState.update {
            it.copy(
                messages = it.messages + ChatMessage(text = message, isUser = true),
                isLoading = true,
                error = null
            )
        }

        viewModelScope.launch {
            aiRepository.chat(message, conversationHistory.toList())
                .onSuccess { response ->
                    conversationHistory.add(
                        Content(parts = listOf(Part(text = message)), role = "user")
                    )
                    conversationHistory.add(
                        Content(parts = listOf(Part(text = response)), role = "model")
                    )

                    _uiState.update {
                        it.copy(
                            messages = it.messages + ChatMessage(text = response, isUser = false),
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message ?: "Terjadi kesalahan",
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun clearChat() {
        conversationHistory.clear()
        _uiState.value = ChatUiState()
    }

    fun dismissError() {
        _uiState.update { it.copy(error = null) }
    }
}
