package com.davezy.newsapi.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.davezy.newsapi.data.remote.model.NewsArticleModel
import com.davezy.newsapi.domain.repository.NewsRepository

class NewsArticleListPagingSource(
    private val repository: NewsRepository,
    private val sourceId: String,
    private val search: String? = null
) : PagingSource<Int, NewsArticleModel>() {

    override fun getRefreshKey(state: PagingState<Int, NewsArticleModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticleModel> {
        val page = params.key ?: 1
        val pageSize = params.loadSize

        return runCatching {
            repository.getEverything(sourceId, page, pageSize, search).fold(
                onSuccess = { response ->
                    LoadResult.Page(
                        data = response.articles,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.articles.isEmpty()) null else page + 1
                    )
                },
                onFailure = { LoadResult.Error(it) }
            )
        }.getOrElse { LoadResult.Error(it)  }
    }
}
