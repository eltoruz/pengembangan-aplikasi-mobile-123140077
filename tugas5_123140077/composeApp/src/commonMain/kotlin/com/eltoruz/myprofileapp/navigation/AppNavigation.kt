package com.eltoruz.myprofileapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.read
import com.eltoruz.myprofileapp.screens.*
import com.eltoruz.myprofileapp.data.ProfileUiState
import com.eltoruz.myprofileapp.viewmodel.NoteViewModel
import com.eltoruz.myprofileapp.viewmodel.ProfileViewModel

@Composable
fun AppNavigation(
    profileViewModel: ProfileViewModel,
    profileUiState: ProfileUiState,
    isDarkMode: Boolean
) {
    val navController = rememberNavController()
    val noteViewModel: NoteViewModel = viewModel { NoteViewModel() }
    val notes by noteViewModel.notes.collectAsState()
    val favoriteNotes = notes.filter { it.isFavorite }


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val bottomNavItems = listOf(
        BottomNavItem.Notes,
        BottomNavItem.Favorites,
        BottomNavItem.Profile
    )


    val bottomNavRoutes = bottomNavItems.map { it.route }
    val showBottomBar = currentRoute in bottomNavRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color.White
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
                                unselectedIconColor = Color(0xFF9E9E9E),
                                unselectedTextColor = Color(0xFF9E9E9E),
                                indicatorColor = Color(0xFFE3F2FD)
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
        NavHost(
            navController = navController,
            startDestination = Screen.NoteList.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screen.NoteList.route) {
                NoteListScreen(
                    notes = notes,
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


            composable(Screen.Profile.route) {
                ProfileScreen(
                    profile = profileUiState.profile,
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = { profileViewModel.toggleDarkMode() },
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
                val noteId = backStackEntry.arguments?.read { getInt("noteId") } ?: 0
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
                val noteId = backStackEntry.arguments?.read { getInt("noteId") } ?: 0
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
