package it.macgood.wallpaperapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "unsplash_image"
)
data class UnsplashImageEntity(
    @PrimaryKey
    val id: String,
    val full: String,
    val raw: String,
    val regular: String,
    val small: String
)