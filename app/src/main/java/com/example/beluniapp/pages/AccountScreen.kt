package com.example.beluniapp.pages

import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beluniapp.R
import com.example.beluniapp.dialogs.ProfileBottomSheet
import com.example.beluniapp.mvi.viewmodel.AccountScreenViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navBack:()->Unit,
    viewmodel: AccountScreenViewModel = viewModel()
    ) {
    val fisrtname by viewmodel.fisrtname.observeAsState("")
    val email by viewmodel.email.observeAsState("")
    val birthDate by viewmodel.birthdate.observeAsState(0L)
    val gender by viewmodel.gender.observeAsState("")
    val lastName by viewmodel.lastname.observeAsState("")
    val phone by viewmodel.phone.observeAsState("")
    val bottomSheetState = rememberModalBottomSheetState(true)
    var showBottomSheet by remember { mutableStateOf(false) }

    val formattedBirthdate=remember(birthDate) {
        if (birthDate!=0L || birthDate!=null){
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(birthDate))
        }else ""
    }

    val context = LocalContext.current
    val imagePath by viewmodel.imagePath.observeAsState()
//    val bitmapimage=remember { mutableStateOf<Bitmap?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewmodel.onImageLoad(uri)}
       // bitmapimage.value=null
    }
    val painter=remember(imagePath) {//Запомни вычисленное значение, и пересчитай его заново, только если imagePath изменится
        if (imagePath!=null){
            BitmapFactory.decodeFile(imagePath)?.asImageBitmap()
        }else null
    }

    val photoPermLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 15.dp)
    ) {
        // Top Bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 15.dp)
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable{navBack()})
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = stringResource(id = R.string.myprofile),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceBright),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Edit",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(15.dp)
                        .clickable {
                            showBottomSheet=true
                        }

                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    Box( // parent box for both photo and edit icon
                        modifier = Modifier
                            .size(100.dp) // whole area size
                            .clickable {
                                photoPermLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
//                                bitmapimage.value != null -> {
//                                    Image(
//                                        bitmap = bitmapimage.value!!.asImageBitmap(),
//                                        contentDescription = "Selected image",
//                                        modifier = Modifier.fillMaxSize(),
//                                        contentScale = ContentScale.Crop
//                                    )
//                                }
                                painter != null -> {
                                    Image(
                                        bitmap = painter,
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                else -> {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Person",
                                        tint = Color.Gray,
                                        modifier = Modifier.size(60.dp)
                                    )
                                }
                            }
                        }

                        // Edit icon overlay
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit Photo",
                            modifier = Modifier
                                .align(Alignment.BottomEnd) // put at corner
                                .offset(x = 8.dp, y = 8.dp) // push it out slightly
                                .size(24.dp)
                                .background(Color.White, CircleShape)
                                .padding(4.dp)
                        )
                    }



                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "${fisrtname} ${lastName}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    HorizontalDivider(Modifier.padding(vertical = 15.dp))

                    ProfileField(label = stringResource(id = R.string.profilename), value = fisrtname)
                    ProfileField(label = stringResource(id = R.string.lastname), value = lastName)
                    ProfileField(label = stringResource(id = R.string.phone), value = phone)
                    ProfileField(label = stringResource(id = R.string.email), value = email)
                    ProfileField(label = stringResource(id = R.string.birth), value = formattedBirthdate)
                    ProfileField(label = stringResource(id = R.string.gender), value = gender)
                }
                if (showBottomSheet){
                    ModalBottomSheet(onDismissRequest = {showBottomSheet=false},
                        sheetState = bottomSheetState) {
                        ProfileBottomSheet(
                            onClear = {},
                            onSave = {showBottomSheet=false}
                        )
                    }
                }
            }
        }
    }

}



@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodySmall)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
