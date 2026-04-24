package com.eltoruz.myprofileapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileHeader(
    name: String,
    title: String,
    bio: String,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onEditClick: () -> Unit
) {
    val headerColor = if (isDarkMode) Color(0xFF0F3460) else Color(0xFF1976D2)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(headerColor)
            .padding(24.dp)
    ) {

        Row(
            modifier = Modifier.align(Alignment.TopStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                contentDescription = "Toggle Dark Mode",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Switch(
                checked = isDarkMode,
                onCheckedChange = { onToggleDarkMode() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF90CAF9),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFF64B5F6)
                )
            )
        }


        IconButton(
            onClick = onEditClick,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Profile",
                tint = Color.White
            )
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF90CAF9)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Avatar",
                    modifier = Modifier.size(56.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = title,
                fontSize = 14.sp,
                color = Color(0xFFBBDEFB)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = bio,
                fontSize = 13.sp,
                color = Color(0xFFE3F2FD),
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}


@Composable
fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    isDarkMode: Boolean = false
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1976D2).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(text = label, fontSize = 11.sp, color = colorScheme.onSurface.copy(alpha = 0.6f))
                Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = colorScheme.onSurface)
            }
        }
    }
}


@Composable
fun SkillCard(skills: List<String>, isDarkMode: Boolean = false) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        val chunked = skills.chunked(3)
        Column(modifier = Modifier.padding(16.dp)) {
            chunked.forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { skill ->
                        SkillChip(skill = skill)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SkillChip(skill: String, isDarkMode: Boolean = false) {
    val colorScheme = MaterialTheme.colorScheme
    val chipBg = Color(0xFF1976D2).copy(alpha = 0.12f)
    val chipText = Color(0xFF1976D2)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(chipBg)
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = skill,
            fontSize = 13.sp,
            color = chipText,
            fontWeight = FontWeight.Medium
        )
    }
}
