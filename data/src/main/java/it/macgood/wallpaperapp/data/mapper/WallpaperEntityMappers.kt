package it.macgood.wallpaperapp.data.mapper

import it.macgood.wallpaper.domain.model.DownloadUrl
import it.macgood.wallpaper.domain.model.FavoriteImage
import it.macgood.wallpaper.domain.model.SavedImage
import it.macgood.wallpaper.domain.model.UnsplashImage
import it.macgood.wallpaper.domain.model.WallpaperCategory
import it.macgood.wallpaperapp.data.database.entity.FavoriteImageEntity
import it.macgood.wallpaperapp.data.database.entity.SavedImageEntity
import it.macgood.wallpaperapp.data.database.entity.UnsplashImageEntity
import it.macgood.wallpaperapp.data.database.entity.WallpaperCategoryEntity
import it.macgood.wallpaperapp.data.remote.dto.DownloadUrlDto
import it.macgood.wallpaperapp.data.remote.dto.UnsplashImagesDtoItem

fun WallpaperCategoryEntity.toWallpaperCategory(): WallpaperCategory =
    WallpaperCategory(id, name, text)

fun WallpaperCategory.toWallpaperCategoryEntity(): WallpaperCategoryEntity =
    WallpaperCategoryEntity(id, name, text)


fun UnsplashImageEntity.toUnsplashImage(): UnsplashImage =
    UnsplashImage(id, full, raw, regular, small)

fun UnsplashImage.toUnsplashImageEntity(): UnsplashImageEntity =
    UnsplashImageEntity(id, full, raw, regular, small)

fun UnsplashImagesDtoItem.toUnsplashImage(): UnsplashImage = UnsplashImage(
    id = id,
    full = urls.full,
    raw = urls.raw,
    regular = urls.regular,
    small = urls.small
)

fun UnsplashImagesDtoItem.toUnsplashImageEntity(): UnsplashImageEntity = UnsplashImageEntity(
    id = id,
    full = urls.full,
    raw = urls.raw,
    regular = urls.regular,
    small = urls.small
)

fun UnsplashImageEntity.toFavoriteImage(): FavoriteImage = FavoriteImage(id, small)
fun UnsplashImageEntity.toSavedImageEntity(): SavedImageEntity = SavedImageEntity(id, regular)
fun UnsplashImage.toFavoriteImageEntity(): FavoriteImageEntity = FavoriteImageEntity(id, small)
fun UnsplashImage.toSavedImageEntity(): SavedImageEntity = SavedImageEntity(id, regular)
fun UnsplashImage.toFavoriteImage(): FavoriteImage = FavoriteImage(id, small)
fun UnsplashImage.toSavedImage(): SavedImage = SavedImage(id, regular)


fun DownloadUrlDto.toDownloadUrl(): DownloadUrl = DownloadUrl(url = url)

fun UnsplashImageEntity.toFavoriteImageEntity(): FavoriteImageEntity = FavoriteImageEntity(id, small)

fun FavoriteImageEntity.toFavoriteImage(): FavoriteImage = FavoriteImage(id, small)
fun FavoriteImage.toFavoriteImageEntity(): FavoriteImageEntity = FavoriteImageEntity(id, small)

fun SavedImageEntity.toSavedImage(): SavedImage = SavedImage(id, regular)
fun SavedImage.toSavedImageEntity(): SavedImageEntity = SavedImageEntity(id, regular)
