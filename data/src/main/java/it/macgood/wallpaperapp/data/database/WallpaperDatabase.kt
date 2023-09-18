package it.macgood.wallpaperapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import it.macgood.wallpaperapp.data.database.entity.FavoriteImageEntity
import it.macgood.wallpaperapp.data.database.entity.SavedImageEntity
import it.macgood.wallpaperapp.data.database.entity.UnsplashImageEntity
import it.macgood.wallpaperapp.data.database.entity.WallpaperCategoryEntity

@Database(
    entities = [
        WallpaperCategoryEntity::class, UnsplashImageEntity::class, FavoriteImageEntity::class, SavedImageEntity::class
    ],
    version = 1
)
abstract class WallpaperDatabase : RoomDatabase() {
    abstract val wallpaperDao: WallpaperDao
    companion object {
        const val DATABASE_NAME = "wallpaper.db"
    }
}