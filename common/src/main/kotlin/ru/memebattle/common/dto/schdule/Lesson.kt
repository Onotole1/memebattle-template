package ru.memebattle.common.dto.schdule

data class Lesson(
    val id: Long,
    val teacherName: String,
    val teacherId: Long,
    val classRoom: String,
    val timeStart: Long,
    val timeEnd: Long
)