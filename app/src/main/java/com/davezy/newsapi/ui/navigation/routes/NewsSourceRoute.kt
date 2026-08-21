package com.davezy.newsapi.ui.navigation.routes

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.davezy.newsapi.ui.navigation.Routes.NewsSource
import com.davezy.newsapi.ui.screens.source.NewsSourceEffect
import com.davezy.newsapi.ui.screens.source.NewsSourceScreen
import com.davezy.newsapi.ui.screens.source.NewsSourceViewModel
import com.davezy.newsapi.ui.util.EffectCollector

fun NavController.navigateToNewsSource(category: String) {
    navigate(NewsSource(category))
}

fun NavGraphBuilder.buildNewsSourceRoute(
    navController: NavController
) = composable<NewsSource> {
    val viewModel = hiltViewModel<NewsSourceViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    EffectCollector(viewModel.viewEffect) { effect ->
        when (effect) {
            is NewsSourceEffect.NavigateToNewsList -> navController.navigateToNewsArticleList(effect.sourceId)
            is NewsSourceEffect.NavigateBack -> navController.popBackStack()
        }
    }

    NewsSourceScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}
