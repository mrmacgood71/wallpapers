package it.macgood.wallpaper.domain.usecase.saved

import javax.inject.Inject

class SavedImageUseCases @Inject constructor(
    val getSavedImagesUseCase: GetSavedImagesUseCase,
    val getSavedImageUseCase: GetSavedImageUseCase,
    val upsertSavedImageUseCase: UpsertSavedImageUseCase
){
}