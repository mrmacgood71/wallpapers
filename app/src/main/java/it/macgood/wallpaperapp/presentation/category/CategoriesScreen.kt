package it.macgood.wallpaperapp.presentation.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import it.macgood.wallpaperapp.presentation.destinations.ImagesScreenDestination
import it.macgood.wallpaperapp.presentation.root.model.ImageNavGraph
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ImageNavGraph(start = true)
@Destination(start = true)
fun CategoriesScreen(
    categoriesViewModel: WallpaperCategoriesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val categories = categoriesViewModel.categories.collectAsState().value

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2)
    ) {
        items(categories.categories) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
                    .height(132.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                onClick = {
                    navigator.navigate(ImagesScreenDestination(it.name))
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = it.name)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = it.text)
                }
            }
        }

    }
}