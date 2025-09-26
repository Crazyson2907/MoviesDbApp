package com.task.moviesdbapp.presentation.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.task.moviesdbapp.presentation.all.ItemRow


@Composable
fun FavoritesScreen(vm: FavoritesViewModel = hiltViewModel()) {
    val state by vm.container.stateFlow.collectAsState()
    if (state.items.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No favorites yet")
        }
        return
    }
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 16.dp)) {
        items(state.items, key = { it.id }) { m ->
            ItemRow(m) { id, fav -> vm.onToggleFavorite(id, fav) }
        }
    }
}