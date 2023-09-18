package it.macgood.wallpaper.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.macgood.wallpaper.domain.repository.WallpaperRepository
import it.macgood.wallpaper.domain.usecase.favorite.FavoriteImageUseCases
import it.macgood.wallpaper.domain.usecase.favorite.GetFavoriteImageUseCase
import it.macgood.wallpaper.domain.usecase.favorite.GetFavoriteImagesUseCase
import it.macgood.wallpaper.domain.usecase.favorite.UpsertFavoriteImageUseCase
import it.macgood.wallpaper.domain.usecase.saved.GetSavedImageUseCase
import it.macgood.wallpaper.domain.usecase.saved.GetSavedImagesUseCase
import it.macgood.wallpaper.domain.usecase.saved.SavedImageUseCases
import it.macgood.wallpaper.domain.usecase.saved.UpsertSavedImageUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideFavoriteImageUseCases(repository: WallpaperRepository) = FavoriteImageUseCases(
        getFavoriteImagesUseCase = GetFavoriteImagesUseCase(repository),
        getFavoriteImageUseCase = GetFavoriteImageUseCase(repository),
        upsertFavoriteImageUseCase = UpsertFavoriteImageUseCase(repository)
    )

    @Provides
    @Singleton
    fun provideSavedImageUseCases(repository: WallpaperRepository) = SavedImageUseCases(
        getSavedImagesUseCase = GetSavedImagesUseCase(repository),
        getSavedImageUseCase = GetSavedImageUseCase(repository),
        upsertSavedImageUseCase = UpsertSavedImageUseCase(repository)
    )

}