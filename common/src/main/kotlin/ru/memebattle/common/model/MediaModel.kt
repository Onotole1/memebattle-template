package ru.memebattle.common.model

enum class MediaType {
    IMAGE
}

data class MediaModel(val id: String, val mediaType: MediaType)