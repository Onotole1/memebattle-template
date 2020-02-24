package ru.memebattle.common.dto

import ru.memebattle.common.model.PostModel
import ru.memebattle.common.model.PostType

data class PostResponseDto(
    val id: Long,
    val author: String,
    val content: String? = null,
    val created: Int,
    val likes: Int = 0,
    val postType: PostType = PostType.POST
) {
    companion object {
        fun fromModel(model: PostModel) = PostResponseDto(
                id = model.id,
                author = model.author,
                content = model.content,
                created = model.created,
                likes = model.likes,
                postType = model.postType
            )
    }
}
