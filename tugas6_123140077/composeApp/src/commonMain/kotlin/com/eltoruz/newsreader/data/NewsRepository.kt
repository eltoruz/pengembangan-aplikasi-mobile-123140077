package com.eltoruz.newsreader.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

/**
 * Repository pattern untuk mengakses data dari JSONPlaceholder API.
 * Memisahkan logic API dari ViewModel.
 */
class NewsRepository(private val client: HttpClient) {

    private val baseUrl = "https://jsonplaceholder.typicode.com"
    private val imageBaseUrl = "https://picsum.photos/id"

    /**
     * Mengambil daftar artikel dari /posts.
     * Setiap artikel mendapat gambar dari picsum.photos berdasarkan post ID.
     */
    suspend fun getArticles(): Result<List<Article>> {
        return try {
            val posts: List<Post> = client.get("$baseUrl/posts").body()

            val articles = posts.map { post ->
                Article(
                    id = post.id,
                    title = post.title.replaceFirstChar { it.uppercase() },
                    description = post.body.replaceFirstChar { it.uppercase() },
                    imageUrl = buildImageUrl(post.id)
                )
            }
            Result.success(articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Mengambil detail artikel berdasarkan ID.
     */
    suspend fun getArticleById(id: Int): Result<Article> {
        return try {
            val post: Post = client.get("$baseUrl/posts/$id").body()

            val article = Article(
                id = post.id,
                title = post.title.replaceFirstChar { it.uppercase() },
                description = post.body.replaceFirstChar { it.uppercase() },
                imageUrl = buildImageUrl(post.id)
            )
            Result.success(article)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Membangun URL gambar dari picsum.photos.
     * ID dibatasi sampai 100 karena picsum punya batas ID valid.
     * Format: https://picsum.photos/id/{id}/600/400
     */
    private fun buildImageUrl(id: Int): String {
        val safeId = ((id - 1) % 100) + 1 // pastikan ID antara 1–100
        return "$imageBaseUrl/$safeId/600/400"
    }
}