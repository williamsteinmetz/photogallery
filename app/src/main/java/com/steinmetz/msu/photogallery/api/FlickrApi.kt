package com.steinmetz.msu.photogallery.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "da0272d96c710535e1abf6f0877757f2"

interface FlickrApi {
    @GET("services/rest/?method=flickr.interestingness.getList" +
    "&api_key=$API_KEY" +
    "&format=json" +
    "&nojsoncallback=1" +
    "&extras=url_s"
    )
    suspend fun fetchPhotos(
        @Query("page") pageNumber: Int,
        @Query("perpage") loadSize: Int
    ): FlickrResponse
}