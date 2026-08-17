package com.appsv.academiclibrary.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes{

    @Serializable
    object HomeScreen

    @Serializable
    data class BooksByCategory(val category : String)

    @Serializable
    data class ShowPdfScreen(val url : String)
}