package it.macgood.wallpaper.domain.usecase.favorite

import javax.inject.Inject

class FavoriteImageUseCases @Inject constructor(
    val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    val getFavoriteImageUseCase: GetFavoriteImageUseCase,
    val upsertFavoriteImageUseCase: UpsertFavoriteImageUseCase
){
}