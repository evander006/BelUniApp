package com.example.beluniapp.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.beluniapp.R
import com.example.beluniapp.mvi.viewmodel.MainScreenViewModel
import com.example.data.UniItemCard


@Composable
fun UniversityDetailScreen(
    uni: UniItemCard,
    navigateBack:()->Unit,
    viewmodel: MainScreenViewModel
) {
    val state by viewmodel.state.collectAsState()
    val updatedUni = state.universities.find { it.id==uni.id } ?: uni
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Box {
            AsyncImage(
                model = uni.imageUrl,
                contentDescription = uni.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            IconButton(
                onClick = {navigateBack()},
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
                    .background(Color.White.copy(alpha = 0.7f), CircleShape)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = uni.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
//                maxLines = 3,
//                overflow = TextOverflow.Ellipsis
            )

            Box(modifier = Modifier
                .clickable{
                  viewmodel.toggleFavourite(uni.id)
                }
                .size(36.dp)
                .background(MaterialTheme.colorScheme.surfaceBright, shape = CircleShape),
                contentAlignment = Alignment.Center,){
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    tint = if (updatedUni.isFavourite) Color.Red else Color.Gray
                )
            }
        }
        Spacer(Modifier.height(18.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoTag(icon = Icons.Default.LocationOn, text = uni.city)
            InfoTag(icon = Icons.Default.Star, text = uni.details.firstOrNull()?.rank ?: "No rank available")
            InfoTag(icon = Icons.Default.Notifications, text = uni.details.firstOrNull()?.duration ?: "No duration available")
        }

        Spacer(Modifier.height(18.dp))

        Text(stringResource(R.string.details), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Text(
            text =  uni.details.firstOrNull()?.description ?: "No description available",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(18.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            DetailCard(
                icon = Icons.Default.Edit,
                label = stringResource(R.string.tuition),
                value = "$2,000/yr",
                modifier = Modifier.weight(1f)
            )
            DetailCard(
                icon = Icons.Default.Home,
                label = stringResource(R.string.dorm),
                value = stringResource(R.string.yes),
                modifier = Modifier.weight(1f)
            )
            DetailCard(
                icon = Icons.Default.Person,
                label = stringResource(R.string.website),
                value = uni.details.firstOrNull()?.website ?: "No website available",
                modifier = Modifier.weight(1f)
            )
        }

    }
}

@Composable
fun InfoTag(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0xFFEFEFEF), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(4.dp))
        Text(text, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun DetailCard(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth() // важно для равномерного распределения
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF5F5F5))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = label)
        Spacer(Modifier.height(4.dp))
        Text(text = label, fontWeight = FontWeight.Bold, fontSize = 14.sp, textAlign = TextAlign.Center)
        Text(
            text = value,
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
