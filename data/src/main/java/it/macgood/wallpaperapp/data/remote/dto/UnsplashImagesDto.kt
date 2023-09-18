package it.macgood.wallpaperapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import it.macgood.wallpaperapp.data.remote.dto.unsplash.ImageLinks
import it.macgood.wallpaperapp.data.remote.dto.unsplash.Sponsorship
import it.macgood.wallpaperapp.data.remote.dto.unsplash.TopicSubmissions
import it.macgood.wallpaperapp.data.remote.dto.unsplash.Urls
import it.macgood.wallpaperapp.data.remote.dto.unsplash.User

data class UnsplashImagesDtoItem(
    @SerializedName("alt_description")
    val altDescription: String,
    @SerializedName("blur_hash")
    val blurHash: String,
    val breadcrumbs: List<Any>,
    val color: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("current_user_collections")
    val currentUserCollections: List<Any>,
    val description: String,
    val height: Int,
    val id: String,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    val likes: Int,
    val imageLinks: ImageLinks,
    @SerializedName("promoted_at")
    val promotedAt: String,
    val slug: String,
    val sponsorship: Sponsorship,
    @SerializedName("topic_submissions")
    val topicSubmissions: TopicSubmissions,
    @SerializedName("updated_at")
    val updatedAt: String,
    val urls: Urls,
    val user: User,
    val width: Int
)

