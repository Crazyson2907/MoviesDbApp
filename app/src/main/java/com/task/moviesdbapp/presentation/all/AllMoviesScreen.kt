package com.task.moviesdbapp.presentation.all

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.task.moviesdbapp.domain.core.model.Movie
import java.time.format.DateTimeFormatter

@Composable
fun AllMoviesScreen(vm: AllMoviesViewModel = hiltViewModel()) {
    val state by vm.container.stateFlow.collectAsState()
    val refreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

    if (state.isLoading && state.items.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    SwipeRefresh(state = refreshState, onRefresh = vm::onRefresh) {
        MovieList(
            movies = state.items,
            onToggle = { id, fav -> vm.onToggleFavorite(id, fav) }
        )
    }

    state.error?.let {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No movies yet")
        }
    }
}

@Composable
private fun MovieList(
    movies: List<Movie>,
    onToggle: (Int, Boolean) -> Unit
) {
    val monthFmt = DateTimeFormatter.ofPattern("MMM yyyy")
    var lastHeader: String? by remember { mutableStateOf(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        itemsIndexed(movies, key = { _, m -> m.id }) { _, movie ->
            val header = movie.releaseDate?.format(monthFmt) ?: "Unknown"
            if (header != lastHeader) {
                lastHeader = header
                ItemHeader(header)
            }
            ItemRow(movie, onToggle)
        }
    }
}

@Composable
private fun ItemHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun ItemRow(movie: Movie, onToggle: (Int, Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val posterUrl = movie.posterPath?.let { "https://image.tmdb.org/t/p/w185$it" }
        Image(
            painter = rememberAsyncImagePainter(posterUrl),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(movie.title, style = MaterialTheme.typography.titleMedium)
            Text(
                movie.overview.take(120),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("★ ${"%.1f".format(movie.voteAverage)}", style = MaterialTheme.typography.labelMedium)
                Spacer(Modifier.width(12.dp))
                Text(if (movie.isFavorite) "Bookmarked" else "Like", style = MaterialTheme.typography.labelMedium)
            }
        }
        IconButton(onClick = { onToggle(movie.id, !movie.isFavorite) }) {
            Icon(
                imageVector = if (movie.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null
            )
        }
    }
}