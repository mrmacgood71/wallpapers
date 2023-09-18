package it.macgood.wallpaperapp.data.remote.dto.unsplash

import com.google.gson.annotations.SerializedName

data class ImageLinks(
    val download: String,
    @SerializedName("download_location")
    val downloadLocation: String,
    val html: String,
    val self: String
)