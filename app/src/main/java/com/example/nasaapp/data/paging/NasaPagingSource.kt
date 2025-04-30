package com.example.nasaapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nasaapp.domain.model.SearchItem
import com.example.nasaapp.domain.repository.NasaRepository


class NasaPagingSource(
    private val repository: NasaRepository,
    private val query: String,
    private val mediaType: String?
) : PagingSource<Int, SearchItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.searchImages(query, mediaType, currentPage)

            LoadResult.Page(
                data = response,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (response.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}