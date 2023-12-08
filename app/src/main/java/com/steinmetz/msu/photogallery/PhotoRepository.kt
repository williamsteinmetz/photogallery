package com.steinmetz.msu.photogallery

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.steinmetz.msu.photogallery.api.FlickrApi
import com.steinmetz.msu.photogallery.api.GalleryItem
import com.steinmetz.msu.photogallery.api.PhotoResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class  PhotoRepository {
    private val flickrApi: FlickrApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        flickrApi = retrofit.create()
    }

    suspend fun fetchPhotos(): List<GalleryItem> =
        flickrApi.fetchPhotos().photos.galleryItems

    fun galleryItemPagingSource() = GalleryPagingSource()
}
//
//class GalleryPagingSource(val flickrApi: FlickrApi) :
//        PagingSource<Int, PhotoResponse>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoResponse> {
//        val page = params.key ?: GALLERY_STARTING_PAGE_INDEX
//
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, PhotoResponse>): Int? {
//        TODO("Not yet implemented")
//    }
//}




