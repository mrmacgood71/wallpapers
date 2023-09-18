package it.macgood.wallpaperapp.presentation.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import it.macgood.wallpaperapp.presentation.NavGraphs
import it.macgood.wallpaperapp.presentation.root.component.WallpapersBottomBar
import it.macgood.wallpaperapp.presentation.ui.theme.WallpaperAppTheme

@Composable
fun Root() {
    val darkTheme = remember { mutableStateOf(false) }
    WallpaperAppTheme(
        darkTheme = darkTheme.value
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    WallpapersBottomBar(navController)
                }
            ) { innerPaddings ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPaddings)
                ) {
                    DestinationsNavHost(
                        navController = navController,
                        navGraph = NavGraphs.root,
                        dependenciesContainerBuilder = {
                            dependency(darkTheme)
                        }
                    )
                }
            }
        }
    }

}