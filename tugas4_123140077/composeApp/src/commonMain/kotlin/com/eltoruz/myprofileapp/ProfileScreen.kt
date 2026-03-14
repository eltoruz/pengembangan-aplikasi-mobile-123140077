package com.eltoruz.myprofileapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

// ==============================
// MAIN SCREEN
// ==============================
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF5F5F5))
    ) {
        ProfileHeader(
            name = "Rifael Eurico Sitorus",
            title = "Cyber Security Engineer",
            bio = "Cybersecurity enthusiast focused on securing systems, networks, and applications. Passionate about ethical hacking, threat analysis, and building secure digital solutions."
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Info Section
        Text(
            text = "Contact Info",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        InfoItem(icon = Icons.Default.Email,    label = "Email",    value = "eltoruz@wearehackerone.com")
        InfoItem(icon = Icons.Default.Phone,    label = "Phone",    value = "+62 812-3456-7890")
        InfoItem(icon = Icons.Default.LocationOn, label = "Location", value = "Lampung, Indonesia")


        Spacer(modifier = Modifier.height(16.dp))

        // Skills Section
        Text(
            text = "Skills",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        SkillCard(
            skills = listOf("Python", "Bash", "Web Application Security", "Burpsuite", "Mobile Application Security")
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Button
        Button(
            onClick = { /* action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Share, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Share Profile")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ==============================
// COMPOSABLE 1: ProfileHeader
// ==============================
@Composable
fun ProfileHeader(
    name: String,
    title: String,
    bio: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1976D2))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar Circle (pakai Box karena tanpa gambar asli)
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

// ==============================
// COMPOSABLE 2: InfoItem
// ==============================
@Composable
fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                    .background(Color(0xFFE3F2FD)),
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
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

// ==============================
// COMPOSABLE 3: SkillCard
// ==============================
@Composable
fun SkillCard(skills: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        // Bagi skills menjadi baris-baris @2 item
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
fun SkillChip(skill: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFE3F2FD))
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = skill,
            fontSize = 13.sp,
            color = Color(0xFF1565C0),
            fontWeight = FontWeight.Medium
        )
    }
}