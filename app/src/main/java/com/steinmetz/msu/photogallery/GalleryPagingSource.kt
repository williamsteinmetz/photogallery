package com.steinmetz.msu.photogallery

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.steinmetz.msu.photogallery.api.GalleryItem
import java.lang.Integer.max

private const val STARTING_KEY = 0
class GalleryPagingSource : PagingSource<Int, GalleryItem>() {
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        val start = params.key ?: STARTING_KEY
        val range = start.until(start + params.loadSize)

        return LoadResult.Page(
            data = range.map { number ->
                GalleryItem(
                    id = number,
                    title = "This is the image $number title",
                    url = ""
                )
            },

            prevKey = when (start) {
                STARTING_KEY -> null
                else -> ensureValidKey(key = range.first - params.loadSize)
            },
            nextKey = range.last + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val galleryItem = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = galleryItem.id - (state.config.pageSize / 2))
    }
}