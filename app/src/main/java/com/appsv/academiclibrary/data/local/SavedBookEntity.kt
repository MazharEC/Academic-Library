package com.appsv.academiclibrary.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_books")
data class SavedBookEntity(
    @PrimaryKey val bookId: String,
    val bookName: String,
    val bookAuthor: String,
    val bookUrl: String,
    val bookDescription: String,
    val courseName: String,
    val booksDept: String,
    val bookImage: String
)