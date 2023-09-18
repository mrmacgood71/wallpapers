package it.macgood.wallpaper.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashImage(
    val id: String,
    val full: String,
    val raw: String,
    val regular: String,
    val small: String
): Parcelable