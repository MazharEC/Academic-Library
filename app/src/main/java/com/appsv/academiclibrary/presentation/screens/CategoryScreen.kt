package com.appsv.academiclibrary.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.appsv.academiclibrary.presentation.viewmodel.BookViewModel
import com.appsv.academiclibrary.presentation.components.CategoryShimmer
import com.appsv.academiclibrary.presentation.components.BookCategoryCard

@Composable
fun CategoryScreen(viewModel: BookViewModel = hiltViewModel(), navHostController: NavHostController) {

    LaunchedEffect(Unit) {
        viewModel.bringCategories()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val res = viewModel.state.value

        when {

            res.isLoading -> {

                Column(modifier = Modifier.fillMaxSize()) {
                    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
                        items(10) {
                            CategoryShimmer()
                        }
                    }
                }

            }

            res.error.isNotEmpty() -> {
                Text(text = res.error)
            }

            res.category.isNotEmpty() -> {

                LazyVerticalGrid(
                    GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(res.category) {
                        BookCategoryCard(
                            imageUrl = it.deptImageUrl,
                            category = it.deptName,
                            navHostController = navHostController
                        )
                    }
                }
            }
        }
    }
}