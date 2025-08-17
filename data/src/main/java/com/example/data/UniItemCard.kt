package com.example.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UniItemCard(
    val id: Int = 0,
    val imageUrl: String = "",
    val name: String = "",
    val city: String = "",
    var isFavourite: Boolean = false,
    val details: List<UniItemCardDetails> = listOf<UniItemCardDetails>()
): Parcelable

@Parcelize

data class UniItemCardDetails(
    val description: String = "",
    val website: String = "",
    val phone: String = "",
    val email: String = "",
    val rank: String = "",
    val duration: String = "",
): Parcelable