import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// ============================================================
// DATA CLASS
// ============================================================

data class News(
    val id: Int,
    val title: String,
    val category: String,
    val content: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)

// ============================================================
// NEWS FEED SIMULATOR
// ============================================================

class NewsFeedSimulator {

    // -------- [FITUR 4] StateFlow untuk menyimpan jumlah berita yang sudah dibaca --------
    private val _readNewsCount = MutableStateFlow(0)
    val readNewsCount: StateFlow<Int> = _readNewsCount.asStateFlow()

    // -------- Kategori filter aktif (null = semua) --------
    private val _activeCategory = MutableStateFlow<String?>(null)

    private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    // Pool data berita
    private val newsPool = listOf(
        News(1,  "Kotlin 2.0 Resmi Dirilis",           "Teknologi", "Kotlin 2.0 hadir dengan compiler K2 yang lebih cepat."),
        News(2,  "Indonesia Menang di Piala AFF",       "Olahraga",  "Timnas Indonesia menang 3-1 melawan Thailand."),
        News(3,  "AI Generatif Mengubah Dunia Kerja",   "Teknologi", "Perusahaan mulai adopsi AI generatif."),
        News(4,  "Harga Emas Naik Tajam",               "Ekonomi",   "Emas melonjak ke USD 2.800 per troy ounce."),
        News(5,  "Film Indonesia Box Office",           "Hiburan",   "Film baru tembus 5 juta penonton."),
        News(6,  "Gempa M6.2 Guncang Sulawesi",        "Nasional",  "BMKG catat gempa 6.2 SR di Sulteng."),
        News(7,  "Jetpack Compose Makin Populer",       "Teknologi", "Google rilis fitur baru Jetpack Compose."),
        News(8,  "Liga Champions: Madrid vs Bayern",    "Olahraga",  "Pertandingan berakhir imbang 2-2."),
        News(9,  "Rupiah Menguat Terhadap Dolar",       "Ekonomi",   "Rupiah menguat ke Rp15.200 per USD."),
        News(10, "Festival Musik Jakarta 2026",         "Hiburan",   "We The Fest 2026 siap digelar Juli."),
        News(11, "Pemilu 2029: Partai Bersiap",         "Nasional",  "Partai politik mulai konsolidasi."),
        News(12, "Coroutines vs Threads di Kotlin",     "Teknologi", "Coroutines lebih efisien dari threads."),
    )

    // -------- [FITUR 1] Flow yang emit berita baru setiap 2 detik --------
    fun newsFeedFlow(): Flow<News> = flow {
        var index = 0
        while (true) {
            val news = newsPool[index % newsPool.size].copy(timestamp = LocalDateTime.now())
            emit(news)
            index++
            delay(2000) // emit setiap 2 detik
        }
    }

    // -------- [FITUR 2] Filter berita berdasarkan kategori --------
    fun Flow<News>.filterByCategory(category: String?): Flow<News> {
        if (category == null) return this
        return this.filter { it.category.equals(category, ignoreCase = true) }
    }

    // -------- [FITUR 3] Transform data ke format display --------
    fun Flow<News>.transformToDisplay(): Flow<String> {
        return this.map { news ->
            "  ${news.timestamp.format(formatter)} | [${news.category.uppercase()}] ${news.title} — ${news.content}"
        }
    }

    // -------- [FITUR 5] Coroutines untuk fetch detail berita async --------
    suspend fun fetchNewsDetail(newsId: Int): String {
        delay(1000) // simulasi network call
        val news = newsPool.find { it.id == newsId }
            ?: return "  Berita #$newsId tidak ditemukan."
        return buildString {
            appendLine("  +----------------------------------------------------+")
            appendLine("  | DETAIL BERITA #${news.id}")
            appendLine("  +----------------------------------------------------+")
            appendLine("  | Judul    : ${news.title}")
            appendLine("  | Kategori : ${news.category}")
            appendLine("  | Konten   : ${news.content}")
            append("  +----------------------------------------------------+")
        }
    }

    fun markAsRead() {
        _readNewsCount.value++
    }

    // -------- MENU --------
    private fun printMenu() {
        println()
        println("===========================================================")
        println("Menu: [1]Semua [2]Teknologi [3]Olahraga [4]Ekonomi")
        println("      [5]Hiburan [6]Nasional [r]Baca Detail [q]Keluar")
        println("===========================================================")
    }

    // -------- SIMULASI INTERAKTIF --------
    suspend fun run() = coroutineScope {
        println()
        println("========================================")
        println("   NEWS FEED SIMULATOR")
        println("   Kotlin Flow & Coroutines Demo")
        println("========================================")

        printMenu()

        val currentCategory = _activeCategory.value ?: "Semua"
        println("--> Memulai stream topik: $currentCategory...")
        println()

        // Job untuk stream berita di background
        var feedJob = launch {
            collectFeed(_activeCategory.value)
        }

        // Job untuk monitor StateFlow readNewsCount
        val stateFlowJob = launch {
            readNewsCount.collect { count ->
                if (count > 0) {
                    println("  [StateFlow] Total berita dibaca: $count")
                }
            }
        }

        // Baca input user dari terminal (di thread terpisah biar non-blocking)
        val reader = BufferedReader(InputStreamReader(System.`in`))
        val inputJob = launch(Dispatchers.IO) {
            while (isActive) {
                val line = try { reader.readLine() } catch (e: Exception) { null }
                if (line == null) continue

                when (line.trim().lowercase()) {
                    "1" -> {
                        _activeCategory.value = null
                        println("\n--> Beralih ke: Semua kategori")
                        feedJob.cancel()
                        feedJob = launch { collectFeed(null) }
                    }
                    "2" -> {
                        _activeCategory.value = "Teknologi"
                        println("\n--> Filter aktif: Teknologi")
                        feedJob.cancel()
                        feedJob = launch { collectFeed("Teknologi") }
                    }
                    "3" -> {
                        _activeCategory.value = "Olahraga"
                        println("\n--> Filter aktif: Olahraga")
                        feedJob.cancel()
                        feedJob = launch { collectFeed("Olahraga") }
                    }
                    "4" -> {
                        _activeCategory.value = "Ekonomi"
                        println("\n--> Filter aktif: Ekonomi")
                        feedJob.cancel()
                        feedJob = launch { collectFeed("Ekonomi") }
                    }
                    "5" -> {
                        _activeCategory.value = "Hiburan"
                        println("\n--> Filter aktif: Hiburan")
                        feedJob.cancel()
                        feedJob = launch { collectFeed("Hiburan") }
                    }
                    "6" -> {
                        _activeCategory.value = "Nasional"
                        println("\n--> Filter aktif: Nasional")
                        feedJob.cancel()
                        feedJob = launch { collectFeed("Nasional") }
                    }
                    "r" -> {
                        println("\n  Mengambil detail berita #1, #5, #9 secara paralel...")
                        val d1 = async { fetchNewsDetail(1) }
                        val d2 = async { fetchNewsDetail(5) }
                        val d3 = async { fetchNewsDetail(9) }
                        println(d1.await())
                        println(d2.await())
                        println(d3.await())
                        printMenu()
                    }
                    "q" -> {
                        println("\n========================================")
                        println("   RINGKASAN")
                        println("========================================")
                        println("  Total berita dibaca: ${readNewsCount.value}")
                        println()
                        println("  Fitur yang digunakan:")
                        println("  1. Flow      -> emit berita setiap 2 detik")
                        println("  2. Filter    -> filter by kategori via menu")
                        println("  3. Transform -> map News ke format display")
                        println("  4. StateFlow -> counter berita dibaca")
                        println("  5. Coroutines-> async fetch detail paralel")
                        println("========================================")
                        feedJob.cancel()
                        stateFlowJob.cancel()
                        this@coroutineScope.cancel()
                        return@launch
                    }
                    else -> {
                        println("  (Input tidak dikenal. Ketik 1-6, r, atau q)")
                    }
                }
            }
        }
    }

    // Collect feed dengan filter kategori tertentu
    private suspend fun collectFeed(category: String?) {
        newsFeedFlow()
            .filterByCategory(category)
            .transformToDisplay()
            .collect { line ->
                markAsRead()
                println(line)
            }
    }
}

// ============================================================
// MAIN
// ============================================================

fun main() = runBlocking {
    val simulator = NewsFeedSimulator()
    try {
        simulator.run()
    } catch (_: CancellationException) {
        // normal exit saat user ketik 'q'
    }
}
