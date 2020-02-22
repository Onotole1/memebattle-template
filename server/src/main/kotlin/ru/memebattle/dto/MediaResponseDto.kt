package ru.memebattle.dto

import ru.memebattle.model.MediaModel
import ru.memebattle.model.MediaType

data class MediaResponseDto(val id: String, val mediaType: MediaType) {
    companion object {
        fun fromModel(model: MediaModel) = MediaResponseDto(
            id = model.id,
            mediaType = model.mediaType
        )
    }
}