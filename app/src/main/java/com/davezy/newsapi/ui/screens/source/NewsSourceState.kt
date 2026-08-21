package com.davezy.newsapi.ui.screens.source

import androidx.compose.runtime.Immutable
import com.davezy.newsapi.data.remote.model.NewsSourceModel
import com.davezy.newsapi.ui.util.NavigationEffect
import com.davezy.newsapi.ui.util.UiEffect
import com.davezy.newsapi.ui.util.UiEvent

@Immutable
data class NewsSourceState(
    val isLoading: Boolean = false,
    val sources: List<NewsSourceModel> = emptyList(),
    val error: String? = null
)

sealed interface NewsSourceEvent : UiEvent {
    data object GetSources : NewsSourceEvent
    data class OnSourceClick(val sourceId: String) : NewsSourceEvent
    data object NavigateBack : NewsSourceEvent
}

sealed interface NewsSourceEffect : UiEffect {
    data class NavigateToNewsList(val sourceId: String) : NewsSourceEffect, NavigationEffect
    data object NavigateBack : NewsSourceEffect, NavigationEffect
}
