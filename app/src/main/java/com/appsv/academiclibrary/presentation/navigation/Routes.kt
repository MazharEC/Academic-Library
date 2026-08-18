package com.appsv.academiclibrary.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data class BooksByCategory(val category: String) : Routes()

    @Serializable
    data class ShowPdfScreen(val url: String) : Routes()
}