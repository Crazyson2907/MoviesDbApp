package com.task.moviesdbapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.task.moviesdbapp.domain.cache.core.MoviesRepository
import com.task.moviesdbapp.presentation.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var repo: MoviesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = 0x00000000,
                darkScrim  = 0x00000000
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = 0x00000000,
                darkScrim  = 0x00000000
            )
        )
        setContent { MaterialTheme { MainScreen() } }
    }
}