package com.eltoruz.myprofileapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eltoruz.myprofileapp.components.InfoItem
import com.eltoruz.myprofileapp.components.ProfileHeader
import com.eltoruz.myprofileapp.components.SkillCard
import com.eltoruz.myprofileapp.data.ProfileData

@Composable
fun ProfileScreen(
    profile: ProfileData,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onEditClick: () -> Unit
) {
    val bgColor = if (isDarkMode) Color(0xFF1A1A2E) else Color(0xFFF5F5F5)
    val textColor = if (isDarkMode) Color.White else Color(0xFF1A1A1A)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(bgColor)
    ) {

        ProfileHeader(
            name = profile.name,
            title = profile.title,
            bio = profile.bio,
            isDarkMode = isDarkMode,
            onToggleDarkMode = onToggleDarkMode,
            onEditClick = onEditClick
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Contact Info",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        InfoItem(icon = Icons.Default.Email,      label = "Email",    value = profile.email,    isDarkMode = isDarkMode)
        InfoItem(icon = Icons.Default.Phone,      label = "Phone",    value = profile.phone,    isDarkMode = isDarkMode)
        InfoItem(icon = Icons.Default.LocationOn, label = "Location", value = profile.location, isDarkMode = isDarkMode)
        InfoItem(icon = Icons.Default.Work,       label = "Job",      value = profile.job,      isDarkMode = isDarkMode)

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = "Skills",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        SkillCard(skills = profile.skills, isDarkMode = isDarkMode)

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2)
            )
        ) {
            Icon(Icons.Default.Share, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Share Profile")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
