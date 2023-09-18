package it.macgood.wallpaperapp.presentation.install

import it.macgood.wallpaper.domain.model.UnsplashImage

sealed class InstallScreenEvent {
    data class OnDownloadClick(val image: UnsplashImage) : InstallScreenEvent()
    data class OnWallpaperInstallClick(val imageId: String) : InstallScreenEvent()
    data class  OnLikeClickEvent(val image: UnsplashImage) : InstallScreenEvent()
    data class OnLoadingChanges(val value: Boolean) : InstallScreenEvent()
    data class OnDownloadAlertVisibilityChanges(val value: Boolean) : InstallScreenEvent()
    data class OnWallpaperAlertVisibilityChanges(val value: Boolean) : InstallScreenEvent()
    data class OnInitScreen(val imageId: String) : InstallScreenEvent()
}