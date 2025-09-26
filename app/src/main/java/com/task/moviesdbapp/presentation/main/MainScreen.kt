package com.task.moviesdbapp.presentation.main


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.task.moviesdbapp.presentation.all.AllMoviesScreen
import com.task.moviesdbapp.presentation.favorites.FavoritesScreen

@Composable
fun MainScreen() {
    var tab by remember { mutableStateOf(0) }
    val titles = listOf("Films", "Favorites")

    Scaffold { padding ->
        Column(Modifier.fillMaxSize()) {
            TabRow(selectedTabIndex = tab) {
                titles.forEachIndexed { i, t ->
                    Tab(selected = tab == i, onClick = { tab = i }, text = { Text(t) })
                }
            }
            when (tab) {
                0 -> AllMoviesScreen()
                else -> FavoritesScreen()
            }
        }
    }
}