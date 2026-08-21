package com.davezy.newsapi.ui.screens.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davezy.newsapi.ui.components.NewsTopBar
import com.davezy.newsapi.ui.theme.NewsApiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCategoryScreen(
    state: NewsCategoryState,
    onEvent: (NewsCategoryEvent) -> Unit
) {
    Scaffold(topBar = { NewsTopBar("News Categories") }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.categories) { category ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { onEvent(NewsCategoryEvent.OnCategoryClick(category)) }
                    ) {
                        Text(
                            text = category.replaceFirstChar { it.uppercase() },
                            modifier = Modifier.padding(24.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Success")
@Composable
fun NewsCategoryScreenSuccessPreview() {
    NewsApiTheme {
        NewsCategoryScreen(
            state = NewsCategoryState(),
            onEvent = {}
        )
    }
}
