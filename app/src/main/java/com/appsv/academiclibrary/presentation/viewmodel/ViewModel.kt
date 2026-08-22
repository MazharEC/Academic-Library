package com.appsv.academiclibrary.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsv.academiclibrary.domain.repo.AllBookRepo
import com.appsv.academiclibrary.model.BookModel
import com.appsv.academiclibrary.model.BooksDeptModel
import com.appsv.academiclibrary.model.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(val repo: AllBookRepo) : ViewModel() {

    private val _state: MutableState<ItemState> = mutableStateOf(ItemState())
    val state: MutableState<ItemState> = _state

    // Kept separate from ItemState on purpose: ItemState gets fully replaced
    // every time the Firebase live listener fires, which would silently
    // wipe the user's search text if it lived inside ItemState.
    private val _searchQuery: MutableState<String> = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    // Derived, read-only, recomputes automatically when state.items or
    // searchQuery changes. No Firebase calls involved — pure in-memory filter.
    val filteredItems: State<List<BookModel>> = derivedStateOf {
        val query = _searchQuery.value.trim()
        val allItems = _state.value.items

        if (query.isBlank()) {
            allItems
        } else {
            allItems.filter { book ->
                book.bookName.contains(query, ignoreCase = true) ||
                        book.bookAuthor.contains(query, ignoreCase = true) ||
                        book.courseName.contains(query, ignoreCase = true)
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun bringAllBooks() {
        viewModelScope.launch {
            repo.getAllBooks().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }

                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.localizedMessage ?: "Something went wrong")
                    }

                    is ResultState.Success -> {
                        _state.value = ItemState(items = it.data)
                    }
                }
            }
        }
    }

    fun bringCategories() {
        viewModelScope.launch {
            repo.getAllCategories().collect {
                when (it) {
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }

                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.localizedMessage ?: "Something went wrong")
                    }

                    is ResultState.Success -> {
                        _state.value = ItemState(category = it.data)
                    }
                }
            }
        }
    }

    fun bringAllBooksByCategory(category: String) {
        viewModelScope.launch {
            repo.getBooksByCategory(category).collect {
                when (it) {
                    is ResultState.Loading -> {
                        _state.value = ItemState(isLoading = true)
                    }

                    is ResultState.Error -> {
                        _state.value = ItemState(error = it.exception.localizedMessage ?: "Something went wrong")
                    }

                    is ResultState.Success -> {
                        _state.value = ItemState(items = it.data)
                    }
                }
            }
        }
    }
}

data class ItemState(
    val isLoading: Boolean = false,
    val items: List<BookModel> = emptyList(),
    val error: String = "",
    val category: List<BooksDeptModel> = emptyList()
)