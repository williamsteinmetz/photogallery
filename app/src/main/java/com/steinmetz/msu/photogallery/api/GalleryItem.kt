package com.steinmetz.msu.photogallery.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GalleryItem(
    val id: Int,
    val title: String,
    @Json(name = "url_s") val url: String,
)
