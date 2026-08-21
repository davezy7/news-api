package com.davezy.newsapi.ui.screens.article_list

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.davezy.newsapi.data.paging.NewsArticleListPagingSource
import com.davezy.newsapi.domain.repository.NewsRepository
import com.davezy.newsapi.ui.navigation.Routes.NewsArticleList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class NewsArticleListViewModel @Inject constructor(
    private val repository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route = savedStateHandle.toRoute<NewsArticleList>()

    private val _viewState = MutableStateFlow(
        NewsArticleListState(articles = createPagerFlow(null))
    )
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = Channel<NewsArticleListEffect>(Channel.BUFFERED)
    val viewEffect = _viewEffect.receiveAsFlow()

    init {
        observeTextField()
    }

    fun onEvent(event: NewsArticleListEvent) {
        when (event) {
            is NewsArticleListEvent.NavigateBack -> {
                viewModelScope.launch {
                    _viewEffect.send(NewsArticleListEffect.NavigateBack)
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeTextField() = snapshotFlow { _viewState.value.searchFieldState.text.toString() }
        .debounce(500.milliseconds)
        .distinctUntilChanged()
        .onEach { query ->
            val searchQuery = query.ifBlank { null }

            // Recreate the pager with the new query
            _viewState.update { it.copy(articles = createPagerFlow(searchQuery)) }
        }
        .launchIn(viewModelScope)

    private fun createPagerFlow(query: String?) = Pager(PagingConfig(pageSize = 20)) {
        NewsArticleListPagingSource(
            repository = repository,
            sourceId = route.sourceId,
            search = query
        )
    }.flow.cachedIn(viewModelScope)
}
