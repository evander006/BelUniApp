package com.example.beluniapp.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenderSelectorDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onGenderSelected: (String) -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "Select Gender")
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            onGenderSelected("Male")
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Male")
                    }
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            onGenderSelected("Female")
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Female")
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )

    }
}



