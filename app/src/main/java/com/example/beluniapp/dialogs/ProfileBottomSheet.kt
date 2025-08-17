package com.example.beluniapp.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beluniapp.mvi.viewmodel.AccountScreenViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfileBottomSheet(
    onClear: () -> Unit,
    onSave: () -> Unit,
    viewmodel: AccountScreenViewModel= viewModel()
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showGenderPicker by remember { mutableStateOf(false) }
    val nameState by viewmodel.fisrtname.observeAsState("")
    val lastnameState by viewmodel.lastname.observeAsState("")
    val emailState by viewmodel.email.observeAsState("")
    val birthState by viewmodel.birthdate.observeAsState(0L)
    val genderState by viewmodel.gender.observeAsState("")
    val phoneState by viewmodel.phone.observeAsState("")

    val formattedBirthdate=remember(birthState) {
        if (birthState!=0L || birthState!=null){
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(birthState))
        }else ""
    }

    Surface(
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction = 0.85f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )

            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = nameState,
                onValueChange = {
                    viewmodel.editName(it)
                },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = lastnameState,
                onValueChange = {viewmodel.editLastname(it)},
                label = { Text("Surname") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = emailState,
                onValueChange = {viewmodel.editEmail(it)},
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = phoneState,
                onValueChange = {viewmodel.editPhone(it)},
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = formattedBirthdate,
                onValueChange = {},
                label = { Text("Birthdate") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Pick date")
                    }
                }
            )
            if (showDatePicker){
                DatePicker(onDateSelected = {
                    viewmodel.editBirthDate(it)
                }, onDismiss = {
                    showDatePicker=false
                })
            }
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = genderState,
                onValueChange = {},
                label = { Text("Gender") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { showGenderPicker = true }) {
                        Icon(Icons.Default.Face, contentDescription = "Pick gender")
                    }
                }
            )
            if (showGenderPicker){
                GenderSelectorDialog(
                    show = showGenderPicker,
                    onDismiss = {showGenderPicker=false},
                    onGenderSelected = {
                        viewmodel.editGender(it)}
                )
            }
            Spacer(Modifier.height(24.dp))

            // Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onClear,
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Clear All")
                }
                Button(
                    onClick = onSave,
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Save")
                }
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}
