package it.macgood.wallpaperapp.presentation.category

import androidx.annotation.StringRes
import it.macgood.wallpaper.domain.model.WallpaperCategory

data class CategoriesState(
    val categories: List<WallpaperCategory> = emptyList(),
    @StringRes val error: Int? = null,
    val isLoading: Boolean = true
) {
}