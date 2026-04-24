package com.eltoruz.myprofileapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

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
    val colorScheme = MaterialTheme.colorScheme

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
            focusedTextColor = colorScheme.onSurface,
            unfocusedTextColor = colorScheme.onSurface,
            focusedBorderColor = Color(0xFF1976D2),
            unfocusedBorderColor = colorScheme.outline,
            cursorColor = Color(0xFF1976D2),
            focusedLabelColor = Color(0xFF1976D2),
            unfocusedLabelColor = colorScheme.onSurface.copy(alpha = 0.6f)
        )
    )
}
