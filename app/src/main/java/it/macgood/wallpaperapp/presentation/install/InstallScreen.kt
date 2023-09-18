package it.macgood.wallpaperapp.presentation.install

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import it.macgood.wallpaper.domain.model.UnsplashImage
import it.macgood.wallpaperapp.presentation.install.component.DownloadAlert
import it.macgood.wallpaperapp.presentation.install.component.FunctionsRow
import it.macgood.wallpaperapp.presentation.install.component.InstallScreenTopBar
import it.macgood.wallpaperapp.presentation.install.component.WallpaperInstallingAlert
import it.macgood.wallpaperapp.presentation.root.model.ImageNavGraph

@Composable
@ImageNavGraph
@Destination
fun InstallScreen(
    navigator: DestinationsNavigator,
    installViewModel: InstallViewModel = hiltViewModel(),
    image: UnsplashImage,
    isOffline: Boolean
) {
    val bitmap = installViewModel.image.collectAsState().value
    val screenState = installViewModel.screenState.collectAsState().value
    val context = LocalContext.current

    installViewModel.onEvent(InstallScreenEvent.OnInitScreen(image.id))

    Scaffold(
        topBar = { InstallScreenTopBar(navigator) }
    ) { innerPaddings ->

        Box(modifier = Modifier.fillMaxSize()) {
            if (screenState.isLoaded) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent, RoundedCornerShape(8.dp))
                    .weight(10f)
                    .padding(innerPaddings),

                model = image.regular,
                onSuccess = { InstallScreenEvent.OnLoadingChanges(false) },
                onError = { InstallScreenEvent.OnLoadingChanges(false) },

                imageLoader = ImageLoader.Builder(context)
                    .crossfade(true)
                    .build(),
                contentDescription = null
            )
            Divider(thickness = 1.dp)

            FunctionsRow(isOffline = isOffline, image = image)

            if (screenState.isDownloadAlertVisible) {
                DownloadAlert(
                    isDownloaded = screenState.isDownloaded,
                    context = context
                ) {
                    installViewModel.onEvent(InstallScreenEvent.OnDownloadAlertVisibilityChanges(it))
                }
            }

            if (screenState.isInstallAlertVisible) {
                WallpaperInstallingAlert(
                    context = context,
                    image = bitmap
                ) {
                    installViewModel.onEvent(InstallScreenEvent.OnWallpaperAlertVisibilityChanges(it))
                }
            }
        }
    }
}




