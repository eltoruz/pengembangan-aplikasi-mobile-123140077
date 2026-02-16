import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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

    private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    // -------- [FITUR 1] Flow yang emit berita baru setiap 2 detik --------
    fun newsFeedFlow(): Flow<News> = flow {
        var index = 0
        while (true) {
            val news = newsPool[index % newsPool.size].copy(timestamp = LocalDateTime.now())
            emit(news)
            index++
            delay(2000)
        }
    }

    // -------- [FITUR 2] Filter berita berdasarkan kategori --------
    fun Flow<News>.filterByCategory(category: String): Flow<News> {
        return this.filter { it.category.equals(category, ignoreCase = true) }
    }

    // -------- [FITUR 3] Transform data ke format display --------
    fun Flow<News>.transformToDisplay(): Flow<String> {
        return this.map { news ->
            "  [${news.timestamp.format(formatter)}] " +
            "[${news.category}] ${news.title} — ${news.content}"
        }
    }

    // -------- [FITUR 5] Coroutines untuk fetch detail berita async --------
    suspend fun fetchNewsDetail(newsId: Int): String {
        delay(1000) // simulasi network call
        val news = newsPool.find { it.id == newsId }
            ?: return "  Berita #$newsId tidak ditemukan."
        return "  ID:${news.id} | ${news.title} | ${news.category} | ${news.content}"
    }

    fun markAsRead() {
        _readNewsCount.value++
    }

    // -------- SIMULASI UTAMA --------
    suspend fun run() {
        println()
        println("========================================")
        println("   NEWS FEED SIMULATOR")
        println("   Kotlin Flow & Coroutines Demo")
        println("========================================")

        // ═══════════════════════════════════════════════
        // DEMO 1: Flow dasar — emit semua berita
        // ═══════════════════════════════════════════════
        println()
        println("----------------------------------------")
        println("DEMO 1: Flow — Emit berita setiap 2 detik")
        println("(Menampilkan 5 berita dari semua kategori)")
        println("----------------------------------------")

        newsFeedFlow()
            .transformToDisplay()
            .take(5)
            .collect { line ->
                markAsRead()
                println(line)
            }

        // ═══════════════════════════════════════════════
        // DEMO 2: Filter kategori
        // Menunjukkan perbedaan: TANPA filter vs DENGAN filter
        // ═══════════════════════════════════════════════
        println()
        println("----------------------------------------")
        println("DEMO 2: Filter berita berdasarkan kategori")
        println("----------------------------------------")
        println()
        println("  Kategori yang ada di news pool:")
        val categories = newsPool.map { it.category }.distinct().sorted()
        categories.forEach { println("    - $it") }

        // Filter: hanya Olahraga
        val filterCategory = "Olahraga"
        println()
        println("  >> Memfilter hanya kategori: \"$filterCategory\"")
        println("  >> Berita non-$filterCategory akan di-skip oleh Flow.filter")
        println()

        newsFeedFlow()
            .filterByCategory(filterCategory)
            .transformToDisplay()
            .take(2)
            .collect { line ->
                markAsRead()
                println(line)
            }

        println()
        println("  (Hanya berita \"$filterCategory\" yang tampil,")
        println("   berita kategori lain otomatis dilewati oleh filter)")

        // Filter kedua: Ekonomi
        val filterCategory2 = "Ekonomi"
        println()
        println("  >> Sekarang filter kategori: \"$filterCategory2\"")
        println()

        newsFeedFlow()
            .filterByCategory(filterCategory2)
            .transformToDisplay()
            .take(2)
            .collect { line ->
                markAsRead()
                println(line)
            }

        println()
        println("  (Hanya berita \"$filterCategory2\" yang tampil)")

        // ═══════════════════════════════════════════════
        // DEMO 3: Async detail fetching (Coroutines)
        // ═══════════════════════════════════════════════
        println()
        println("----------------------------------------")
        println("DEMO 3: Fetch detail berita secara async")
        println("(3 request paralel menggunakan async/await)")
        println("----------------------------------------")
        println()
        println("  Mengirim 3 request secara paralel...")

        coroutineScope {
            val d1 = async { fetchNewsDetail(1) }
            val d2 = async { fetchNewsDetail(5) }
            val d3 = async { fetchNewsDetail(9) }

            // Semua selesai hampir bersamaan karena paralel
            println(d1.await())
            println(d2.await())
            println(d3.await())
        }
        println("  (Ketiga detail di-fetch paralel, total ~1 detik)")

        // ═══════════════════════════════════════════════
        // DEMO 4: StateFlow monitoring
        // ═══════════════════════════════════════════════
        println()
        println("----------------------------------------")
        println("DEMO 4: StateFlow — Counter berita dibaca")
        println("(StateFlow menyimpan state & notify observer)")
        println("----------------------------------------")
        println()
        println("  Berita sudah dibaca sejauh ini: ${readNewsCount.value}")
        println()

        coroutineScope {
            val monitorJob = launch {
                readNewsCount.collect { count ->
                    println("  [StateFlow] nilai berubah -> $count berita dibaca")
                }
            }

            launch {
                repeat(3) { i ->
                    delay(1500)
                    markAsRead()
                    println("  >> Membaca berita tambahan #${i + 1}")
                }
                delay(500)
                monitorJob.cancel()
            }
        }

        // ═══════════════════════════════════════════════
        // RINGKASAN
        // ═══════════════════════════════════════════════
        println()
        println("========================================")
        println("   RINGKASAN")
        println("========================================")
        println("  Total berita dibaca: ${readNewsCount.value}")
        println()
        println("  Fitur yang didemonstrasikan:")
        println("  1. Flow      -> emit berita setiap 2 detik")
        println("  2. Filter    -> filter by kategori (Olahraga, Ekonomi)")
        println("  3. Transform -> map News ke format string display")
        println("  4. StateFlow -> counter berita dibaca (reactive)")
        println("  5. Coroutines-> async/await fetch detail paralel")
        println("========================================")
    }
}

// ============================================================
// MAIN
// ============================================================

fun main() = runBlocking {
    val simulator = NewsFeedSimulator()
    simulator.run()
}
