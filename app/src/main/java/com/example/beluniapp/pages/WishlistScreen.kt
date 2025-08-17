package com.example.beluniapp.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beluniapp.mvi.viewmodel.MainScreenViewModel
import com.example.beluniapp.pages.unicard.WishlistCard
import com.example.data.UniItemCard
import com.example.data.roomdb.WishlistUniversity

@Composable
fun WishlistScreen(viewModel: MainScreenViewModel= viewModel(),
//                   navigateToDetails:(WishlistUniversity)->Unit
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.loadWishlist()
    }
    when{
        state.wishlist.isEmpty()->{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Your wishlist is empty.")
            }
        }
        state.isLoading->{
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressBarWishlist()
            }
        }else->{
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)){
                items(state.wishlist) { uni->
                    WishlistCard(uni.imageUrl.toString(), uni.name.toString(), uni.city.toString(),
                    //    isFavourite = uni.isFavourite, makeFavourite = {viewModel.toggleFavourite(uni.id)}
                    )
                }
            }
        }
    }
}

@Composable
fun CircularProgressBarWishlist(){
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