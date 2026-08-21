package com.davezy.newsapi.data.repository

import com.davezy.newsapi.data.remote.NewsApiService
import com.davezy.newsapi.data.remote.model.NewsResponse
import com.davezy.newsapi.data.remote.model.NewsSourcesResponse
import com.davezy.newsapi.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: NewsApiService
) : NewsRepository {

    override suspend fun getSources(category: String): Result<NewsSourcesResponse> {
        return try {
            val response = apiService.getSources(category)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEverything(
        sources: String,
        page: Int,
        pageSize: Int,
        search: String?
    ): Result<NewsResponse> {
        return try {
            val response = apiService.getEverything(
                sources = sources,
                page = page,
                pageSize = pageSize,
                search = search
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
