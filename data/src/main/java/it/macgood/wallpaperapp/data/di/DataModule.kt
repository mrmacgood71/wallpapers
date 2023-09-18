package it.macgood.wallpaperapp.data.di

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.macgood.wallpaper.domain.repository.WallpaperRepository
import it.macgood.wallpaperapp.data.database.PrepopulateWallpaperCategoriesCallback
import it.macgood.wallpaperapp.data.database.WallpaperDao
import it.macgood.wallpaperapp.data.database.WallpaperDatabase
import it.macgood.wallpaperapp.data.database.WallpaperDatabase.Companion.DATABASE_NAME
import it.macgood.wallpaperapp.data.remote.api.UnsplashApi
import it.macgood.wallpaperapp.data.repository.WallpaperRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @Named("apiKey") apiKey: String
    ) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", apiKey)
                .build()
            chain.proceed(request)
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideBeerApi(
        client: OkHttpClient,
        @Named("baseUrl") baseUrl: String
    ): UnsplashApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UnsplashApi::class.java)



    @Provides
    @Singleton
    fun provideWallpaperDatabase(
        @ApplicationContext context: Context,
        provider: Provider<WallpaperDao>,
        resources: Resources
    ) =
        Room.databaseBuilder(
            context,
            WallpaperDatabase::class.java,
            DATABASE_NAME
        ).addCallback(
            PrepopulateWallpaperCategoriesCallback(
                provider = provider,
                resources = resources
            )
        ).build()

    @Provides
    @Singleton
    fun provideWallpaperDao(db: WallpaperDatabase) = db.wallpaperDao

    @Provides
    @Singleton
    fun provideWallpaperRepository(
        database: WallpaperDatabase,
        dao: WallpaperDao,
        api: UnsplashApi
    ): WallpaperRepository = WallpaperRepositoryImpl(database, dao, api)
}