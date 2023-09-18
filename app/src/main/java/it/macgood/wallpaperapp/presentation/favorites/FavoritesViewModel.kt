package it.macgood.wallpaperapp.presentation.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.macgood.wallpaper.domain.model.FavoriteImage
import it.macgood.wallpaper.domain.model.SavedImage
import it.macgood.wallpaper.domain.usecase.favorite.GetFavoriteImagesUseCase
import it.macgood.wallpaper.domain.usecase.saved.GetSavedImagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    private val getSavedImagesUseCase: GetSavedImagesUseCase
): ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoriteImage>>(emptyList())
    val favorites = _favorites.asStateFlow()

    private val _saved = MutableStateFlow<List<SavedImage>>(emptyList())
    val saved = _saved.asStateFlow()

    init {
        viewModelScope.launch {
            getSavedImagesUseCase().collect { picked ->
                Log.d("TAG", "picked: $picked")
                _saved.update { picked }
            }


        }
        viewModelScope.launch {
            getFavoriteImagesUseCase().collect { picked ->
                _favorites.update { picked }
            }
        }
    }
}