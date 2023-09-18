package it.macgood.wallpaperapp.data.remote.dto.unsplash

import com.google.gson.annotations.SerializedName

data class Sponsorship(
    @SerializedName("impression_urls")
    val impressionUrls: List<String>,
    val sponsor: User,
    val tagline: String,
    @SerializedName("tagline_url")
    val taglineUrl: String
)