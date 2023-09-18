package it.macgood.wallpaperapp.presentation.install

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.macgood.wallpaper.domain.usecase.favorite.FavoriteImageUseCases
import it.macgood.wallpaper.domain.usecase.GetDownloadUrlUseCase
import it.macgood.wallpaper.domain.usecase.saved.GetSavedImageUseCase
import it.macgood.wallpaper.domain.usecase.saved.SavedImageUseCases
import it.macgood.wallpaper.domain.usecase.saved.UpsertSavedImageUseCase
import it.macgood.wallpaperapp.data.mapper.toFavoriteImage
import it.macgood.wallpaperapp.data.mapper.toSavedImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class InstallViewModel @Inject constructor(
    private val getDownloadUrlUseCase: GetDownloadUrlUseCase,
    private val savedImageUseCases: SavedImageUseCases,
    private val favoriteImageUseCases: FavoriteImageUseCases,
    private val context: Context
) : ViewModel() {

    private val _uri = MutableStateFlow<Uri?>(null)

    private val _image = MutableStateFlow<Bitmap?>(null)
    val image = _image.asStateFlow()

    private val _screenState = MutableStateFlow(InstallScreenState())
    val screenState = _screenState.asStateFlow()

    fun onEvent(event: InstallScreenEvent) {
        when(event) {
            is InstallScreenEvent.OnDownloadClick -> {
                try {
                    viewModelScope.launch {
                        downloadImage(event.image.id)
                        _screenState.update {
                            it.copy(
                                isDownloadAlertVisible = true
                            )
                        }
                        savedImageUseCases.upsertSavedImageUseCase(event.image.toSavedImage())
                    }
                } catch (e: Exception) {
                    _screenState.update {
                        it.copy(
                            isDownloadAlertVisible = false
                        )
                    }
                }

            }
            is InstallScreenEvent.OnLikeClickEvent -> {
                viewModelScope.launch {
                    favoriteImageUseCases.upsertFavoriteImageUseCase(event.image.toFavoriteImage())
                    _screenState.update {
                        it.copy(isAlreadyLiked = true)
                    }
                }
            }
            is InstallScreenEvent.OnWallpaperInstallClick -> {
                downloadImage(event.imageId)
                _screenState.update {
                    it.copy(isInstallAlertVisible = true)
                }
            }

            is InstallScreenEvent.OnLoadingChanges -> {
                _screenState.update {
                    it.copy(isLoaded = event.value)
                }
            }

            is InstallScreenEvent.OnDownloadAlertVisibilityChanges -> {
                _screenState.update {
                    it.copy(isDownloadAlertVisible = event.value)
                }
            }
            is InstallScreenEvent.OnWallpaperAlertVisibilityChanges -> {
                _screenState.update {
                    it.copy(isInstallAlertVisible = event.value)
                }
            }

            is InstallScreenEvent.OnInitScreen -> {
                isLiked(event.imageId)
                isDownloaded(event.imageId)
            }
        }
    }


    private fun downloadImage(imageId: String) {
        viewModelScope.launch {
            getDownloadUrlUseCase(imageId).collect { url ->
                _image.update {
                    getBitmap(url.url)
                }
            }

            _screenState.update {
                it.copy(isDownloaded = saveImageToMediaStore(context, _image.value))
            }
        }
    }

    private fun saveImageToMediaStore(
        context: Context,
        bitmap: Bitmap?
    ) : Boolean {
        try {
            val resolver = context.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "${System.currentTimeMillis()}_photo.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            val uri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let { resolved ->
                resolver.openOutputStream(resolved)?.let { outputStream ->
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.close()
                }
                _uri.update { resolved }
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    private suspend fun getBitmap(imageUrl: String): Bitmap = withContext(Dispatchers.IO) {
        val inputStream = URL(imageUrl).openStream()

        val bitmap = BitmapFactory.decodeStream(inputStream)
        Log.d("TAG", "createImage: $bitmap")
        return@withContext bitmap
    }

    private fun isLiked(imageId: String) {
        viewModelScope.launch {
            try {
                favoriteImageUseCases.getFavoriteImageUseCase(imageId).collect {
                    _screenState.update {
                        it.copy(isAlreadyLiked = true)
                    }
                }
            } catch (e: Exception) {
                _screenState.update {
                    it.copy(isAlreadyLiked = false)
                }
            }
        }
    }

    private fun isDownloaded(imageId: String) {
        viewModelScope.launch {
            try {
                savedImageUseCases.getSavedImageUseCase(imageId).collect {
                    _screenState.update {
                        it.copy(isAlreadyDownloaded = true)
                    }
                }
            } catch (e: Exception) {
                _screenState.update {
                    it.copy(isAlreadyDownloaded = false)
                }
            }
        }
    }

}