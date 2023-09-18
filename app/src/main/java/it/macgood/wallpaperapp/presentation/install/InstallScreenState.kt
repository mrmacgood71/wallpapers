package it.macgood.wallpaperapp.presentation.install

import androidx.compose.runtime.mutableStateOf

data class InstallScreenState(
    val isDownloadAlertVisible: Boolean = false,
    val isInstallAlertVisible: Boolean = false,
    val isAlreadyLiked: Boolean = false,
    val isAlreadyDownloaded: Boolean = false,
    val isLoaded: Boolean = true,
    val isDownloaded: Boolean = false
)