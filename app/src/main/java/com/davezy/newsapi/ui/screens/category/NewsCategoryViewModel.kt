package com.davezy.newsapi.ui.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsCategoryViewModel @Inject constructor() : ViewModel() {

    private val _viewState = MutableStateFlow(NewsCategoryState())
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = Channel<NewsCategoryEffect>(Channel.BUFFERED)
    val viewEffect = _viewEffect.receiveAsFlow()

    fun onEvent(event: NewsCategoryEvent) {
        when (event) {
            is NewsCategoryEvent.OnCategoryClick -> {
                viewModelScope.launch {
                    _viewEffect.send(NewsCategoryEffect.NavigateToNewsList(event.category))
                }
            }
        }
    }
}
