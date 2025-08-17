package com.example.beluniapp.mvi.intent

sealed class MainScreenIntent{
    data class AddFavourites(val id: Int): MainScreenIntent()
    object LoadUniversities: MainScreenIntent()
}