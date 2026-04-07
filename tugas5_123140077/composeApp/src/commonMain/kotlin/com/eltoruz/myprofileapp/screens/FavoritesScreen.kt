package com.eltoruz.myprofileapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
    if (favoriteNotes.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = Color(0xFFBDBDBD)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Belum ada favorit",
                    fontSize = 18.sp,
                    color = Color(0xFF9E9E9E)
                )
                Text(
                    text = "Tap ♥ pada catatan untuk menambahkan ke favorit",
                    fontSize = 14.sp,
                    color = Color(0xFFBDBDBD)
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
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
