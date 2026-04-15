package com.eltoruz.newsreader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltoruz.newsreader.data.Article
import com.eltoruz.newsreader.data.NewsRepository
import com.eltoruz.newsreader.data.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel untuk News Detail screen.
 * Mengelola state loading, success, error untuk satu artikel.
 */
class NewsDetailViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Article>>(UiState.Loading)
    val uiState: StateFlow<UiState<Article>> = _uiState.asStateFlow()

    /**
     * Memuat detail artikel berdasarkan ID.
     */
    fun loadArticle(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getArticleById(id)
                .onSuccess { article ->
                    _uiState.value = UiState.Success(article)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        error.message ?: "Gagal memuat detail artikel"
                    )
                }
        }
    }
}
