package com.appsv.academiclibrary.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.appsv.academiclibrary.presentation.viewmodel.BookViewModel
import com.appsv.academiclibrary.presentation.components.AnimatedShimmer
import com.appsv.academiclibrary.presentation.components.BookCard
import com.appsv.academiclibrary.presentation.components.BookSearchBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksByCategoryScreen(
    viewModel: BookViewModel = hiltViewModel(),
    category: String,
    navHostController: NavHostController
) {

    LaunchedEffect(Unit) {
        viewModel.bringAllBooksByCategory(category)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val focusManager = LocalFocusManager.current
    val searchQuery by viewModel.searchQuery

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(category) },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        val res = viewModel.state.value
        val filteredItems = viewModel.filteredItems.value

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            BookSearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                onImeSearch = { focusManager.clearFocus() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            when {
                res.isLoading -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyColumn {
                            items(10) {
                                AnimatedShimmer()
                            }
                        }
                    }
                }

                res.error.isNotEmpty() -> {
                    Text(text = res.error)
                }

                res.items.isEmpty() -> {
                    Text(text = "No books available")
                }

                filteredItems.isEmpty() -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = "No results found")
                        Text(text = "Try searching with a different keyword")
                    }
                }

                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(filteredItems) {
                                BookCard(
                                    imageUrl = it.bookImage,
                                    title = it.bookName,
                                    description = it.bookDescription,
                                    bookUrl = it.bookUrl,
                                    author = it.bookAuthor,
                                    navHostController = navHostController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}