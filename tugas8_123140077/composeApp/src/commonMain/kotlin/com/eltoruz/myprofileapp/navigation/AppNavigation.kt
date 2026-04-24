package com.eltoruz.myprofileapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eltoruz.myprofileapp.components.NetworkStatusIndicator
import com.eltoruz.myprofileapp.data.ProfileUiState
import com.eltoruz.myprofileapp.platform.BatteryInfo
import com.eltoruz.myprofileapp.platform.DeviceInfo
import com.eltoruz.myprofileapp.screens.*
import com.eltoruz.myprofileapp.viewmodel.NoteViewModel
import com.eltoruz.myprofileapp.viewmodel.ProfileViewModel
import com.eltoruz.myprofileapp.viewmodel.SettingsViewModel
import org.koin.compose.koinInject

@Composable
fun AppNavigation(isDarkMode: Boolean) {
    val navController = rememberNavController()

    val noteViewModel: NoteViewModel = koinInject()
    val settingsViewModel: SettingsViewModel = koinInject()
    val profileViewModel: ProfileViewModel = koinInject()
    val deviceInfo: DeviceInfo = koinInject()
    val batteryInfo: BatteryInfo = koinInject()

    val notesUiState by noteViewModel.notesUiState.collectAsState()
    val searchQuery by noteViewModel.searchQuery.collectAsState()
    val favoriteNotes by noteViewModel.favoriteNotes.collectAsState()
    val currentTheme by settingsViewModel.currentTheme.collectAsState()
    val currentSortOrder by settingsViewModel.currentSortOrder.collectAsState()
    val profileUiState by profileViewModel.uiState.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        BottomNavItem.Notes,
        BottomNavItem.Favorites,
        BottomNavItem.Settings,
        BottomNavItem.Profile
    )

    val bottomNavRoutes = bottomNavItems.map { it.route }
    val showBottomBar = currentRoute in bottomNavRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF1976D2),
                                selectedTextColor = Color(0xFF1976D2),
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                indicatorColor = Color(0xFF1976D2).copy(alpha = 0.15f)
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            if (currentRoute == Screen.NoteList.route) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddNote.route) },
                    containerColor = Color(0xFF1976D2),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Note")
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            NetworkStatusIndicator()

            NavHost(
                navController = navController,
                startDestination = Screen.NoteList.route
            ) {
                composable(Screen.NoteList.route) {
                    NoteListScreen(
                        uiState = notesUiState,
                        searchQuery = searchQuery,
                        onSearchQueryChange = { noteViewModel.updateSearchQuery(it) },
                        onNoteClick = { noteId ->
                            navController.navigate(Screen.NoteDetail.createRoute(noteId))
                        },
                        onToggleFavorite = { noteId ->
                            noteViewModel.toggleFavorite(noteId)
                        },
                        onDeleteNote = { noteId ->
                            noteViewModel.deleteNote(noteId)
                        }
                    )
                }

                composable(Screen.Favorites.route) {
                    FavoritesScreen(
                        favoriteNotes = favoriteNotes,
                        onNoteClick = { noteId ->
                            navController.navigate(Screen.NoteDetail.createRoute(noteId))
                        },
                        onToggleFavorite = { noteId ->
                            noteViewModel.toggleFavorite(noteId)
                        }
                    )
                }

                composable(Screen.Settings.route) {
                    SettingsScreen(
                        currentTheme = currentTheme,
                        currentSortOrder = currentSortOrder,
                        onThemeChange = { theme ->
                            settingsViewModel.changeTheme(theme)
                        },
                        onSortOrderChange = { sort ->
                            settingsViewModel.changeSortOrder(sort)
                            noteViewModel.updateSortOrder(sort)
                        },
                        deviceInfo = deviceInfo,
                        batteryInfo = batteryInfo
                    )
                }

                composable(Screen.Profile.route) {
                    ProfileScreen(
                        profile = profileUiState.profile,
                        isDarkMode = isDarkMode,
                        onToggleDarkMode = { settingsViewModel.changeTheme(if (isDarkMode) "light" else "dark") },
                        onEditClick = {
                            navController.navigate("edit_profile")
                        }
                    )
                }

                composable("edit_profile") {
                    EditProfileScreen(
                        currentName = profileUiState.profile.name,
                        currentBio = profileUiState.profile.bio,
                        isDarkMode = isDarkMode,
                        onSave = { newName, newBio ->
                            profileViewModel.saveProfile(newName, newBio)
                            navController.popBackStack()
                        },
                        onCancel = {
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    route = Screen.NoteDetail.route,
                    arguments = listOf(
                        navArgument("noteId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                    val note = noteViewModel.getNoteById(noteId)

                    NoteDetailScreen(
                        note = note,
                        onBack = { navController.popBackStack() },
                        onEdit = { id ->
                            navController.navigate(Screen.EditNote.createRoute(id))
                        },
                        onDelete = { id ->
                            noteViewModel.deleteNote(id)
                            navController.popBackStack()
                        },
                        onToggleFavorite = { id ->
                            noteViewModel.toggleFavorite(id)
                        }
                    )
                }

                composable(Screen.AddNote.route) {
                    AddNoteScreen(
                        onBack = { navController.popBackStack() },
                        onSave = { title, content ->
                            noteViewModel.addNote(title, content)
                            navController.popBackStack()
                        }
                    )
                }

                composable(
                    route = Screen.EditNote.route,
                    arguments = listOf(
                        navArgument("noteId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                    val note = noteViewModel.getNoteById(noteId)

                    EditNoteScreen(
                        note = note,
                        onBack = { navController.popBackStack() },
                        onSave = { id, title, content ->
                            noteViewModel.updateNote(id, title, content)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
