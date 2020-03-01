package ru.memebattle.model.vk.model

import com.google.gson.annotations.SerializedName

data class Item(
    val attachments: List<Attachment?>?,
    val comments: Comments? = null,
    val date: Int? = null,
    val edited: Int? = null,
    @SerializedName("from_id")
    val fromId: Int? = null,
    val id: Int? = null,
    @SerializedName("is_favorite")
    val isFavorite: Boolean? = null,
    @SerializedName("is_pinned")
    val isPinned: Int? = null,
    val likes: Likes? = null,
    @SerializedName("marked_as_ads")
    val markedAsAds: Int? = null,
    @SerializedName("owner_id")
    val ownerId: Int? = null,
    @SerializedName("post_source")
    val postSource: PostSource? = null,
    @SerializedName("post_type")
    val postType: String? = null,
    val reposts: Reposts?,
    @SerializedName("signer_id")
    val signerId: Int? = null,
    val text: String? = null,
    val views: Views? = null
)