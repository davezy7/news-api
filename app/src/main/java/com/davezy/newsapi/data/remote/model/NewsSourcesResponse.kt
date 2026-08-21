package com.davezy.newsapi.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsSourcesResponse(
    @SerialName("status") val status: String,
    @SerialName("sources") val sources: List<NewsSourceModel>
)
