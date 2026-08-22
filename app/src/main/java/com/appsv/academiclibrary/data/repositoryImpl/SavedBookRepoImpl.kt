package com.appsv.academiclibrary.data.repositoryImpl

import com.appsv.academiclibrary.data.local.SavedBookDao
import com.appsv.academiclibrary.data.local.SavedBookEntity
import com.appsv.academiclibrary.domain.repository.SavedBookRepo
import com.appsv.academiclibrary.model.BookModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SavedBookRepoImpl @Inject constructor(
    private val dao: SavedBookDao
) : SavedBookRepo {

    override fun getAllSavedBooks(): Flow<List<BookModel>> =
        dao.getAllSavedBooks().map { entities ->
            entities.map {
                BookModel(
                    bookId = it.bookId,
                    bookName = it.bookName,
                    bookAuthor = it.bookAuthor,
                    bookUrl = it.bookUrl,
                    bookDescription = it.bookDescription,
                    courseName = it.courseName,
                    booksDept = it.booksDept,
                    bookImage = it.bookImage
                )
            }
        }

    override fun isBookSaved(bookId: String): Flow<Boolean> = dao.isBookSaved(bookId)

    override suspend fun toggleSavedBook(book: BookModel, isCurrentlySaved: Boolean) {
        if (isCurrentlySaved) {
            dao.unsaveBook(book.bookId)
        } else {
            dao.saveBook(
                SavedBookEntity(
                    bookId = book.bookId,
                    bookName = book.bookName,
                    bookAuthor = book.bookAuthor,
                    bookUrl = book.bookUrl,
                    bookDescription = book.bookDescription,
                    courseName = book.courseName,
                    booksDept = book.booksDept,
                    bookImage = book.bookImage
                )
            )
        }
    }
}