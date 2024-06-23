package com.example.zebracoffee.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.zebracoffee.domain.entity.Notification
import com.example.zebracoffee.domain.useCases.loyaltyCardUseCase.GetNotificationUseCase
import kotlinx.coroutines.flow.MutableStateFlow


class NotificationPagingSource(
    private val bearerToken: String,
    private val repository: GetNotificationUseCase,
    private val limit: Int = LIMIT,
    private var hasMorePages: MutableStateFlow<Boolean>,
) : PagingSource<Int, Notification>() {
    var currentPage = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notification> {
        val nextOffset = currentPage * limit
        try {
            val responseData = repository.getNotificationMessages(bearerToken, page = currentPage, limit = limit)
            currentPage++
            hasMorePages.value = responseData.results.isNotEmpty()
            return  LoadResult.Page(
                data = responseData.results,
                prevKey = if (nextOffset == FIRST_PAGE || responseData.previous == null) null else (nextOffset - limit).coerceAtLeast(0),
                nextKey = if (responseData.results.isEmpty() || responseData.next == null) null else nextOffset + limit
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Notification>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val FIRST_PAGE = 0
        private const val LIMIT = 10
    }
}