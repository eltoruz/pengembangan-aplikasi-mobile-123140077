package com.eltoruz.myprofileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eltoruz.myprofileapp.components.FavoriteNoteCard
import com.eltoruz.myprofileapp.data.Note

@Composable
fun FavoritesScreen(
    favoriteNotes: List<Note>,
    onNoteClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    if (favoriteNotes.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = colorScheme.onSurface.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Belum ada favorit",
                    fontSize = 18.sp,
                    color = colorScheme.onSurface.copy(alpha = 0.5f)
                )
                Text(
                    text = "Tap ♥ pada catatan untuk menambahkan ke favorit",
                    fontSize = 14.sp,
                    color = colorScheme.onSurface.copy(alpha = 0.35f)
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(favoriteNotes, key = { it.id }) { note ->
                FavoriteNoteCard(
                    note = note,
                    onClick = { onNoteClick(note.id) },
                    onToggleFavorite = { onToggleFavorite(note.id) }
                )
            }
        }
    }
}
