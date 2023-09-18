package it.macgood.wallpaper.domain.usecase.saved

import it.macgood.wallpaper.domain.repository.WallpaperRepository
import javax.inject.Inject


class GetSavedImagesUseCase @Inject constructor(
    private val repository: WallpaperRepository
) {
    operator fun invoke() = repository.getAllSavedImages()
}