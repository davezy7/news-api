package com.davezy.newsapi.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes {

    @Serializable
    data object NewsCategory : Routes

    @Serializable
    data class NewsSource(val category: String) : Routes

    @Serializable
    data class NewsArticleList(val sourceId: String) : Routes
}
