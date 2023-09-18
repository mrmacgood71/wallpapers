package it.macgood.wallpaperapp.presentation.favorites

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import it.macgood.wallpaperapp.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Destination
fun FavoritesScreenPager() {
    val coroutineScope = rememberCoroutineScope()

    val tabRowItems = listOf(
        ImageTabItem(
            text = stringResource(R.string.favorites_tab),
            icon = Icons.Default.Favorite,
            screen = { FavoritesScreen() }
        ),
        ImageTabItem(
            text = stringResource(R.string.saved),
            icon = Icons.Default.Save,
            screen = { SavedImages() }
        )
    )
    val pagerState = rememberPagerState(pageCount = { 2 })

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
            tabRowItems.forEachIndexed { i, item ->
                Tab(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    text = { Text(text = item.text) },
                    icon = { Icon(imageVector = item.icon, "") },
                    selected = pagerState.currentPage == i,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(i) } }
                )
            }
        }
    }

    HorizontalPager(
        modifier = Modifier.padding(top = 64.dp),
        state = pagerState,
        userScrollEnabled = true,
        beyondBoundsPageCount = tabRowItems.size
    ) {
        tabRowItems[pagerState.currentPage].screen()
    }
}

data class ImageTabItem(
    val text: String,
    val icon: ImageVector,
    val screen: @Composable () -> Unit
)

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesViewModel = hiltViewModel()
) {
    val images = favoritesViewModel.favorites.collectAsState().value
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(images){
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.FillBounds,
                model = it.small,
                contentDescription = null
            )
        }
    }
}

@Composable
@Destination
fun SavedImages(favoritesViewModel: FavoritesViewModel = hiltViewModel()) {
    val images = favoritesViewModel.saved.collectAsState().value
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(images){
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.FillBounds,
                model = it.regular,
                contentDescription = null
            )
        }
    }
}