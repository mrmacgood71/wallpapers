package it.macgood.wallpaper.domain.usecase.favorite

import it.macgood.wallpaper.domain.model.FavoriteImage
import it.macgood.wallpaper.domain.repository.WallpaperRepository
import javax.inject.Inject

class UpsertFavoriteImageUseCase @Inject constructor(
    private val repository: WallpaperRepository
) {
    suspend operator fun invoke(image: FavoriteImage) = repository.upsertFavoriteImage(image)
}