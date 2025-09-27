package com.task.moviesdbapp.presentation.main

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun EmptyFirstRunOffline(onRetry: () -> Unit) {
    val ctx = LocalContext.current
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
            Text("You're offline", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text(
                "Connect to the internet to load the first page. " +
                        "Once loaded, it will be available offline.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = onRetry) { Text("Retry") }
                OutlinedButton(onClick = {
                    ctx.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                }) { Text("Open settings") }
            }
        }
    }
}