package com.davezy.newsapi.data.remote

import com.davezy.newsapi.data.remote.model.NewsResponse
import com.davezy.newsapi.data.remote.model.NewsSourcesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines/sources")
    suspend fun getSources(@Query("category") category: String): NewsSourcesResponse

    @GET("v2/everything")
    suspend fun getEverything(
        @Query("sources") sources: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("language") language: String = "en",
        @Query("q") search: String?
    ): NewsResponse
}
