package it.macgood.wallpaper.domain.usecase.saved

import it.macgood.wallpaper.domain.repository.WallpaperRepository
import javax.inject.Inject

class GetSavedImageUseCase @Inject constructor(
    private val repository: WallpaperRepository
) {
    operator fun invoke(imageId: String) = repository.getSavedImage(imageId)
}