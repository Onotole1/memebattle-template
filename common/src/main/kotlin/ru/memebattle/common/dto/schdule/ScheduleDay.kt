package ru.memebattle.common.dto.schdule

data class ScheduleDay(
    val id: Long,
    val date: Long,
    val lessons: List<Lesson>
)