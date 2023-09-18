package it.macgood.wallpaper.domain.repository

import androidx.paging.PagingData
import it.macgood.wallpaper.domain.model.DownloadUrl
import it.macgood.wallpaper.domain.model.FavoriteImage
import it.macgood.wallpaper.domain.model.SavedImage
import it.macgood.wallpaper.domain.model.UnsplashImage
import it.macgood.wallpaper.domain.model.WallpaperCategory
import kotlinx.coroutines.flow.Flow

interface WallpaperRepository {

    fun getAllCategories(): Flow<List<WallpaperCategory>>

    suspend fun getDownloadUrl(imageId: String): Flow<DownloadUrl>

    fun getImage(id: String): Flow<UnsplashImage>

    fun getAllFavoriteImages(): Flow<List<FavoriteImage>>

    fun getAllSavedImages(): Flow<List<SavedImage>>

    suspend fun upsertFavoriteImage(image: FavoriteImage)

    suspend fun upsertSavedImage(image: SavedImage)

    fun getFavoriteImage(id: String): Flow<FavoriteImage>

    fun getSavedImage(id: String): Flow<SavedImage>

    fun getPagingFlow(name: String) : Flow<PagingData<UnsplashImage>>

}