package com.appsv.academiclibrary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedBookDao {

    @Query("SELECT * FROM saved_books")
    fun getAllSavedBooks(): Flow<List<SavedBookEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM saved_books WHERE bookId = :bookId)")
    fun isBookSaved(bookId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBook(book: SavedBookEntity)

    @Query("DELETE FROM saved_books WHERE bookId = :bookId")
    suspend fun unsaveBook(bookId: String)
}