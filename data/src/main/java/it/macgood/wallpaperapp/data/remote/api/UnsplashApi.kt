package it.macgood.wallpaperapp.data.remote.api

import it.macgood.wallpaperapp.data.BuildConfig
import it.macgood.wallpaperapp.data.remote.dto.DownloadUrlDto
import it.macgood.wallpaperapp.data.remote.dto.UnsplashImagesDtoItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET("/photos/random")
    suspend fun getUnsplashImages(
        @Query("query") category: String,
        @Query("client_id") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int,
        @Query("count") count: Int,
        @Query("orientation") orientation: String = "portrait",
    ): List<UnsplashImagesDtoItem>


    @GET("/photos/{id}/download")
    suspend fun downloadImage(
        @Path("id") imageId: String,
        @Query("client_id") apiKey: String = BuildConfig.API_KEY,
    ): DownloadUrlDto



}