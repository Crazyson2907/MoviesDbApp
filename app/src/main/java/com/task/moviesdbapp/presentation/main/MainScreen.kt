package com.task.moviesdbapp.presentation.main


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.task.moviesdbapp.presentation.all.AllMoviesScreen
import com.task.moviesdbapp.presentation.auth.AccountChip
import com.task.moviesdbapp.presentation.favorites.FavoritesScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var tab by rememberSaveable { mutableStateOf(0) }
    val titles = listOf("Films", "Favorites")

    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                title = { Text("Movies") },
                actions = { AccountChip(Modifier.padding(end = 8.dp)) },
                // Ensure the bar itself sits below the status bar
                modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars)
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            TabRow(selectedTabIndex = tab) {
                titles.forEachIndexed { i, t ->
                    Tab(
                        selected = tab == i,
                        onClick = { tab = i },
                        text = { Text(t) }
                    )
                }
            }
            when (tab) {
                0 -> AllMoviesScreen()
                else -> FavoritesScreen()
            }
        }
    }
}