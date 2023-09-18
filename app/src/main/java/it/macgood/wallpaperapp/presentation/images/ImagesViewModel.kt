package it.macgood.wallpaperapp.presentation.images

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import it.macgood.wallpaper.domain.repository.WallpaperRepository
import it.macgood.wallpaperapp.data.database.WallpaperDatabase
import it.macgood.wallpaperapp.data.mapper.toUnsplashImage
import it.macgood.wallpaperapp.data.remote.api.ImagesRemoteMediator
import it.macgood.wallpaperapp.data.remote.api.UnsplashApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val db: WallpaperDatabase,
    private val api: UnsplashApi,
    wallpaperRepository: WallpaperRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val name = savedStateHandle["name"] ?: ""

    private val _isOffline = MutableStateFlow(false)
    val isOffline = _isOffline.asStateFlow()

    val pagingFlow = wallpaperRepository.getPagingFlow(name)

    fun changeInternetMode(value: Boolean) {
        _isOffline.update { value }
    }
}