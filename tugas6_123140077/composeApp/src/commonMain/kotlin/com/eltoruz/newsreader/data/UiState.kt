package com.eltoruz.newsreader.data

/**
 * Sealed class untuk merepresentasikan state UI pada networking operations.
 * Mendukung tiga state: Loading, Success, dan Error.
 */
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
