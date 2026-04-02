package com.eltoruz.myprofileapp.data

data class Note(
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val timestamp: Long = 0L,
    val isFavorite: Boolean = false
)
