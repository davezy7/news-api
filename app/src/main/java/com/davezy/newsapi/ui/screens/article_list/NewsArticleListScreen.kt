package com.davezy.newsapi.ui.screens.article_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.davezy.newsapi.data.remote.model.NewsArticleModel
import com.davezy.newsapi.ui.components.ArticleBottomSheet
import com.davezy.newsapi.ui.components.NewsEmptyState
import com.davezy.newsapi.ui.components.NewsErrorContent
import com.davezy.newsapi.ui.components.NewsTopBar
import com.davezy.newsapi.ui.components.SearchBar
import com.davezy.newsapi.ui.theme.NewsApiTheme
import com.davezy.newsapi.ui.util.formatDate
import com.davezy.newsapi.ui.util.shimmer
import com.davezy.newsapi.ui.util.shimmerBrush
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsArticleListScreen(
    state: NewsArticleListState,
    onEvent: (NewsArticleListEvent) -> Unit
) {
    val pagingItems = state.articles.collectAsLazyPagingItems()
    val shimmerBrush = shimmerBrush()

    var selectedArticle by rememberSaveable { mutableStateOf("") }

    ArticleBottomSheet(
        url = selectedArticle,
        onDismiss = { selectedArticle = "" }
    )

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                NewsTopBar(title = "Articles") { onEvent(NewsArticleListEvent.NavigateBack) }
                SearchBar(
                    state = state.searchFieldState,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                when (pagingItems.loadState.refresh) {
                    is LoadState.Loading -> {
                        items(10) {
                            ArticleShimmerItem(brush = shimmerBrush)
                        }
                    }

                    is LoadState.NotLoading if pagingItems.itemCount == 0 -> {
                        item { NewsEmptyState(modifier = Modifier.fillParentMaxSize()) }
                    }

                    else -> {
                        items(pagingItems.itemCount) { index ->
                            pagingItems[index]?.let { article ->
                                ArticleItem(
                                    article = article,
                                    brush = shimmerBrush,
                                    onClick = { selectedArticle = article.url }
                                )
                            }
                        }
                    }
                }

                pagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Error -> {
                            val error = pagingItems.loadState.refresh as LoadState.Error
                            item {
                                NewsErrorContent(
                                    message = error.error.localizedMessage ?: "Unknown Error",
                                    onRetry = { retry() }
                                )
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = pagingItems.loadState.append as LoadState.Error
                            item {
                                NewsErrorContent(
                                    message = error.error.localizedMessage ?: "Unknown Error",
                                    onRetry = { retry() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleShimmerItem(brush: Brush) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmer(brush)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(20.dp)
                            .shimmer(brush)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .height(14.dp)
                            .shimmer(brush)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(14.dp)
                            .shimmer(brush)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .shimmer(brush)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .shimmer(brush)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(16.dp)
                    .shimmer(brush)
            )
        }
    }
}

@Composable
fun ArticleItem(
    article: NewsArticleModel,
    brush: Brush,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SubcomposeAsyncImage(
                    model = article.urlToImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .shimmer(brush)
                        )
                    },
                    error = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.BrokenImage,
                                contentDescription = "Image failed to load",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = article.author ?: "Unknown Author",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = article.publishedAt.formatDate(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true, name = "Success")
@Composable
fun NewsArticleListScreenSuccessPreview() {
    NewsApiTheme {
        NewsArticleListScreen(
            state = NewsArticleListState(
                articles = flowOf(
                    PagingData.from(
                        data = listOf(
                            NewsArticleModel(
                                author = "Joe Rossignol",
                                title = "Apple Sued by Customers Who Lost Combined $1.8 Million Through Fake Bitcoin Wallet in App Store",
                                description = "Apple is facing a new lawsuit from three customers who allegedly lost a combined $1.8 million after falling victim to a fake Bitcoin wallet app in the App Store.",
                                url = "url1",
                                urlToImage = "https://images.macrumors.com/t/uqecFasuc9ZRQVgUmv6wrPy_1SE=/1920x/article-new/2021/03/apple-bitcoin-app-scam.jpg",
                                publishedAt = "2026-07-25T18:08:31Z",
                                content = "Content"
                            ),
                            NewsArticleModel(
                                author = "Jane Doe",
                                title = "Another Interesting News Story",
                                description = "This is a short description of another news story that is quite interesting.",
                                url = "url2",
                                urlToImage = null,
                                publishedAt = "2026-08-20T10:00:00Z",
                                content = "Content"
                            )
                        ),
                        // FORCE the LoadState to NotLoading so the preview doesn't get stuck on Shimmer
                        sourceLoadStates = LoadStates(
                            refresh = LoadState.NotLoading(endOfPaginationReached = false),
                            prepend = LoadState.NotLoading(endOfPaginationReached = false),
                            append = LoadState.NotLoading(endOfPaginationReached = false)
                        )
                    )
                )
            ),
            onEvent = {}
        )
    }
}
