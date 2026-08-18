package com.appsv.academiclibrary.data.repoImple

import com.appsv.academiclibrary.domain.repo.AllBookRepo
import com.appsv.academiclibrary.model.BookModel
import com.appsv.academiclibrary.model.BooksDeptModel
import com.appsv.academiclibrary.model.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AllBookRepoImpl @Inject constructor(val firebaseDatabase: FirebaseDatabase) : AllBookRepo {

    override fun getAllBooks(): Flow<ResultState<List<BookModel>>> = callbackFlow{

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var items: List<BookModel> = emptyList()

                items = snapshot.children.map { value ->
                    value.getValue<BookModel>()!! as BookModel
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


    override fun getAllCategories(): Flow<ResultState<List<BooksDeptModel>>> = callbackFlow{

        trySend(ResultState.Loading)


        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var items: List<BooksDeptModel> = emptyList()

                items = snapshot.children.map { value ->
                    value.getValue<BooksDeptModel>()!! as BooksDeptModel
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

    override fun getBooksByCategory(category : String): Flow<ResultState<List<BookModel>>> = callbackFlow{

        trySend(ResultState.Loading)

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var items: List<BookModel> = emptyList()

                items = snapshot.children.filter { value ->
                    value.getValue<BookModel>()!!.category ==  category   // it will filter data which has the category :String
                }.map { value ->
                    value.getValue<BookModel>()!!  // then it map
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
}