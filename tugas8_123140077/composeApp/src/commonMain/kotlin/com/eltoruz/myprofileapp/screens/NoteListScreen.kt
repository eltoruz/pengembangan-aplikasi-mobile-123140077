package com.eltoruz.myprofileapp.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eltoruz.myprofileapp.components.NoteCard
import com.eltoruz.myprofileapp.data.Note
import com.eltoruz.myprofileapp.viewmodel.NotesUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListScreen(
    uiState: NotesUiState,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onNoteClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onDeleteNote: (Int) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = {
                    Text(
                        "Cari catatan...",
                        color = colorScheme.onSurface.copy(alpha = 0.4f),
                        fontSize = 15.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF1976D2)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color(0xFF1976D2),
                    focusedTextColor = colorScheme.onSurface,
                    unfocusedTextColor = colorScheme.onSurface
                )
            )
        }

        when (uiState) {
            is NotesUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            color = Color(0xFF1976D2),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Memuat catatan...",
                            fontSize = 16.sp,
                            color = colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }

            is NotesUiState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = if (searchQuery.isNotEmpty()) Icons.Default.SearchOff else Icons.Default.Notes,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (searchQuery.isNotEmpty()) "Tidak ditemukan" else "Belum ada catatan",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                        Text(
                            text = if (searchQuery.isNotEmpty()) "Coba kata kunci lain" else "Tap + untuk menambah catatan baru",
                            fontSize = 14.sp,
                            color = colorScheme.onSurface.copy(alpha = 0.35f)
                        )
                    }
                }
            }

            is NotesUiState.Content -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = "${uiState.notes.size} catatan",
                            fontSize = 13.sp,
                            color = colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                        )
                    }
                    items(uiState.notes, key = { it.id }) { note ->
                        NoteCard(
                            note = note,
                            onClick = { onNoteClick(note.id) },
                            onToggleFavorite = { onToggleFavorite(note.id) },
                            onDelete = { onDeleteNote(note.id) }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}
