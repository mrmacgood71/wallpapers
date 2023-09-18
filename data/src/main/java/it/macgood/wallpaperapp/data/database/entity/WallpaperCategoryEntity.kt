package it.macgood.wallpaperapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "category"
)
data class WallpaperCategoryEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val text: String
)
