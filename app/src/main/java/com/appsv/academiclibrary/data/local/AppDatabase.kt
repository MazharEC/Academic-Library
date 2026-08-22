package com.appsv.academiclibrary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SavedBookEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedBookDao(): SavedBookDao
}