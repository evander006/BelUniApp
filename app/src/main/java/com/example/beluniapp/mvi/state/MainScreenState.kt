package com.example.beluniapp.mvi.state

import com.example.data.UniItemCard
import com.example.data.roomdb.WishlistUniversity

data class MainScreenState(
    val isLoading: Boolean=false,
    val universities:List<UniItemCard> = emptyList(),
    val wishlist:List<WishlistUniversity> = emptyList(),
    val error: String? = null
)