package it.macgood.wallpaperapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite"
)
data class FavoriteImageEntity(
    @PrimaryKey
    val id: String,
    val small: String
)
