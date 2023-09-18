package it.macgood.wallpaper.domain.usecase

import it.macgood.wallpaper.domain.repository.WallpaperRepository
import javax.inject.Inject

class GetDownloadUrlUseCase @Inject constructor(
    private val repository: WallpaperRepository
) {

    suspend operator fun invoke(imageId: String) = repository.getDownloadUrl(imageId)

}