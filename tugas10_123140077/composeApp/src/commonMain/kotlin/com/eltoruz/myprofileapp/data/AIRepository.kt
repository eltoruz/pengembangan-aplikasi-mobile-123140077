package com.eltoruz.myprofileapp.data

interface AIRepository {
    suspend fun chat(
        userMessage: String,
        conversationHistory: List<Content>
    ): Result<String>

    suspend fun summarizeNote(title: String, content: String): Result<String>
}

class AIRepositoryImpl(
    private val geminiService: GeminiService
) : AIRepository {

    private val systemInstruction = """
Kamu adalah asisten cerdas bernama NotesAI di dalam aplikasi Notes.

Tugas:
- Membantu pengguna dalam mengelola catatan mereka
- Menjawab pertanyaan umum dengan ramah dan informatif
- Memberikan ide untuk menulis catatan baru
- Merangkum atau memperbaiki teks yang diberikan pengguna
- Berkomunikasi dalam Bahasa Indonesia

Rules:
- Selalu jawab dengan ramah dan ringkas
- Gunakan Bahasa Indonesia yang baik dan santai
- Jika diminta merangkum, berikan poin-poin utama
- Jika tidak yakin, katakan dengan jujur
- Jangan menggunakan markdown formatting yang berlebihan
""".trimIndent()

    override suspend fun chat(
        userMessage: String,
        conversationHistory: List<Content>
    ): Result<String> {
        val contents = mutableListOf<Content>()

        if (conversationHistory.isEmpty()) {
            contents.add(
                Content(
                    parts = listOf(Part(text = "$systemInstruction\n\nUser: $userMessage")),
                    role = "user"
                )
            )
        } else {
            contents.addAll(conversationHistory)
            contents.add(
                Content(
                    parts = listOf(Part(text = userMessage)),
                    role = "user"
                )
            )
        }

        return geminiService.generateContent(contents)
    }

    override suspend fun summarizeNote(title: String, content: String): Result<String> {
        val prompt = """
Rangkum catatan berikut dalam 2-3 kalimat singkat.
Fokus pada poin-poin utama.

Judul: $title
Isi: $content
""".trimIndent()

        val contents = listOf(
            Content(parts = listOf(Part(text = prompt)), role = "user")
        )

        return geminiService.generateContent(contents)
    }
}
