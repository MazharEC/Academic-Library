package com.appsv.academiclibrary.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.launch
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.appsv.academiclibrary.R
import com.appsv.academiclibrary.presentation.components.BookSearchBar
import com.appsv.academiclibrary.presentation.viewmodel.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: BookViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val urlHandler = LocalUriHandler.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val focusManager = LocalFocusManager.current
    val searchQuery by viewModel.searchQuery

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(250.dp)
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    NavigationDrawerItem(
                        label = { Text("Home") },
                        selected = true,
                        icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home") },
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                        }
                    )
                    Divider()
                    NavigationDrawerItem(
                        label = { Text("Versoin 1.0") },
                        selected = false,
                        icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Versoin 1.0") },
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            Toast.makeText(context, "Versoin 1.0", Toast.LENGTH_SHORT).show()
                        }
                    )
                    Divider()
                    NavigationDrawerItem(
                        label = { Text("Contact Me") },
                        selected = false,
                        icon = { Icon(imageVector = Icons.Filled.Email, contentDescription = "Contact Me") },
                        onClick = { urlHandler.openUri("https://www.linkedin.com/in/mazhar-tuhin") }
                    )
                    Divider()
                    NavigationDrawerItem(
                        label = { Text("Source Code") },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.library_logo),
                                contentDescription = "Source Code",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        onClick = { urlHandler.openUri("https://github.com/MazharEC") }
                    )
                    Divider()
                    NavigationDrawerItem(
                        label = { Text("Bug Report") },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.library_logo),
                                contentDescription = "Source Code",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        onClick = { urlHandler.openUri("https://github.com/MazharEC?tab=repositories") }
                    )
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Book Library",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Open Drawer"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                BookSearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChange = viewModel::onSearchQueryChange,
                    onImeSearch = { focusManager.clearFocus() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                TabScreen(navHostController = navHostController)
            }
        }
    }
}