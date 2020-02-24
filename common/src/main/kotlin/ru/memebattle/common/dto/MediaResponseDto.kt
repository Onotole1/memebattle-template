package ru.memebattle.common.dto

import ru.memebattle.common.model.MediaModel
import ru.memebattle.common.model.MediaType

data class MediaResponseDto(val id: String, val mediaType: MediaType) {
    companion object {
        fun fromModel(model: MediaModel) = MediaResponseDto(
            id = model.id,
            mediaType = model.mediaType
        )
    }
}