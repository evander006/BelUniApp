package com.example.beluniapp.pages

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.beluniapp.R
import com.example.beluniapp.mvi.intent.MainScreenIntent
import com.example.beluniapp.mvi.viewmodel.AccountScreenViewModel
import com.example.beluniapp.pages.unicard.UniversityCard
import com.example.beluniapp.mvi.viewmodel.MainScreenViewModel
import com.example.beluniapp.navigation.Screens
import com.example.data.UniItemCard
import kotlin.times

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = viewModel(),
    viewmodelAccount: AccountScreenViewModel=viewModel(),
    navigateToDetails: (UniItemCard) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val searchtext by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val filteredListUnis by viewModel.filteredunis.collectAsState()
    val imagePath by viewmodelAccount.imagePath.observeAsState()
    val painter=remember(imagePath) {//Запомни вычисленное значение, и пересчитай его заново, только если imagePath изменится
        if (imagePath!=null){
            BitmapFactory.decodeFile(imagePath)?.asImageBitmap()
        }else null
    }
    val fisrtname by viewmodelAccount.fisrtname.observeAsState("")

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    // Сделаем размер аватарки как 1/8 ширины экрана, но не меньше 40dp и не больше 80dp
    val avatarSize = screenWidth / 8
    val finalSize = avatarSize.coerceIn(40.dp, 80.dp)

    LaunchedEffect(Unit) {
        if (state.universities.isEmpty()){
            viewModel.handleIntent(MainScreenIntent.LoadUniversities)
        }
    }

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressBar()
            }
        }

        else -> {
            Scaffold(
                topBar = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(finalSize) // ✅ вот здесь управляем размером
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                when {
                                    painter != null -> {
                                        Image(
                                            bitmap = painter,
                                            contentDescription = "User image",
                                            modifier = Modifier
                                                .fillMaxSize() // картинка заполняет Box
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    else -> {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Person",
                                            tint = Color.Gray,
                                            modifier = Modifier.size(finalSize * 0.6f) // иконка чуть меньше Box
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "${stringResource(R.string.hellomain)}, $fisrtname!",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 24.sp
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        DockedSearchBar(
                            colors = SearchBarColors(containerColor = MaterialTheme.colorScheme.surface, dividerColor = MaterialTheme.colorScheme.secondary),
                            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "search") },
                            query = searchtext,
                            onQueryChange = { viewModel.onSearchTextChange(it) },
                            onSearch = { },
                            active = isSearching,
                            onActiveChange = { viewModel.onToggleSearch() },
                            placeholder = { Text(stringResource(R.string.searchunis)) },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                        ) {
                            filteredListUnis.forEach { uni ->
                                ListItem(
                                    headlineContent = { Text(uni.name) },
                                    supportingContent = { Text(uni.city) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { navigateToDetails(uni) }
                                )
                            }
                        }

                    }


                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(filteredListUnis) { uni ->
                        UniversityCard(
                            imageRes = uni.imageUrl,
                            name = uni.name,
                            city = uni.city,
                            isFavourite = uni.isFavourite,
                            makeFavourite = { viewModel.toggleFavourite(uni.id) },
                            goToDetails = { navigateToDetails(uni) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CircularProgressBar(){
    Column(modifier = Modifier.fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator(modifier = Modifier.size(100.dp)
            .padding(16.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 8.dp,
            trackColor = Color.LightGray)
    }
}


//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview(){
//    MainScreen()
//}