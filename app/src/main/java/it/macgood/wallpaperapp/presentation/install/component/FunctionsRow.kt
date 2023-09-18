package it.macgood.wallpaperapp.presentation.install.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Portrait
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import it.macgood.wallpaper.domain.model.UnsplashImage
import it.macgood.wallpaperapp.presentation.install.InstallScreenEvent
import it.macgood.wallpaperapp.presentation.install.InstallViewModel

@Composable
fun ColumnScope.FunctionsRow(
    installViewModel: InstallViewModel = hiltViewModel(),
    isOffline: Boolean,
    image: UnsplashImage
) {
    val screenState = installViewModel.screenState.collectAsState().value
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        FunctionButton(
            iconTint = if (screenState.isAlreadyDownloaded) Color.Blue else Color.LightGray,
            image = Icons.Filled.Download
        ) {
            if (!isOffline) installViewModel.onEvent(InstallScreenEvent.OnDownloadClick(image))
        }

        FunctionButton(
            iconTint = if (screenState.isAlreadyLiked) Color.Red else Color.LightGray,
            image = Icons.Filled.Favorite,
        ) {
            installViewModel.onEvent(InstallScreenEvent.OnLikeClickEvent(image))
        }
        FunctionButton(
            iconTint = Color.DarkGray,
            image = Icons.Filled.Portrait,
        ) {
            if (!isOffline) installViewModel.onEvent(
                InstallScreenEvent.OnWallpaperInstallClick(
                    image.id
                )
            )
        }
    }
}

@Composable
fun FunctionButton(
    imageModifier: Modifier = Modifier,
    iconTint: Color = Color.Black,
    image: ImageVector,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { onClick() }
        ) {
            Icon(
                modifier = imageModifier.size(32.dp),
                imageVector = image,
                contentDescription = null,
                tint = iconTint
            )
        }
    }
}