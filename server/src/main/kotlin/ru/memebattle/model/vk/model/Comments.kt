package ru.memebattle.model.vk.model

import com.google.gson.annotations.SerializedName

data class Comments(
    @SerializedName("can_post")
    val canPost: Int?,
    val count: Int?,
    @SerializedName("groups_can_post")
    val groupsCanPost: Boolean?
)