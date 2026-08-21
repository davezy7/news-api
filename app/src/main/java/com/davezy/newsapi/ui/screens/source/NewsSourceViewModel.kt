package com.davezy.newsapi.ui.screens.source

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.davezy.newsapi.domain.repository.NewsRepository
import com.davezy.newsapi.ui.navigation.Routes.NewsSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSourceViewModel @Inject constructor(
    private val repository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route = savedStateHandle.toRoute<NewsSource>()

    private val _viewState = MutableStateFlow(NewsSourceState())
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = Channel<NewsSourceEffect>(Channel.BUFFERED)
    val viewEffect = _viewEffect.receiveAsFlow()

    init {
        onEvent(NewsSourceEvent.GetSources)
    }

    fun onEvent(event: NewsSourceEvent) {
        when (event) {
            is NewsSourceEvent.GetSources -> getSources()
            is NewsSourceEvent.OnSourceClick -> {
                viewModelScope.launch {
                    _viewEffect.send(NewsSourceEffect.NavigateToNewsList(event.sourceId))
                }
            }
            is NewsSourceEvent.NavigateBack -> viewModelScope.launch {
                _viewEffect.send(NewsSourceEffect.NavigateBack)
            }
        }
    }

    private fun getSources() {
        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true, error = null) }
            repository.getSources(route.category)
                .onSuccess { response ->
                    _viewState.update { it.copy(isLoading = false, sources = response.sources) }
                }
                .onFailure { error ->
                    _viewState.update {
                        it.copy(isLoading = false, error = error.message ?: "Unknown error")
                    }
                }
        }
    }
}
