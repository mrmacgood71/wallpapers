package it.macgood.wallpaper.domain.usecase

import it.macgood.wallpaper.domain.repository.WallpaperRepository
import javax.inject.Inject

class GetWallpaperCategoriesUseCase @Inject constructor(
    private val repository: WallpaperRepository
) {
    operator fun invoke() = repository.getAllCategories()
}