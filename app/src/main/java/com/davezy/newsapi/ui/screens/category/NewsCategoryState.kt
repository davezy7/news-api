package com.davezy.newsapi.ui.screens.category

import androidx.compose.runtime.Immutable
import com.davezy.newsapi.ui.util.NavigationEffect
import com.davezy.newsapi.ui.util.UiEffect
import com.davezy.newsapi.ui.util.UiEvent

@Immutable
data class NewsCategoryState(
    val categories: List<String> = listOf(
        "business", "entertainment", "general", "health", "science", "sports", "technology"
    )
)

sealed interface NewsCategoryEvent : UiEvent {
    data class OnCategoryClick(val category: String) : NewsCategoryEvent
}

sealed interface NewsCategoryEffect : UiEffect {
    data class NavigateToNewsList(val category: String) : NewsCategoryEffect, NavigationEffect
}
