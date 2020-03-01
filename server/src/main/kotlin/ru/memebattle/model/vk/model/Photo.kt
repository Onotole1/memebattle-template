package ru.memebattle.model.vk.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("access_key")
    val accessKey: String? = null,
    @SerializedName("album_id")
    val albumId: Int? = null,
    val date: Int? = null,
    val id: Int? = null,
    @SerializedName("owner_id")
    val ownerId: Int? = null,
    @SerializedName("post_id")
    val postId: Int? = null,
    val sizes: List<Size?>?,
    val text: String? = null,
    @SerializedName("user_id")
    val userId: Int? = null
)

fun List<Size?>.getMaxImage(): String? =
    maxBy {
        it?.height ?: 0
    }?.url