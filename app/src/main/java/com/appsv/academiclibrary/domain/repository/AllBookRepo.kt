package com.appsv.academiclibrary.domain.repository

import com.appsv.academiclibrary.model.BookModel
import com.appsv.academiclibrary.model.BooksDeptModel
import com.appsv.academiclibrary.model.ResultState
import kotlinx.coroutines.flow.Flow

interface AllBookRepo {
    fun getAllBooks() : Flow<ResultState<List<BookModel>>>
    fun getAllCategories() : Flow<ResultState<List<BooksDeptModel>>>
    fun getBooksByCategory(category : String) : Flow<ResultState<List<BookModel>>>
}