package ru.memebattle.model.vk.model

import com.google.gson.annotations.SerializedName

data class Reposts(
    val count: Int?,
    @SerializedName("user_reposted")
    val userReposted: Int?
)