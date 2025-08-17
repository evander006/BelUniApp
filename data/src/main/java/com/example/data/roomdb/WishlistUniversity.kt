package com.example.data.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlistuni")
data class WishlistUniversity(
    @PrimaryKey val id:Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
)