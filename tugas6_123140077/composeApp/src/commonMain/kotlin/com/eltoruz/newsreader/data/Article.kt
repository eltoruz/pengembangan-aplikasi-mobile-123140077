package com.eltoruz.newsreader.data

import kotlinx.serialization.Serializable

/**
 * Data class dari JSONPlaceholder /posts endpoint
 */
@Serializable
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

/**
 * Data class dari JSONPlaceholder /photos endpoint
 */
@Serializable
data class Photo(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)

/**
 * Model artikel gabungan dari Post + Photo untuk ditampilkan di UI
 */
data class Article(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)
