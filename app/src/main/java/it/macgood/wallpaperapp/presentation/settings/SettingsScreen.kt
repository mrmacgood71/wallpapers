package it.macgood.wallpaperapp.presentation.settings

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import it.macgood.wallpaperapp.data.local.PreferencesManager
import javax.inject.Inject

@Composable
@Destination
fun SettingsScreen(
    navigator: DestinationsNavigator,
    darkTheme: MutableState<Boolean>,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Theme ${
                if (darkTheme.value) "dark"
                else "light"
            }")
            Switch(
                checked = darkTheme.value,
                onCheckedChange = {
                    darkTheme.value = !darkTheme.value
                    settingsViewModel.darkTheme.value = darkTheme.value
                }
            )
        }
    }
}
@HiltViewModel
class SettingsViewModel @Inject constructor(private val preferencesManager: PreferencesManager) : ViewModel() {
    val darkTheme = mutableStateOf(preferencesManager.darkTheme)
}

