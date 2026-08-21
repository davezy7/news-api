package com.davezy.newsapi.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsArticleModel(
    @SerialName("author") val author: String?,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String?,
    @SerialName("url") val url: String,
    @SerialName("urlToImage") val urlToImage: String?,
    @SerialName("publishedAt") val publishedAt: String,
    @SerialName("content") val content: String?
)
