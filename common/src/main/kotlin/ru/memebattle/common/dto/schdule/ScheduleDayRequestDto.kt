package ru.memebattle.common.dto.schdule

data class ScheduleDayRequestDto(
    val date: Long,
    val lessons: List<LessonRequestDto>
)