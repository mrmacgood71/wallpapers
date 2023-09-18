package it.macgood.wallpaper.domain.usecase.favorite

import it.macgood.wallpaper.domain.repository.WallpaperRepository
import javax.inject.Inject

class GetFavoriteImageUseCase @Inject constructor(
    private val repository: WallpaperRepository
) {
    operator fun invoke(imageId: String) = repository.getFavoriteImage(imageId)
}