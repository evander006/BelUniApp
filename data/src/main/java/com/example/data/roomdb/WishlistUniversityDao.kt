package com.example.data.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistUniversityDao {
    @Query("SELECT * FROM wishlistuni")
    fun getAll(): Flow<List<WishlistUniversity>>

    @Query("SELECT EXISTS(SELECT 1 FROM wishlistuni WHERE id = :id)")
    suspend fun isInWishlist(id: Int): Boolean

    @Query("SELECT id FROM wishlistuni")
    fun getAllWishlistIds(): Flow<List<Int>>

    @Insert
    suspend fun addToWishlist(uni: WishlistUniversity)

    @Delete
    suspend fun removeFromWishlist(uni: WishlistUniversity)
}