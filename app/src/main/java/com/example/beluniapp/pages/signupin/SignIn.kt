package com.example.beluniapp.pages.signupin

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth


@Composable
fun SignIn(onSignInSuccess:() ->Unit) {
    var errorMessage by rememberSaveable  { mutableStateOf<String?>(null) }
    val auth= FirebaseAuth.getInstance()
    var email by rememberSaveable  { mutableStateOf("") }
    var password by rememberSaveable  { mutableStateOf("") }
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 24.dp, end = 24.dp), // horizontal padding added
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {email=it},
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {password=it},
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))

                Button(modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor= MaterialTheme.colorScheme.onPrimary),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty() && password.length>=6){
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task->
                                    if (task.isSuccessful){
                                        onSignInSuccess()
                                    }else{
                                        errorMessage=task.exception?.message ?: "Sign in failed"
                                    }
                                }
                        }
                    }) {
                    Text("Sign In")
                }
            }
        }
        if (errorMessage!=null){
            Toast.makeText(LocalContext.current, "${errorMessage!!}", Toast.LENGTH_SHORT).show()
        }
    }
}