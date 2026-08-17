package com.appsv.academiclibrary.presentation.AllBooksByCategory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.appsv.academiclibrary.presentation.Effects.AnimatedShimmer
import com.appsv.academiclibrary.presentation.UiComponent.BookCard
import com.appsv.academiclibrary.presentation.ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BooksByCategoryScreen(
    viewModel: ViewModel = hiltViewModel(),
    category: String,
    navHostController: NavHostController
) {

    LaunchedEffect(Unit) {
        viewModel.BringAllBooksByCategory(category)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text(category) },
                navigationIcon = {
                    IconButton(onClick = {navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )

        }
    )
    { innerPadding ->
        val res = viewModel.state.value

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {

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

                res.items.isNotEmpty() -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(res.items) {
                                BookCard(
                                    imageUrl = it.image,
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
                else -> {
                    Text(text = "No books available")
                }
            }
        }
    }
}