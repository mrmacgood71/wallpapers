package it.macgood.wallpaperapp.presentation.install.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import it.macgood.wallpaperapp.R
import it.macgood.wallpaperapp.presentation.destinations.ImagesScreenDestination
import it.macgood.wallpaperapp.presentation.destinations.InstallScreenDestination

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun InstallScreenTopBar(navigator: DestinationsNavigator) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.photo)) },
        navigationIcon = {
            IconButton(
                onClick = { navigator.popBackStack() }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}