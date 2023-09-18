package it.macgood.wallpaperapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(
    tableName = "saved"
)
data class SavedImageEntity(
    @PrimaryKey
    val id: String,
    val regular: String
)