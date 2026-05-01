package com.eltoruz.myprofileapp.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eltoruz.myprofileapp.components.LabeledTextField


@Composable
fun EditProfileScreen(
    currentName: String,
    currentBio: String,
    isDarkMode: Boolean,
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit
) {

    var nameValue by remember(currentName) { mutableStateOf(currentName) }
    var bioValue by remember(currentBio) { mutableStateOf(currentBio) }

    val colorScheme = MaterialTheme.colorScheme
    val headerColor = if (isDarkMode) Color(0xFF0F3460) else Color(0xFF1976D2)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerColor)
                .padding(16.dp)
        ) {
            IconButton(
                onClick = onCancel,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Cancel",
                    tint = Color.White
                )
            }

            Text(
                text = "Edit Profile",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = { onSave(nameValue, bioValue) },
                modifier = Modifier.align(Alignment.CenterEnd),
                enabled = nameValue.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save",
                    tint = if (nameValue.isNotBlank()) Color.White else Color(0x88FFFFFF)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "Informasi Dasar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF1976D2),
                    modifier = Modifier.padding(bottom = 16.dp)
                )


                LabeledTextField(
                    label = "Nama",
                    value = nameValue,
                    onValueChange = { nameValue = it },
                    isDarkMode = isDarkMode,
                    isError = nameValue.isBlank(),
                    supportingText = if (nameValue.isBlank()) "Nama tidak boleh kosong" else null
                )

                Spacer(modifier = Modifier.height(16.dp))


                LabeledTextField(
                    label = "Bio",
                    value = bioValue,
                    onValueChange = { bioValue = it },
                    isDarkMode = isDarkMode,
                    singleLine = false,
                    minLines = 3
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        if (nameValue.isNotBlank()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Preview",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF1976D2),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = nameValue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = colorScheme.onSurface
                    )
                    if (bioValue.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = bioValue,
                            fontSize = 13.sp,
                            color = colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = { onSave(nameValue, bioValue) },
            enabled = nameValue.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2)
            )
        ) {
            Text("Simpan Perubahan", modifier = Modifier.padding(vertical = 4.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
