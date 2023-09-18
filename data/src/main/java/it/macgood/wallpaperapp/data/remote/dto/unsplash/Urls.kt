package it.macgood.wallpaperapp.data.remote.dto.unsplash

import com.google.gson.annotations.SerializedName

data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    @SerializedName("small_s3")
    val smallS3: String,
    val thumb: String
)