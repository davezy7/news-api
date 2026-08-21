package com.davezy.newsapi.ui.navigation.routes

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.davezy.newsapi.ui.navigation.Routes
import com.davezy.newsapi.ui.navigation.Routes.NewsCategory
import com.davezy.newsapi.ui.screens.category.NewsCategoryScreen
import com.davezy.newsapi.ui.screens.category.NewsCategoryViewModel
import com.davezy.newsapi.ui.screens.category.NewsCategoryEffect
import com.davezy.newsapi.ui.util.EffectCollector

fun NavController.navigateToNewsCategory() {
    navigate(NewsCategory)
}

fun NavGraphBuilder.buildNewsCategoryRoute(
    navController: NavController
) = composable<NewsCategory> {
    val viewModel = hiltViewModel<NewsCategoryViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    EffectCollector(viewModel.viewEffect) { effect ->
        when (effect) {
            is NewsCategoryEffect.NavigateToNewsList -> navController.navigateToNewsSource(effect.category)
        }
    }

    NewsCategoryScreen(
        state = state,
        onEvent = viewModel::onEvent
    )
}
