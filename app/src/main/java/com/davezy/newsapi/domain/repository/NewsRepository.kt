package com.davezy.newsapi.domain.repository

import com.davezy.newsapi.data.remote.model.NewsResponse
import com.davezy.newsapi.data.remote.model.NewsSourcesResponse

interface NewsRepository {

    suspend fun getSources(category: String): Result<NewsSourcesResponse>

    suspend fun getEverything(sources: String, page: Int, pageSize: Int, search: String? = null): Result<NewsResponse>
}
