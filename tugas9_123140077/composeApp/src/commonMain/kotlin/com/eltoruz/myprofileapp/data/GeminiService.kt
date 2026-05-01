package com.eltoruz.myprofileapp.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class GeminiService(private val client: HttpClient) {

    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta"
    private val model = "gemini-2.5-flash"

    suspend fun generateContent(
        contents: List<Content>,
        generationConfig: GenerationConfig = GenerationConfig()
    ): Result<String> = safeAICall {
        retryWithBackoff {
            val request = GeminiRequest(
                contents = contents,
                generationConfig = generationConfig
            )

            val response: GeminiResponse = client.post(
                "$baseUrl/models/$model:generateContent"
            ) {
                contentType(ContentType.Application.Json)
                parameter("key", ApiConfig.geminiApiKey)
                setBody(request)
            }.body()

            response.error?.let { err ->
                throw AIError.ServerError(err.message ?: "Gemini API error (${err.code})")
            }

            response.promptFeedback?.blockReason?.let { reason ->
                throw AIError.ParseError("Pesan diblokir oleh safety filter: $reason")
            }

            val candidate = response.candidates?.firstOrNull()
                ?: throw AIError.ParseError("Tidak ada respons dari Gemini")

            if (candidate.finishReason == "SAFETY") {
                throw AIError.ParseError("Respons diblokir oleh safety filter")
            }

            candidate.content?.parts?.firstNotNullOfOrNull { it.text }
                ?: throw AIError.ParseError("Respons Gemini kosong, coba kirim pesan lain")
        }
    }
}
