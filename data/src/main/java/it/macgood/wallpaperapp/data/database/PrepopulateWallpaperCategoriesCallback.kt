package it.macgood.wallpaperapp.data.database

import android.content.res.Resources
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import it.macgood.wallpaperapp.data.R
import it.macgood.wallpaperapp.data.database.entity.WallpaperCategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class PrepopulateWallpaperCategoriesCallback @Inject constructor(
    val provider: Provider<WallpaperDao>,
    val resources: Resources
): RoomDatabase.Callback() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        applicationScope.launch(Dispatchers.IO) {
            populateDatabase()
        }

    }

    private suspend fun populateDatabase() {
        val categoriesJson = resources.openRawResource(R.raw.category).bufferedReader().use {
            it.readText()
        }
        val typeToken = object : TypeToken<List<WallpaperCategoryEntity>>() {}.type
        val categoryList = Gson().fromJson<List<WallpaperCategoryEntity>>(categoriesJson, typeToken)

        provider.get().insertAllCategories(categoryList)
    }
}