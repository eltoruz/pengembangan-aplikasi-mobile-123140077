package com.eltoruz.myprofileapp.ui


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

    val bgColor = if (isDarkMode) Color(0xFF1A1A2E) else Color(0xFFF5F5F5)
    val cardColor = if (isDarkMode) Color(0xFF16213E) else Color.White
    val textColor = if (isDarkMode) Color.White else Color(0xFF1A1A1A)
    val headerColor = if (isDarkMode) Color(0xFF0F3460) else Color(0xFF1976D2)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(headerColor)
                .padding(16.dp)
        ) {
            // Tombol Back (cancel)
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

            // Tombol Save
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
            colors = CardDefaults.cardColors(containerColor = cardColor),
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
                colors = CardDefaults.cardColors(containerColor = cardColor),
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
                        color = textColor
                    )
                    if (bioValue.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = bioValue,
                            fontSize = 13.sp,
                            color = if (isDarkMode) Color(0xFFAAAAAA) else Color.Gray
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


@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isDarkMode: Boolean = false,
    singleLine: Boolean = true,
    minLines: Int = 1,
    isError: Boolean = false,
    supportingText: String? = null
) {
    val textColor = if (isDarkMode) Color.White else Color(0xFF1A1A1A)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = singleLine,
        minLines = minLines,
        isError = isError,
        supportingText = if (supportingText != null) {
            { Text(supportingText, color = MaterialTheme.colorScheme.error) }
        } else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedBorderColor = Color(0xFF1976D2),
            unfocusedBorderColor = if (isDarkMode) Color(0xFF555555) else Color(0xFFBBBBBB),
            cursorColor = Color(0xFF1976D2)
        )
    )
}