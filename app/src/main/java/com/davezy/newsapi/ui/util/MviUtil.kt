package com.davezy.newsapi.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

interface UiEvent
interface UiEffect
interface NavigationEffect : UiEffect

@Composable
fun <T> EffectCollector(
    flow: Flow<T>,
    onEffect: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { effect ->
                onEffect(effect)
            }
        }
    }
}
