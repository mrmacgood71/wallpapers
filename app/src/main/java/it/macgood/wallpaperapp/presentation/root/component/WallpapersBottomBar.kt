package it.macgood.wallpaperapp.presentation.root.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.navigation.navigate
import it.macgood.wallpaperapp.presentation.NavGraphs
import it.macgood.wallpaperapp.presentation.appCurrentDestinationAsState
import it.macgood.wallpaperapp.presentation.destinations.CategoriesScreenDestination
import it.macgood.wallpaperapp.presentation.destinations.Destination
import it.macgood.wallpaperapp.presentation.destinations.FavoritesScreenPagerDestination
import it.macgood.wallpaperapp.presentation.destinations.ImagesScreenDestination
import it.macgood.wallpaperapp.presentation.destinations.InstallScreenDestination
import it.macgood.wallpaperapp.presentation.destinations.SettingsScreenDestination
import it.macgood.wallpaperapp.presentation.root.model.BottomBarDestinations
import it.macgood.wallpaperapp.presentation.startAppDestination

@Composable
fun WallpapersBottomBar(
    navController: NavController
) {
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    NavigationBar {

        NavigationBarItem(
            selected = when (currentDestination) {
                CategoriesScreenDestination -> true
                ImagesScreenDestination -> true
                InstallScreenDestination -> true
                else -> false
            },
            label = { Text(text = stringResource(id = BottomBarDestinations.Wallpapers.label)) },
            onClick = {
                navController.navigate(
                    BottomBarDestinations.Wallpapers.direction,
                    fun NavOptionsBuilder.() {
                        launchSingleTop = true
                    })
            },
            icon = {
                Icon(
                    imageVector = BottomBarDestinations.Wallpapers.icon,
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = when (currentDestination) {
                SettingsScreenDestination -> true
                else -> false
            },
            label = { Text(text = stringResource(id = BottomBarDestinations.Settings.label)) },
            onClick = {
                navController.navigate(
                    BottomBarDestinations.Settings.direction,
                    fun NavOptionsBuilder.() {
                        launchSingleTop = true
                    })
            },
            icon = {
                Icon(
                    imageVector = BottomBarDestinations.Settings.icon,
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = when (currentDestination) {
                FavoritesScreenPagerDestination -> true
                else -> false
            },
            label = { Text(text = stringResource(id = BottomBarDestinations.Favorites.label)) },
            onClick = {
                navController.navigate(
                    BottomBarDestinations.Favorites.direction,
                    fun NavOptionsBuilder.() {
                        launchSingleTop = true
                    })
            },
            icon = {
                Icon(
                    imageVector = BottomBarDestinations.Favorites.icon,
                    contentDescription = null
                )
            }
        )

    }
}

