package ru.memebattle.common.dto.user

import java.io.Serializable

data class SemesterDto(
    val id: Long,
    val name: String,
    val subjects: List<SemesterSubjectDto>
) : Serializable