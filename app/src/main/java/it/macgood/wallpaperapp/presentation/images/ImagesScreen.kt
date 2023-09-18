package it.macgood.wallpaperapp.presentation.images

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.ImageLoader
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import it.macgood.wallpaperapp.presentation.destinations.InstallScreenDestination
import it.macgood.wallpaperapp.presentation.root.model.ImageNavGraph

@Composable
@ImageNavGraph
@Destination
fun ImagesScreen(
    navigator: DestinationsNavigator,
    name: String,
    imagesViewModel: ImagesViewModel = hiltViewModel()
) {
    val gridState = rememberLazyGridState()
    val savedName by rememberSaveable { mutableStateOf(name) }
    val imagesState = imagesViewModel.pagingFlow.collectAsLazyPagingItems()
    val context = LocalContext.current
    val isOffline = imagesViewModel.isOffline.collectAsState().value

    LaunchedEffect(key1 = imagesState.loadState) {
        if (imagesState.loadState.refresh is LoadState.Error) {
            imagesViewModel.changeInternetMode(true)
            Toast.makeText(
                context,
                "Error: " + (imagesState.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        topBar = {
            ImagesTopBar(savedName, navigator)
        }
    ) { innerPaddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddings)
        ) {
            if (imagesState.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    state = gridState,
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(
                        count = imagesState.itemCount,
                        key = imagesState.itemKey { it.id },
                        contentType = imagesState.itemContentType { "contentType" }
                    ) {
                        val imageModel = imagesState[it]

                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    navigator.navigate(
                                        InstallScreenDestination(
                                            image = imageModel!!,
                                            isOffline = isOffline
                                        )
                                    )
                                },
                            imageLoader = ImageLoader.Builder(context)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.FillBounds,
                            model = imageModel?.small,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ImagesTopBar(
    name: String,
    navigator: DestinationsNavigator
) {
    TopAppBar(
        title = { Text(text = name) },
        navigationIcon = {
            IconButton(
                onClick = { navigator.popBackStack() }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}