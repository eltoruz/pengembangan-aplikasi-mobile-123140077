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
 * ViewModel untuk News List screen.
 * Mengelola state loading, success, error, dan pull-to-refresh.
 */
class NewsListViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadArticles()
    }

    /**
     * Memuat daftar artikel dari repository.
     */
    fun loadArticles() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getArticles()
                .onSuccess { articles ->
                    _uiState.value = UiState.Success(articles)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        error.message ?: "Terjadi kesalahan yang tidak diketahui"
                    )
                }
        }
    }

    /**
     * Refresh data artikel (dipanggil saat pull-to-refresh).
     */
    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            repository.getArticles()
                .onSuccess { articles ->
                    _uiState.value = UiState.Success(articles)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        error.message ?: "Gagal memuat ulang berita"
                    )
                }
            _isRefreshing.value = false
        }
    }
}
