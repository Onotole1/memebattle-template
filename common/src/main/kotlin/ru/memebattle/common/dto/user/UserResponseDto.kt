package ru.memebattle.common.dto.user

data class UserResponseDto(
    val id: Long,
    val username: String,
    val rating: Int,
    val average: Float,
    val semesters: List<SemesterDto>
)