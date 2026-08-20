package com.appsv.academiclibrary.presentation.AllBooksScreeen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.appsv.academiclibrary.presentation.BookViewModel
import com.appsv.academiclibrary.presentation.Effects.AnimatedShimmer
import com.appsv.academiclibrary.presentation.UiComponent.BookCard

@Composable
fun AllBooksScreen(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    LaunchedEffect(Unit) {
        viewModel.bringAllBooks()
    }

    val res = viewModel.state.value

    when {
        res.isLoading -> {

            Column(modifier = modifier.fillMaxSize()) {
                LazyColumn {
                    items(10) {
                        AnimatedShimmer()
                    }
                }
            }
        }

        res.error.isNotEmpty() -> {

            Text(text = res.error, modifier = modifier)
        }

        res.items.isNotEmpty() -> {

            Column(modifier = modifier.fillMaxSize()) {
                LazyColumn(modifier = modifier.fillMaxSize()) {
                    items(res.items) {
                        BookCard(
                            imageUrl = it.bookImage,
                            title = it.bookName,
                            author = it.bookAuthor,
                            description = it.bookDescription,
                            navHostController = navHostController,
                            bookUrl = it.bookUrl
                        )
                    }
                }
            }
        }

        else -> Text(text = "No Books available", modifier = modifier)
    }
}