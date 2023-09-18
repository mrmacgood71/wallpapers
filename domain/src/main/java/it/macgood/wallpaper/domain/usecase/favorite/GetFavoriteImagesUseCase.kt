package it.macgood.wallpaper.domain.usecase.favorite

import it.macgood.wallpaper.domain.repository.WallpaperRepository
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    private val repository: WallpaperRepository
) {
    operator fun invoke() = repository.getAllFavoriteImages()
}