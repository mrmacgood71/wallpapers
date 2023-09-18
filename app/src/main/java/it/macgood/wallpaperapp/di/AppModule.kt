package it.macgood.wallpaperapp.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.macgood.wallpaperapp.BuildConfig
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey() = BuildConfig.API_KEY

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources = context.resources

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context

}