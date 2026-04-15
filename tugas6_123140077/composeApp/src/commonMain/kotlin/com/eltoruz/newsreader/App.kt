package com.eltoruz.newsreader

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eltoruz.newsreader.data.HttpClientFactory
import com.eltoruz.newsreader.data.NewsRepository
import com.eltoruz.newsreader.ui.NewsDetailScreen
import com.eltoruz.newsreader.ui.NewsListScreen
import com.eltoruz.newsreader.viewmodel.NewsDetailViewModel
import com.eltoruz.newsreader.viewmodel.NewsListViewModel

// Custom Color Scheme
private val LightColors = lightColorScheme(
    primary = Color(0xFF1565C0),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD1E4FF),
    onPrimaryContainer = Color(0xFF001D36),
    secondary = Color(0xFF535F70),
    onSecondary = Color.White,
    surface = Color(0xFFFDFBFF),
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFDFE2EB),
    onSurfaceVariant = Color(0xFF43474E),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    background = Color(0xFFF0F2F5)
)

/**
 * Composable utama aplikasi News Reader dengan navigasi.
 */
@Composable
fun App() {
    MaterialTheme(colorScheme = LightColors) {
        val navController = rememberNavController()

        // Membuat instance repository dan HttpClient (singleton)
        val repository = remember {
            val client = HttpClientFactory.create()
            NewsRepository(client)
        }

        NavHost(
            navController = navController,
            startDestination = "newsList"
        ) {
            // Route: Daftar Berita
            composable("newsList") {
                val viewModel = remember { NewsListViewModel(repository) }
                NewsListScreen(
                    viewModel = viewModel,
                    onArticleClick = { articleId ->
                        navController.navigate("newsDetail/$articleId")
                    }
                )
            }

            // Route: Detail Berita
            composable(
                route = "newsDetail/{articleId}",
                arguments = listOf(
                    navArgument("articleId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getInt("articleId") ?: 1
                val viewModel = remember { NewsDetailViewModel(repository) }
                NewsDetailScreen(
                    articleId = articleId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}