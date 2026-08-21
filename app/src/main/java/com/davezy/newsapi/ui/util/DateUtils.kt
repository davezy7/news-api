package com.davezy.newsapi.ui.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.formatDate(): String = runCatching {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.forLanguageTag("id-ID")).apply {
        timeZone = TimeZone.getDefault()
    }

    val date = inputFormat.parse(this)
    date?.let { outputFormat.format(it) } ?: this
}.getOrElse { this }
