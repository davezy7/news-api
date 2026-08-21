package com.davezy.newsapi.ui.screens.article_list

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.davezy.newsapi.data.remote.model.NewsArticleModel
import com.davezy.newsapi.ui.util.NavigationEffect
import com.davezy.newsapi.ui.util.UiEffect
import com.davezy.newsapi.ui.util.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class NewsArticleListState(
    val articles: Flow<PagingData<NewsArticleModel>> = emptyFlow(),
    val searchFieldState: TextFieldState = TextFieldState()
)

sealed interface NewsArticleListEvent : UiEvent {
    data object NavigateBack : NewsArticleListEvent
}

sealed interface NewsArticleListEffect : UiEffect {
    data class NavigateToDetail(val url: String) : NewsArticleListEffect, NavigationEffect
    data object NavigateBack : NewsArticleListEffect, NavigationEffect
}
