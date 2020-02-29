package ru.memebattle.common.dto.user

data class SemesterDto(
    val id: Long,
    val name: String,
    val subjects: List<SemesterSubjectDto>
)