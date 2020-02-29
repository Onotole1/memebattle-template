package ru.memebattle.common.dto.schdule

data class ScheduleDayDto(
    val id: Long,
    val date: Long,
    val lessons: List<LessonDto>
)