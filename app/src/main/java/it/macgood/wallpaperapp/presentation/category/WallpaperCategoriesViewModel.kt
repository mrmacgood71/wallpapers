package it.macgood.wallpaperapp.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.macgood.wallpaper.domain.usecase.GetWallpaperCategoriesUseCase
import it.macgood.wallpaperapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WallpaperCategoriesViewModel @Inject constructor(
    private val getWallpaperCategoriesUseCase: GetWallpaperCategoriesUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow(CategoriesState())
    val categories = _categories.asStateFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            getWallpaperCategoriesUseCase().collect { categories ->
                if (categories.isEmpty()) {
                    _categories.update {
                        it.copy(
                            error = R.string.empty,
                            isLoading = false
                        )
                    }
                } else {
                    _categories.update {
                        it.copy(
                            categories = categories,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}