package it.macgood.wallpaper.domain.usecase.saved

import it.macgood.wallpaper.domain.model.SavedImage
import it.macgood.wallpaper.domain.repository.WallpaperRepository
import javax.inject.Inject


class UpsertSavedImageUseCase @Inject constructor(
    private val repository: WallpaperRepository
) {
    suspend operator fun invoke(image: SavedImage) = repository.upsertSavedImage(image)

}

