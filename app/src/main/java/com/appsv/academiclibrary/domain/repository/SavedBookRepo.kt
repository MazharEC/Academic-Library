package com.appsv.academiclibrary.domain.repository

import com.appsv.academiclibrary.model.BookModel
import kotlinx.coroutines.flow.Flow

interface SavedBookRepo {
    fun getAllSavedBooks(): Flow<List<BookModel>>
    fun isBookSaved(bookId: String): Flow<Boolean>
    suspend fun toggleSavedBook(book: BookModel, isCurrentlySaved: Boolean)
}