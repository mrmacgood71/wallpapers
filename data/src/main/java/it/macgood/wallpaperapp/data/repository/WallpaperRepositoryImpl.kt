package it.macgood.wallpaperapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import it.macgood.wallpaper.domain.model.FavoriteImage
import it.macgood.wallpaper.domain.model.SavedImage
import it.macgood.wallpaper.domain.model.UnsplashImage
import it.macgood.wallpaper.domain.model.WallpaperCategory
import it.macgood.wallpaper.domain.repository.WallpaperRepository
import it.macgood.wallpaperapp.data.database.WallpaperDao
import it.macgood.wallpaperapp.data.database.WallpaperDatabase
import it.macgood.wallpaperapp.data.mapper.toDownloadUrl
import it.macgood.wallpaperapp.data.mapper.toFavoriteImage
import it.macgood.wallpaperapp.data.mapper.toFavoriteImageEntity
import it.macgood.wallpaperapp.data.mapper.toSavedImage
import it.macgood.wallpaperapp.data.mapper.toSavedImageEntity
import it.macgood.wallpaperapp.data.mapper.toUnsplashImage
import it.macgood.wallpaperapp.data.mapper.toWallpaperCategory
import it.macgood.wallpaperapp.data.remote.api.ImagesRemoteMediator
import it.macgood.wallpaperapp.data.remote.api.UnsplashApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WallpaperRepositoryImpl @Inject constructor(
    private val database: WallpaperDatabase,
    private val dao: WallpaperDao,
    private val api: UnsplashApi
) : WallpaperRepository {

    override fun getAllCategories(): Flow<List<WallpaperCategory>> = dao.getAllCategories()
        .map { categories ->
            categories.map { it.toWallpaperCategory() }
        }
        .flowOn(Dispatchers.IO)

    override suspend fun getDownloadUrl(imageId: String) = flow {
        emit(api.downloadImage(imageId = imageId).toDownloadUrl())
    }.flowOn(Dispatchers.IO)

    override fun getImage(id: String): Flow<UnsplashImage> = dao.getUnsplashImage(id)
        .map { it.toUnsplashImage() }
        .flowOn(Dispatchers.IO)

    override fun getAllFavoriteImages() = dao.getAllFavoriteImages()
        .map { favorites -> favorites.map { it.toFavoriteImage() } }
        .flowOn(Dispatchers.IO)


    override fun getAllSavedImages(): Flow<List<SavedImage>> = dao.getAllSavedImages()
        .map { saved -> saved.map { it.toSavedImage() } }
        .flowOn(Dispatchers.IO)

    override suspend fun upsertFavoriteImage(image: FavoriteImage) {
        withContext(Dispatchers.IO) {
            dao.upsertFavoriteImage(image.toFavoriteImageEntity())
        }
    }

    override suspend fun upsertSavedImage(image: SavedImage) {
        withContext(Dispatchers.IO) {
            dao.upsertSavedImage(image.toSavedImageEntity())
        }
    }

    override fun getFavoriteImage(id: String): Flow<FavoriteImage> = dao.getFavoriteImage(id)
        .map { it.toFavoriteImage() }
        .flowOn(Dispatchers.IO)

    override fun getSavedImage(id: String): Flow<SavedImage> = dao.getSavedImage(id)
        .map { it.toSavedImage() }
        .flowOn(Dispatchers.IO)

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagingFlow(name: String) = Pager(
        remoteMediator = ImagesRemoteMediator(
            database = database,
            api = api,
            category = name
        ),
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { database.wallpaperDao.pagingSourceUnsplash() }
    ).flow
        .map { data -> data.map { it.toUnsplashImage() }}
        .cachedIn(CoroutineScope(Dispatchers.IO))
}