package com.davezy.newsapi.ui.screens.source

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davezy.newsapi.data.remote.model.NewsSourceModel
import com.davezy.newsapi.ui.components.NewsErrorContent
import com.davezy.newsapi.ui.components.NewsTopBar
import com.davezy.newsapi.ui.theme.NewsApiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsSourceScreen(
    state: NewsSourceState,
    onEvent: (NewsSourceEvent) -> Unit
) {
    Scaffold(
        topBar = {
            NewsTopBar(title = "News Sources") { onEvent(NewsSourceEvent.NavigateBack) }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error != null) {
                NewsErrorContent(
                    message = state.error,
                    onRetry = { onEvent(NewsSourceEvent.GetSources) }
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.sources) { source ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clickable { onEvent(NewsSourceEvent.OnSourceClick(source.id)) }
                        ) {
                            Text(
                                text = source.name,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Success")
@Composable
fun NewsSourceScreenSuccessPreview() {
    NewsApiTheme {
        NewsSourceScreen(
            state = NewsSourceState(
                sources = listOf(
                    NewsSourceModel(
                        id = "abc-news",
                        name = "ABC News",
                        description = "Description",
                        url = "https://abcnews.go.com",
                        category = "general",
                        language = "en",
                        country = "us"
                    )
                )
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true, name = "Error")
@Composable
fun NewsSourceScreenErrorPreview() {
    NewsApiTheme {
        NewsSourceScreen(
            state = NewsSourceState(
                error = "Failed to load news sources. Please check your connection."
            ),
            onEvent = {}
        )
    }
}
