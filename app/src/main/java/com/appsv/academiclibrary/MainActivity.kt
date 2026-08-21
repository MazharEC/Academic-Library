package com.appsv.academiclibrary

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.appsv.academiclibrary.presentation.navigation.NavGraph
import com.appsv.academiclibrary.ui.theme.AcademicLibraryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcademicLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}


@Composable

fun MainScreen(){

    val navHostController = rememberNavController()
    val showsplash = remember {

        mutableStateOf(true)


    }

    LaunchedEffect(Unit) {

        Handler(Looper.getMainLooper()).postDelayed({
            showsplash.value = false
        },3000)
    }

    if (showsplash.value){

        SplashScreen()
    }
    else{
        NavGraph(navHostController = navHostController)
    }
}

@Composable
fun SplashScreen(){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.Center

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Image(painter = painterResource(id = R.drawable.library_logo), contentDescription ="splash logo",
                modifier = Modifier.size(300.dp)

            )
            BasicText(
                text = "Welcome to Mazhar's Book Library",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )


            )

        }
    }
}

