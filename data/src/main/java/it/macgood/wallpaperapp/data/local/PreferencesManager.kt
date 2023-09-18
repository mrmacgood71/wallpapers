package it.macgood.wallpaperapp.data.local

import android.content.Context
import javax.inject.Inject

class PreferencesManager @Inject constructor(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    var darkTheme: Boolean
        get() = sharedPreferences.getBoolean("darkTheme", false)
        set(value) = sharedPreferences.edit().putBoolean("darkTheme", value).apply()
}