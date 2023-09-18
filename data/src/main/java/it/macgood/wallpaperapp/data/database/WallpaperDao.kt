package it.macgood.wallpaperapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import it.macgood.wallpaperapp.data.database.entity.FavoriteImageEntity
import it.macgood.wallpaperapp.data.database.entity.SavedImageEntity
import it.macgood.wallpaperapp.data.database.entity.UnsplashImageEntity
import it.macgood.wallpaperapp.data.database.entity.WallpaperCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpaperDao {

    @Query("SELECT * FROM category")
    fun getAllCategories() : Flow<List<WallpaperCategoryEntity>>

    @Query("SELECT * FROM unsplash_image")
    fun pagingSourceUnsplash(): PagingSource<Int, UnsplashImageEntity>

    @Query("SELECT * FROM favorite")
    fun getAllFavoriteImages(): Flow<List<FavoriteImageEntity>>

    @Query("SELECT * FROM saved")
    fun getAllSavedImages(): Flow<List<SavedImageEntity>>

    @Upsert
    suspend fun upsertAllUnsplashImages(images: List<UnsplashImageEntity>)

    @Upsert
    suspend fun upsertFavoriteImage(image: FavoriteImageEntity)

    @Upsert
    suspend fun upsertSavedImage(image: SavedImageEntity)

    @Insert
    suspend fun insertAllCategories(categories: List<WallpaperCategoryEntity>)

    @Query("SELECT * FROM unsplash_image where id = :id")
    fun getUnsplashImage(id: String): Flow<UnsplashImageEntity>

    @Query("SELECT * FROM favorite where id = :id")
    fun getFavoriteImage(id: String): Flow<FavoriteImageEntity>

    @Query("SELECT * FROM saved where id = :id")
    fun getSavedImage(id: String): Flow<SavedImageEntity>

    @Query("DELETE FROM unsplash_image")
    fun clearAllUnsplash()


}