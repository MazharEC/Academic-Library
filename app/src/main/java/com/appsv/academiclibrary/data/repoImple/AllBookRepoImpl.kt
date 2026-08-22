package com.appsv.academiclibrary.data.repoImple

import com.appsv.academiclibrary.domain.repository.AllBookRepo
import com.appsv.academiclibrary.model.BookModel
import com.appsv.academiclibrary.model.BooksDeptModel
import com.appsv.academiclibrary.model.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AllBookRepoImpl @Inject constructor(val firebaseDatabase: FirebaseDatabase) : AllBookRepo {

    override fun getAllBooks(): Flow<ResultState<List<BookModel>>> = callbackFlow {
        trySend(ResultState.Loading)
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items: List<BookModel> = snapshot.children.mapNotNull { value ->
                    value.getValue<BookModel>()?.apply { bookId = value.key ?: "" }
                }
                trySend(ResultState.Success(items))
            }
            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }
        }
        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)
        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }

    override fun getAllCategories(): Flow<ResultState<List<BooksDeptModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items: List<BooksDeptModel> = snapshot.children.mapNotNull { value ->
                    value.getValue<BooksDeptModel>()
                }
                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }
        }

        firebaseDatabase.reference.child("BooksCategory").addValueEventListener(valueEvent)

        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }

    override fun getBooksByCategory(category: String): Flow<ResultState<List<BookModel>>> = callbackFlow {

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items: List<BookModel> = snapshot.children
                    .mapNotNull { value -> value.getValue<BookModel>() }
                    .filter { book -> book.booksDept == category }

                trySend(ResultState.Success(items))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(error.toException()))
            }
        }

        firebaseDatabase.reference.child("Books").addValueEventListener(valueEvent)

        awaitClose {
            firebaseDatabase.reference.removeEventListener(valueEvent)
            close()
        }
    }
}