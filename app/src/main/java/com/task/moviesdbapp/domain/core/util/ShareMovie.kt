package com.task.moviesdbapp.domain.core.util

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

fun Context.shareMovie(title: String, url: String) {
    val text = if (title.isBlank()) url else "$title\n$url"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    ContextCompat.startActivity(this, Intent.createChooser(intent, "Share via"), null)
}