package com.davezy.newsapi.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.davezy.newsapi.ui.navigation.Routes
import com.davezy.newsapi.ui.navigation.routes.buildNewsCategoryRoute
import com.davezy.newsapi.ui.navigation.routes.buildNewsSourceRoute
import com.davezy.newsapi.ui.navigation.routes.buildNewsArticleListRoute

@Composable
fun NewsNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.NewsCategory,
        modifier = modifier
    ) {
        buildNewsCategoryRoute(navController = navController)
        buildNewsSourceRoute(navController = navController)
        buildNewsArticleListRoute(navController = navController)
    }
}
