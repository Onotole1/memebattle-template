package ru.memebattle.common.dto.user

data class UserResponseDto(
    val id: Long,
    val username: String,
    val average: Float,
    val semesters: List<SemesterDto>
)