package it.macgood.wallpaperapp.data.remote.api

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import it.macgood.wallpaperapp.data.database.WallpaperDatabase
import it.macgood.wallpaperapp.data.database.entity.UnsplashImageEntity
import it.macgood.wallpaperapp.data.mapper.toUnsplashImageEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ImagesRemoteMediator(
    private val database: WallpaperDatabase,
    private val api: UnsplashApi,
    private val category: String
//    private val savedStateHandle: SavedStateHandle
) : RemoteMediator<Int, UnsplashImageEntity>() {
    var currentPage = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImageEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    currentPage = 1
                    currentPage
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        currentPage = 1
                        currentPage
                    } else {
                        currentPage++
                    }
                }
            }
            val images = api.getUnsplashImages(
                category = category,
                page = loadKey,
                count = state.config.pageSize
            )
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.wallpaperDao.clearAllUnsplash()
                } else {
                    val imagesEntities = images.map { it.toUnsplashImageEntity() }
                    database.wallpaperDao.upsertAllUnsplashImages(imagesEntities)
                }
            }
            MediatorResult.Success(endOfPaginationReached = images.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}