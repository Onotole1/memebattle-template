package ru.memebattle.common.dto.schdule

data class LessonRequestDto(
    val name: String,
    val teacherName: String,
    val teacherId: Long,
    val classRoom: String,
    val timeStart: Long,
    val timeEnd: Long
)