package com.steinmetz.msu.photogallery

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.steinmetz.msu.photogallery.api.FlickrApi
import com.steinmetz.msu.photogallery.api.GalleryItem
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

private const val TAG = "PhotoRepository"
class  PhotoRepository {
    private val flickrApi: FlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        flickrApi = retrofit.create()
    }
    fun galleryItemPagingSource() = GalleryPagingSource(flickrApi)
}
class GalleryPagingSource(private val apiService: FlickrApi) : PagingSource<Int, GalleryItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {

        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {

            val response = apiService.fetchPhotos(pageNumber, params.loadSize)
            val photos = response.photos.galleryItems

            LoadResult.Page(
                data = photos,
                prevKey = if(pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = if (photos.isEmpty()) null else pageNumber + 1
            )
        } catch (exception: Exception) {
            Log.e(TAG, "Error fetching photos", exception)
            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        return  state.anchorPosition?.let {anchorPosition ->
        val anchorPage = state.closestPageToPosition(anchorPosition)
         anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    init {
        Log.d(TAG, "PhotoRepository utilized")
    }
}

private const val STARTING_PAGE_INDEX = 0



