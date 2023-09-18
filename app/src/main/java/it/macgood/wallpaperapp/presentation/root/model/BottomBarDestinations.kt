package it.macgood.wallpaperapp.presentation.root.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import it.macgood.wallpaperapp.R
import it.macgood.wallpaperapp.presentation.destinations.CategoriesScreenDestination
import it.macgood.wallpaperapp.presentation.destinations.FavoritesScreenPagerDestination
import it.macgood.wallpaperapp.presentation.destinations.SettingsScreenDestination

enum class BottomBarDestinations(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Wallpapers(
        direction = CategoriesScreenDestination,
        icon = Icons.Filled.ImageSearch,
        label = R.string.wallpapers
    ),
    Settings(
        direction = SettingsScreenDestination,
        icon = Icons.Filled.Settings,
        label = R.string.settings
    ),
    Favorites(
        direction = FavoritesScreenPagerDestination,
        icon = Icons.Filled.Favorite,
        label = R.string.favorites
    )
}

@RootNavGraph(start = true)
@NavGraph
annotation class ImageNavGraph(
    val start: Boolean = false,
)
