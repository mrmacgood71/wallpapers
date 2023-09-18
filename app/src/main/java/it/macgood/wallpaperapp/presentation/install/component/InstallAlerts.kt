package it.macgood.wallpaperapp.presentation.install.component

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.macgood.wallpaperapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun WallpaperInstallingAlert(
    context: Context,
    image: Bitmap?,
    onAlertVisibilityChange: (Boolean) -> Unit
) {
    val isWallpaperInstalling = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val variants = listOf("Home Screen", "Lock Screen", "Both")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(variants[0]) }
    AlertDialog(
        shape = RoundedCornerShape(16.dp),
        title = {
            Text(text = stringResource(R.string.choose_where_you_want_install_wallpaper))
        },
        text = {
            Column(
                modifier = Modifier.selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                variants.forEach {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        RadioButton(
                            selected = (it == selectedOption),
                            onClick = { onOptionSelected(it) }
                        )
                        Text(text = it, fontSize = 22.sp)
                    }
                }
            }
        },
        onDismissRequest = { onAlertVisibilityChange(false) },
        confirmButton = {
            Button(
                enabled = !isWallpaperInstalling.value,
                onClick = {
                    isWallpaperInstalling.value = true
                    coroutineScope.launch(Dispatchers.Default) {
                        val wallpaperManager = WallpaperManager.getInstance(context)

                        when (selectedOption) {
                            variants[0] -> {
                                wallpaperManager.setBitmap(image, null, true, WallpaperManager.FLAG_SYSTEM)
                            }
                            variants[1] -> {
                                wallpaperManager.setBitmap(image, null, true, WallpaperManager.FLAG_LOCK)
                            }
                            variants[2] -> {
                                wallpaperManager.setBitmap(image, null, true, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
                            }
                        }
                    }
                    isWallpaperInstalling.value = false
                    onAlertVisibilityChange(false)
                }
            ) {
                Text(text = stringResource(R.string.ok))
            }
        }
    )
}

@Composable
fun DownloadAlert(
    isDownloaded: Boolean,
    context: Context,
    onAlertVisibilityChange: (Boolean) -> Unit
) {
    AlertDialog(
        modifier = Modifier,
        shape = RoundedCornerShape(16.dp),
        text = {
            Box(modifier = Modifier.size(200.dp)) {
                if (!isDownloaded) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )

                } else {
                    Text(
                        modifier = Modifier.fillMaxSize().align(Alignment.Center),
                        text = context.getString(
                            R.string.file_is_saved
                        ),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = { onAlertVisibilityChange(false) }) {
                Text(text = stringResource(R.string.ok))
            }
        },
        onDismissRequest = { onAlertVisibilityChange(false) }
    )
}