package com.example.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WishlistUniversity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wishlistDao(): WishlistUniversityDao
}