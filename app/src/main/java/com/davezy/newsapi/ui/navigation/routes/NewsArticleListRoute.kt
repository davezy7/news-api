package com.davezy.newsapi.ui.navigation.routes

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.davezy.newsapi.ui.navigation.Routes.NewsArticleList
import com.davezy.newsapi.ui.screens.article_list.NewsArticleListEffect
import com.davezy.newsapi.ui.screens.article_list.NewsArticleListScreen
import com.davezy.newsapi.ui.screens.article_list.NewsArticleListViewModel
import com.davezy.newsapi.ui.util.EffectCollector

fun NavController.navigateToNewsArticleList(sourceId: String) {
    navigate(NewsArticleList(sourceId))
}

fun NavGraphBuilder.buildNewsArticleListRoute(
    navController: NavController
) = composable<NewsArticleList> {
    val viewModel = hiltViewModel<NewsArticleListViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    EffectCollector(viewModel.viewEffect) { effect ->
        when (effect) {
            is NewsArticleListEffect.NavigateToDetail -> {
                // Navigate to detail screen (to be implemented later)
            }
            is NewsArticleListEffect.NavigateBack -> navController.popBackStack()
        }
    }

    NewsArticleListScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}
