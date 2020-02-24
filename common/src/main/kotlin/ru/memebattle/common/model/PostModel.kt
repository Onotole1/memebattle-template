package ru.memebattle.common.model

data class PostModel(
    val id: Long,
    val author: String,
    val content: String? = null,
    val created: Int = (System.currentTimeMillis() / 1000).toInt(),
    val likes: Int = 0,
    val postType: PostType = PostType.POST
)

enum class PostType {
    POST, REPOST
}
